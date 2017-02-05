<%@include file="/WEB-INF/view/jspf/common/teglib.jspf" %>
<%@include file="/WEB-INF/view/jspf/common/page_info.jspf" %>

<html>
<c:set var="title" value="Route" scope="page"/>
<%@include file="/WEB-INF/view/jspf/common/head.jspf" %>
<body>

<c:if test="${sessionScope.user.role.name=='admin'}">

    <%-- User info --%>
    <div class="container">
        <div class="row">
            <div id="cssmenu">
                <ul>
                    <%@include file="/WEB-INF/view/jspf/admin/admin_menu.jspf" %>

                    <%@include file="/WEB-INF/view/jspf/common/locale_chooser.jspf" %>

                    <%@include file="/WEB-INF/view/jspf/common/logout_form.jspf" %>
                </ul>
            </div>
        </div>
    </div>

    <br/>

    <%-- Create route --%>
    <table><br/>
        <form action="/controller">
            <input type="hidden" name="command" value="toRouteCreate">
            <tr>
                <input type="submit" class="btn btn-primary btn-default"
                       value="<fmt:message key="page.lang.create.route" />">
            </tr>
        </form>
    </table>

    <%-- All routes --%>
    <c:if test="${not empty requestScope.routesInfo}">
        <table id="info-table"><br/>
            <tr>
                <th><fmt:message key="page.lang.route.id"/></th>
                <th><fmt:message key="page.lang.estimated.time.en.route"/></th>
                <th><fmt:message key="page.lang.train.number"/></th>
                <th><fmt:message key="page.lang.availability"/></th>
            </tr>
            <c:forEach items="${requestScope.routesInfo}" var="routeInfo">
                <tr>
                    <td>${pageScope.routeInfo.route.id}</td>
                    <td>${pageScope.routeInfo.route.travelTime}</td>
                    <td>${pageScope.routeInfo.train.number}</td>
                    <td>
                        <table id="inner-table">
                            <c:forEach items="${pageScope.routeInfo.trainPlaces}" var="trainPlace">

                                <%-- If we have some seats --%>
                                <c:if test="${pageScope.trainPlace.freePlaces>0}">
                                    <tr>
                                        <td>${pageScope.trainPlace.type.name}: ${pageScope.trainPlace.freePlaces};</td>
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
                    <form action="/controller">
                        <input type="hidden" name="command" value="toChangeEdgeStation">
                        <input type="hidden" name="routeId" value="${pageScope.routeInfo.route.id}">
                        <input type="hidden" name="stationTypeId" value="1">
                        <td><input type="submit" class="btn btn-primary btn-default"
                                   value="<fmt:message key="page.lang.initial.station" />"></td>
                    </form>
                    <form action="/controller">
                        <input type="hidden" name="command" value="toChangeEdgeStation">
                        <input type="hidden" name="routeId" value="${pageScope.routeInfo.route.id}">
                        <input type="hidden" name="stationTypeId" value="2">
                        <td><input type="submit" class="btn btn-primary btn-default"
                                   value="<fmt:message key="page.lang.terminal.station" />"></td>
                    </form>
                    <form action="/controller">
                        <input type="hidden" name="command" value="toChangeWayStation">
                        <input type="hidden" name="routeId" value="${pageScope.routeInfo.route.id}">
                        <td><input type="submit" class="btn btn-primary btn-default"
                                   value="<fmt:message key="page.lang.way.station" />"></td>
                    </form>
                    <form action="/controller">
                        <input type="hidden" name="command" value="toRouteEdit">
                        <input type="hidden" name="routeId" value="${pageScope.routeInfo.route.id}">
                        <td><input type="submit" class="btn btn-primary btn-default"
                                   value="<fmt:message key="page.lang.edit" />"></td>
                    </form>
                    <form action="/controller">
                        <input type="hidden" name="command" value="toRouteRemove">
                        <input type="hidden" name="routeId" value="${pageScope.routeInfo.route.id}">
                        <td><input type="submit" class="btn btn-primary btn-default"
                                   value="<fmt:message key="page.lang.remove" />"></td>
                    </form>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</c:if>

<%-- If user not admin --%>
<u:permit role="admin"/>

<%-- Footer --%>
<%@include file="/WEB-INF/view/jspf/common/footer.jspf" %>
</body>
</html>
