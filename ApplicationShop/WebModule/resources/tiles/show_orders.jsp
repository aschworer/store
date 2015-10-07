<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/mytags" prefix="mt" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>

<c:choose>
    <c:when test="${empty requestScope.orders}">
        <b>No orders</b>
    </c:when>
    <c:otherwise>
        <table border="5" cellspacing="5" cellpadding="5" width='100%'>
        <tr bgcolor="grey">
            <td><b>ID</b>
            </td>
            <td><b>Client</b>
            </td>
            <td><b>Status</b>
            </td>
            <td><b>Date</b>
            </td>
            <td><b>Products</b>
            </td>
        </tr>
        <c:forEach var="ord" items="${requestScope.orders}">

            <tr>
                <td>
                        ${ord.orderID}
                </td>
                <td>
                        ${ord.client.lastname} ${ord.client.firstname}
                </td>
                <td>
                        ${ord.orderStatus}
                </td>
                <td>
                        ${ord.orderDate}
                </td>
                <td>
                    <c:forEach var="p" items="${ord.orderedProducts}">
                        <b>Title:</b> ${p.product.title}; <b>Price:</b> ${p.product.price};
                        <b>Amount:</b> ${p.amount}<br>
                    </c:forEach>
                </td>
            </tr>
        </c:forEach>
        </table>
        <br><br>
        <hr>

        <html:form action="/FilterOrders">
            <html:select property="date" indexed="0">
                <html:option value="0">No filtering</html:option>
                <c:forEach var="date" items="${requestScope.dates}">
                    <html:option value="${date}">${date}</html:option>
                </c:forEach>
            </html:select>
            <html:submit value="Filter" title="Filter"/>
        </html:form>


        <hr>
        <br><b>Report</b><br><br>

        <mt:printTable sourse="${requestScope.report}" fields="customerType,date,numberOfOrders,ordersSum"
                       headers="Type,Date,Number Of Orders,Orders Sum"/>
    </c:otherwise>
</c:choose>