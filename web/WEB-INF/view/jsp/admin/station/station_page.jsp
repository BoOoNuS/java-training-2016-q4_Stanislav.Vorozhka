<%@include file="/WEB-INF/view/jspf/common/teglib.jspf"%>
<%@include file="/WEB-INF/view/jspf/common/page_info.jspf"%>

<html>
<c:set var="title" value="Station" scope="page" />
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

    <%-- Create station --%>
    <table><br/>
        <tr>
            <form action="/controller">
                <input type="hidden" name="command" value="toStationCreate">
                <td><input type="submit" class="btn btn-primary btn-default" value="<fmt:message key="page.lang.create.station" />"></td>
            </form>
        </tr>
    </table>

    <%-- All stations --%>
    <table id="info-table"><br/>
        <tr>
            <th><fmt:message key="page.lang.station.id" /></th>
            <th><fmt:message key="page.lang.station.name" /></th>
            <th><fmt:message key="page.lang.edit" /></th>
            <th><fmt:message key="page.lang.remove" /></th>
        </tr>
        <c:forEach items="${requestScope.stations}" var="station">
            <tr>
                <td>${pageScope.station.id}</td>
                <td>${pageScope.station.name}</td>
                <form action="/controller">
                    <input type="hidden" name="command" value="toStationEdit">
                    <input type="hidden" name="stationId" value="${pageScope.station.id}">
                    <td><input type="submit" class="btn btn-primary btn-default" value="<fmt:message key="page.lang.edit" />"></td>
                </form>
                <form action="/controller">
                    <input type="hidden" name="command" value="toStationRemove">
                    <input type="hidden" name="stationId" value="${pageScope.station.id}">
                    <td><input type="submit" class="btn btn-primary btn-default" value="<fmt:message key="page.lang.remove" />"></td>
                </form>
            </tr>
        </c:forEach>
    </table>
</c:if>

<%-- If user not admin --%>
<u:permit role="admin"/>

<%-- Footer --%>
<%@include file="/WEB-INF/view/jspf/common/footer.jspf"%>
</body>
</html>
