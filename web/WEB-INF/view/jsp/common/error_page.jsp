<%@include file="/WEB-INF/view/jspf/common/teglib.jspf" %>
<%@include file="/WEB-INF/view/jspf/common/page_info.jspf" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="locale"/>

<html>
<c:set var="title" value="Error page" scope="page"/>
<%@include file="/WEB-INF/view/jspf/common/head.jspf" %>
<body>

<table>
    <tr>
        <td>
            <h2><fmt:message key="page.lang.following.error.occurred"/></h2>
            <hr/>
        </td>
    </tr>
    <c:if test="${not empty sessionScope.error}">
        <tr>
            <td>
                    ${sessionScope.error.message}
                <hr/>
            </td>
        </tr>
        <c:if test="${sessionScope.user.role.name == 'admin'}">
            <c:forEach items="${sessionScope.error.stackTrace}" var="trace" end="10">
                <tr>
                    <td>${pageScope.trace}</td>
                </tr>
            </c:forEach>
        </c:if>
    </c:if>
</table>


<%-- Footer --%>
<%@include file="/WEB-INF/view/jspf/common/footer.jspf" %>
</body>
</html>
