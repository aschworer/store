<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<b>Adding New Order</b><br><br>

<c:choose>
    <c:when test="${empty requestScope.products}">
        <b>No products</b>
    </c:when>
    <c:otherwise>


        <table width="60%" border="1" cellpadding="10" cellspacing="15">
            <tr>
                <td>
                    <c:set var="clientID" value="0"/>
                    <ul>
                        <html:form action="/SubmitOrder">
                        <b>
                            <li>Client:
                        </b>
                        <html:select property="clientID">
                            <c:set var="maxID" value="0"/>
                            <c:forEach var="person" items="${requestScope.persons}">
                                <c:choose>
                                    <c:when test="${person.personID > maxID}">
                                        <c:set var="maxID" value="${person.personID}"/>
                                    </c:when>
                                </c:choose>
                            </c:forEach>


                            <c:forEach var="person" items="${requestScope.persons}">
                                ${person.personID}
                                <c:choose>
                                    <c:when test="${person.personID == maxID && 'none'==requestScope.error}">
                                        <html:option
                                                value="${person.personID}">${person.lastname} ${person.firstname}</html:option>
                                    </c:when>
                                    <c:when test="${person.personID == clientID}">
                                        <html:option
                                                value="${person.personID}">${person.lastname} ${person.firstname}</html:option>
                                    </c:when>
                                    <c:when test="${!(person.personID==clientID && person.personID==maxID)}">
                                        <html:option
                                                value="${person.personID}">${person.lastname} ${person.firstname}</html:option>
                                    </c:when>
                                </c:choose>
                            </c:forEach>

                        </html:select>
                        <br>
                        <br>
                        <table width="100%">
                            <tr>
                                <td>
                                    <li><b>Products:</b>
                                </td>
                                <td>
                                    <li><b>Amount:</b>
                                </td>
                            </tr>
                            <logic:iterate id="item" name="products" indexId="index">
                                <tr>
                                    <td>

                                        <bean:write name="item" property="productID"/>,
                                        <b>
                                            <bean:write name="item" property="title"/></b>,
                                        <bean:write name="item" property="price"/>
                                    </td>
                                    <td>
                                        <html:hidden name="products" property="id" indexed="true"
                                                     value="${item.productID}"/>
                                        <html:text property="products[${index}].amount"/>
                                    </td>
                                </tr>
                            </logic:iterate>

                        </table>
                        <br><br>
                        <li><b>Status: </b><html:select property="status">
                            <html:option value="new">New</html:option>
                            <html:option value="in_pack">Packing</html:option>
                            <html:option value="delivering">Delivering</html:option>
                            <html:option value="complete">Complete</html:option>
                            </html:select>
                            <br><br>
                            <br><html:submit title="Add" value="Add Order"/>
                                <html:reset value="Reset" title="Reset"/>
                            </html:form>
                                <html:errors/>
                    </ul>
                </td>
            </tr>
        </table>


    </c:otherwise>
</c:choose>

