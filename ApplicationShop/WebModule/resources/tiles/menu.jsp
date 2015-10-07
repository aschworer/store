<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/tiles" prefix="tiles" %>
<b>
    <c:out value="Menu:"/>
    <b>
        <br><br><br>
        <%--<c:set var="i" value="0"/>--%>
        <%--<c:forEach var='location' items="${sessionScope.locations}">--%>
        <%--<c:choose>--%>
        <%--<c:when test="${requestScope.currentLocation==location}">--%>
        <%--${locations[i]}<br><br>--%>
        <%--</c:when>--%>
        <%--<c:otherwise>--%>
        <%--<a href="${hrefs[i]}">${locations[i]}</a><br><br>--%>
        <%--</c:otherwise>--%>
        <%--</c:choose>--%>
        <%--<c:set var="i" value="${i+1}"/>--%>
        <%--</c:forEach>--%>

        <tiles:importAttribute/>

        <c:set var="i" value="0"/>
        <logic:iterate id="x" name="menu">

        <c:choose>
        <c:when test="${requestScope.currentLocation == x}">
            ${x}
        <br><br>
        </c:when>
        <c:otherwise>
        <a href="${hrefs[i]}">${x}</a><br><br>
        </c:otherwise>
        </c:choose>
            <c:set var="i" value="${i+1}"/>
        </logic:iterate>




