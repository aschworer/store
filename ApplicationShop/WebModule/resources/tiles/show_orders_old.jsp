<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
                            <b>Title:</b> ${p.title}; <b>Price:</b> ${p.price}; <b>Amount:</b> ${p.amountIfOrdered}<br>
                        </c:forEach>
                    </td>
                </tr>
            </c:forEach>
        </table>


    </c:otherwise>
</c:choose>