<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<h3>Welcome to Shop Application</h3><br>
<b><br>Current sessions:<br></b>
<%--<c:forEach var="x" items="${applicationScope.sessions}">--%>
    <logic:iterate id="x" name="sessions">
    <p>
        id: ${x.key}<br>
        Creation time: ${x.value}<br>
    </p>
</logic:iterate>