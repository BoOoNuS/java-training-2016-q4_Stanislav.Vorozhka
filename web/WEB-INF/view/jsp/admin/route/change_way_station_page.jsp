<%@include file="/WEB-INF/view/jspf/common/teglib.jspf"%>
<%@include file="/WEB-INF/view/jspf/common/page_info.jspf"%>

<html>
<c:set var="title" value="Change way station" scope="page" />
<%@include file="/WEB-INF/view/jspf/common/head.jspf"%>
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

    <%-- Stations on route --%>
    <c:if test="${not empty requestScope.stationsOnRoute}">
        <table id="info-table"><br/>
            <tr>
                <th><fmt:message key="page.lang.station.id" /></th>
                <th><fmt:message key="page.lang.date" /></th>
                <th><fmt:message key="page.lang.time.arrival.parking.departure" /></th>
                <th><fmt:message key="page.lang.remove" /></th>
            </tr>
            <c:forEach items="${requestScope.stationsOnRoute}" var="stationOnRoute">
                <tr>
                    <td>${pageScope.stationOnRoute.station.name}</td>
                    <td>${pageScope.stationOnRoute.date}</td>
                    <td>${pageScope.stationOnRoute.time}</td>
                    <form action="/controller">
                        <input type="hidden" name="command" value="toStationOnRouteRemove">
                        <input type="hidden" name="routeId" value="${requestScope.routeId}">
                        <input type="hidden" name="stationId" value="${pageScope.stationOnRoute.station.id}">
                        <td><input type="submit" class="btn btn-primary btn-default" value="<fmt:message key="page.lang.remove" />"></td>
                    </form>
                </tr>
            </c:forEach>
        </table>
    </c:if>

    <table><br/>
        <tr>
            <form action="/controller">
                <input type="hidden" name="command" value="toStationOnRouteAdd">
                <input type="hidden" name="routeId" value="${requestScope.routeId}">
                <td><input type="submit" class="btn btn-primary btn-default" value="<fmt:message key="page.lang.add.station" />"></td>
            </form>
        </tr>
    </table>
</c:if>

<%-- If user not admin --%>
<u:permit role="admin"/>

<%-- Footer --%>
<%@include file="/WEB-INF/view/jspf/common/footer.jspf"%>
</body>
</html>
