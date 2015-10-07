<%@ taglib uri="/mytags" prefix="mt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/tiles" prefix="tiles" %>

<%--<tiles:importAttribute/>--%>
<%--<mt:printTable sourse="persons" fields="lastname,firstname,advicer"--%>
<!--headers="LASTNAME,FIRSTNAME,ADVICER"/>-->
<%--<logic:present name="persons">--%>
<%--<mt:printTable sourse="persons" fields="lastname,firstname,advicer"--%>
<!--headers="LASTNAME,FIRSTNAME,ADVICER"/>-->
<%--</logic:present>--%>
<!--fgsdfdsfg;kj-->

<c:choose>
    <c:when test="${empty requestScope.persons}">
        <b>No clients</b>
    </c:when>
    <c:otherwise>

        <%--<%@ include file="menu.jsp" %>--%>

        <%--count: ${requestScope.pageNum}--%>

        <c:forEach var="i" begin="1" end="${requestScope.pagesNum}">
            <%--<c:choose>--%>
                <%--<c:when test="${requestScope.pageNum+1==i}">--%>
                    <%--[${i}]--%>
                <%--</c:when>--%>
                <%--<c:otherwise>--%>
                    <a href="/webmodule/ShowClients.do?step=${requestScope.step}&pageNum=${i-1}">[${i}]</a>
                <%--</c:otherwise>--%>
            <%--</c:choose>--%>
        </c:forEach>

        <br><br><br>
        <mt:printTable sourse="${requestScope.persons}" fields="personID,type,lastname,firstname,advicer,discription"
                       headers="ID,Type,Lastname,Firstname,Advicer,Discription"/>
        <%--<mt:printTable sourse="persons" fields="lastname,firstname,advicer"--%>
        <!--headers="LASTNAME,FIRSTNAME,ADVICER"/>-->
    </c:otherwise>
</c:choose>