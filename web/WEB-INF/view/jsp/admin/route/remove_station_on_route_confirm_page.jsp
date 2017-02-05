<%@include file="/WEB-INF/view/jspf/common/teglib.jspf"%>
<%@include file="/WEB-INF/view/jspf/common/page_info.jspf"%>

<html>
<c:set var="title" value="Remove confirmation" scope="page" />
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

    <%-- Remove confirmation --%>
    <table><br/>
        <tr>
            <td><fmt:message key="page.lang.sure.delete.station.on.route" />?</td>
        </tr>
    </table>

    <table><br/>
        <tr>
            <form action="/controller" method="post">
                <input type="hidden" name="command" value="removeStationOnRoute">
                <input type="hidden" name="routeId" value="${requestScope.routeId}">
                <input type="hidden" name="stationId" value="${requestScope.stationId}">
                <td><input type="submit" class="btn btn-primary btn-default" value="<fmt:message key="page.lang.yes" />"></td>
            </form>

            <form action="/controller">
                <input type="hidden" name="command" value="toRoute">
                <td><input type="submit" class="btn btn-primary btn-default" value="<fmt:message key="page.lang.no" />"></td>
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
