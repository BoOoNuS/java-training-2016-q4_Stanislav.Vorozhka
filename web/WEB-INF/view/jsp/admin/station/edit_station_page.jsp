<%@include file="/WEB-INF/view/jspf/common/teglib.jspf"%>
<%@include file="/WEB-INF/view/jspf/common/page_info.jspf"%>

<html>
<c:set var="title" value="Edit station" scope="page" />
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

    <%-- Update input --%>
    <table><br/>
        <form action="/controller" method="post">
            <input type="hidden" name="command" value="editStation">
            <input type="hidden" name="stationId" value="${requestScope.stationId}">
            <tr>
                <td><fmt:message key="page.lang.new.station.name" />:</td>
                <td><input name="stationName" minlength="5" maxlength="16" required></td>
            </tr>
            <tr>
                <td><input type="submit" class="btn btn-primary btn-default" value="<fmt:message key="page.lang.edit" />"></td>
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
