<%@include file="/WEB-INF/view/jspf/common/teglib.jspf"%>
<%@include file="/WEB-INF/view/jspf/common/page_info.jspf"%>

<html>
<c:set var="title" value="Station on route" scope="page" />
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

    <%-- Station on route input --%>
    <table><br/>
        <form action="/controller">
            <input type="hidden" name="command" value="addStationToRoute">
            <input type="hidden" name="routeId" value="${requestScope.routeId}">
            <input type="hidden" name="typeId" value="3">

            <tr>
                <td><fmt:message key="page.lang.station.name" />:</td>
                <td><select name="stationName">
                    <c:forEach items="${requestScope.stations}" var="station">
                        <option>${pageScope.station.name}</option>
                    </c:forEach>
                </select></td>
            </tr>
            <tr>
                <td><fmt:message key="page.lang.date" />:</td>
                <td><input type="date" name="date" required></td>
            </tr>
            <tr>
                <td><fmt:message key="page.lang.arrival.time" />:</td>
                <td><input type="time" name="arrivalTime" required></td>
            </tr>
            <tr>
                <td><fmt:message key="page.lang.parking.time" />:</td>
                <td><input type="time" name="parkingTime" required></td>
            </tr>
            <tr>
                <td><fmt:message key="page.lang.departure.time" />:</td>
                <td><input type="time" name="departureTime" required></td>
            </tr>
            <tr>
                <td><input type="submit" class="btn btn-primary btn-default" value="<fmt:message key="page.lang.add" />"></td>
            </tr>
        </form>
    </table>
</c:if>

<%-- If user not admin --%>
<u:permit role="admin"/>

<%-- Footer --%>
<%@include file="/WEB-INF/view/jspf/common/footer.jspf"%>
</body>
</html>
