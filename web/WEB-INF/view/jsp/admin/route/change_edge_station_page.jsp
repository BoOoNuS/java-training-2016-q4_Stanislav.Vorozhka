<%@include file="/WEB-INF/view/jspf/common/teglib.jspf"%>
<%@include file="/WEB-INF/view/jspf/common/page_info.jspf"%>

<html>
<c:set var="title" value="Change edge station" scope="page" />
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

    <%-- If currentStation not empty --%>
    <table><br/>
        <c:if test="${not empty requestScope.currentStation.name}">
            <tr>
                <td><fmt:message key="page.lang.name.current.edge.station" />:</td>
                <td>${requestScope.currentStation.name}</td>
            </tr>
        </c:if>
    </table>

    <%-- Creation input --%>
    <table><br/>
        <form action="/controller" method="post">
            <input hidden name="command" value="changeEdgeStation">
            <input type="hidden" name="routeId" value="${requestScope.routeId}">
            <input type="hidden" name="stationTypeId" value="${requestScope.stationTypeId}">
            <input type="hidden" name="stationId" value="${requestScope.currentStation.id}">
            <tr>
                <td><fmt:message key="page.lang.station.name" />:</td>
                <td><select name="updatedStationName">
                    <c:forEach items="${requestScope.stations}" var="station">
                        <option>${station.name}</option>
                    </c:forEach>
                </select></td>
            </tr>
            <tr>
                <td><fmt:message key="page.lang.date" />:</td>
                <td>
                    <input type="date" name="date" required>
                </td>
            </tr>
            <tr>
                <td><fmt:message key="page.lang.time" />:</td>
                <td>
                    <input type="time" name="time" required>
                </td>
            </tr>
            <tr>
                <td><input type="submit" class="btn btn-primary btn-default" value="<fmt:message key="page.lang.change" />"></td>
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
