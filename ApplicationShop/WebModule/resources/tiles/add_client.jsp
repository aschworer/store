<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<table width="60%" border="1" cellpadding="10" cellspacing="15">
    <b>Adding New Client</b><br><br>
    <tr>
        <td>
            <ul>
                <html:form action="/SubmitClient" focus="firstname">
                <li><B>Firstname:</b> <html:text property="firstname"/>
                    <br><br>
                <li><b>Lastname: </b><html:text property="lastname"/>
                    <br><br>
                <li><b>Company name (if buisness customer): </b><html:text property="company"/>
                    <br><br>
                <li><b>Advicer: </b><html:select property="advicer" indexed="0">
                    <html:option value="0">No advicer</html:option>
                    <c:forEach var="person" items="${requestScope.persons}">
                        <html:option value="${person.personID}">${person}</html:option>
                    </c:forEach>
                    </html:select>
                    <br><br>
                    <li><b>Discription: </b><br><html:textarea property="discription"/>

                        <html:submit value="Add person" title="Add person"/>
                        <html:reset value="Reset" title="Reset"/>
                    </html:form>
                    <html:errors/>
            </ul>
        </td>
    </tr>
</table>