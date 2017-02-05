<%@include file="/WEB-INF/view/jspf/common/teglib.jspf" %>
<%@include file="/WEB-INF/view/jspf/common/page_info.jspf" %>

<html>
<c:set var="title" value="Main" scope="page"/>
<%@include file="/WEB-INF/view/jspf/common/head.jspf" %>
<body>

<%-- User info --%>
<div class="container">
    <div class="row">
        <div id="cssmenu">
            <ul>
                <c:if test="${sessionScope.user.role.name=='admin'}">
                    <%@include file="/WEB-INF/view/jspf/admin/admin_menu.jspf" %>
                </c:if>

                <c:if test="${sessionScope.user.role.name=='client'}">
                    <%@include file="/WEB-INF/view/jspf/common/user_identifier.jspf" %>
                </c:if>

                <%@include file="/WEB-INF/view/jspf/common/locale_chooser.jspf" %>

                <c:if test="${not empty sessionScope.user}">
                    <%@include file="/WEB-INF/view/jspf/common/logout_form.jspf" %>
                </c:if>
            </ul>
        </div>
    </div>
</div>

<br/>

<table align="center">
    <c:if test="${empty sessionScope.user}">
    <tr>
        <td><fmt:message key="page.lang.need.authenticate"/></td>
        <form action="/html/authentication.html">
            <td><input class="btn btn-primary btn-default" type="submit"
                       value="<fmt:message key="page.lang.sign.in" />"></td>
        </form>
    </tr>
    <tr>
        <td><fmt:message key="page.lang.create.account.if.have.no"/></td>
        <form action="/html/registration.html">
            <td><input class="btn btn-primary btn-default" type="submit"
                       value="<fmt:message key="page.lang.sign.up" />"></td>
        </form>
    </tr>
</table>
</c:if>

<br/>

<%-- Search input --%>
<table align="center"><br/>
    <tr>
        <td><fmt:message key="page.lang.from"/>:</td>
        <td><fmt:message key="page.lang.to"/>:</td>
    </tr>

    <tr>
        <form action="/controller">
            <input type="hidden" name="command" value="find">
            <td><input type="search" name="from"></td>
            <td><input type="search" name="to"></td>
            <td><input class="btn btn-primary btn-default" type="submit"
                       value="<fmt:message key="page.lang.find" />"></td>
        </form>
    </tr>
</table>

<%-- Search output --%>
<u:search routesInfo="${requestScope.routesInfo}" user="${sessionScope.user}"/>

<%-- Footer --%>
<%@include file="/WEB-INF/view/jspf/common/footer.jspf" %>
</body>
</html>
