<%@ tag language="java" pageEncoding="UTF-8" %>

<%@ attribute name="routesInfo" type="java.util.List" %>
<%@ attribute name="user" type="ua.nure.vorozhka.SummaryTask4.db.model.entity.User" %>

<%@include file="/WEB-INF/view/jspf/common/teglib.jspf" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="locale"/>

<c:if test="${not empty pageScope.routesInfo}">
    <table id="info-table"><br/>
        <tr>
            <th><fmt:message key="page.lang.train.number"/></th>
            <th><fmt:message key="page.lang.time.date.departure.station"/></th>
            <th><fmt:message key="page.lang.travel.time"/></th>
            <th><fmt:message key="page.lang.time.date.arrival.station"/></th>
            <th><fmt:message key="page.lang.availability.price"/></th>
        </tr>

        <c:forEach items="${pageScope.routesInfo}" var="routeInfo">
            <tr>
                <td>${pageScope.routeInfo.train.number}</td>
                <td>${pageScope.routeInfo.initialStation.time}/
                        ${pageScope.routeInfo.initialStation.date}/
                        ${pageScope.routeInfo.initialStation.stationName}</td>

                <td>${pageScope.routeInfo.route.travelTime}</td>
                <td>${pageScope.routeInfo.terminalStation.time}/
                        ${pageScope.routeInfo.terminalStation.date}/
                        ${pageScope.routeInfo.terminalStation.stationName}</td>

                <td>
                    <table id="inner-table">
                        <c:forEach items="${pageScope.routeInfo.trainPlaces}" var="trainPlace">

                            <%-- If we have some seats --%>
                            <c:if test="${pageScope.trainPlace.freePlaces>0}">
                                <tr>
                                    <td>${pageScope.trainPlace.type.name}: ${pageScope.trainPlace.freePlaces},
                                        Fare - ${pageScope.trainPlace.cost};
                                    </td>

                                        <%-- If the user is authenticated, he may purchase a train ticket --%>
                                    <c:if test="${not empty pageScope.user}">
                                        <form action="/controller" method="post">
                                            <input type="hidden" name="command" value="generateTicket">
                                            <input type="hidden" name="routeInfo" value="${pageScope.routeInfo}">
                                            <input type="hidden" name="trainPlace" value="${pageScope.trainPlace}">
                                                    <td><input type="submit" class="btn btn-primary btn-default"
                                                               value="<fmt:message key="page.lang.buy.ticket" />"></td>
                                        </form>
                                    </c:if>
                                </tr>
                            </c:if>

                            <%-- If no seats --%>
                            <c:if test="${pageScope.trainPlace.freePlaces<=0}">
                                <tr>
                                    <td>${pageScope.trainPlace.type.name}: NO SEATS;</td>
                                </tr>
                            </c:if>
                        </c:forEach>
                    </table>
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>