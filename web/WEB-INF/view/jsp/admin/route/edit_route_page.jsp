<%@include file="/WEB-INF/view/jspf/common/teglib.jspf"%>
<%@include file="/WEB-INF/view/jspf/common/page_info.jspf"%>

<html>
<c:set var="title" value="Edit route" scope="page" />
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

    <%-- Route edit form --%>
    <table><br/>
        <form action="/controller" method="post">
            <input type="hidden" name="command" value="editRoute">
            <input type="hidden" name="routeId" value="${requestScope.routeId}">
            <tr>
                <td><fmt:message key="page.lang.new.estimated.time.en.route" />:</td>
                <td><input type="time" name="travelTime" required></td>
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
