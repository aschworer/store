<%@ page contentType="text/html;charset=cp1251" language="java" %>
<%@ taglib uri="/tags/tiles" prefix="tiles" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<HTML>
<HEAD>
    <TITLE>
        <tiles:getAsString name="title"/>
    </TITLE>
    <link rel="stylesheet" type="text/css" href="<html:rewrite page='/tiles/index.css'/>">
</HEAD>
<BODY>
<%--<tiles:insert attribute='header'/>--%>
<tiles:insert component="tiles/header.jsp"/>
<hr>
<table width="100%" height="70%">
    <tr>
        <td width="12%" valign="top">
            <%--<tiles:insert attribute='menu'/>--%>
            <tiles:insert component="tiles/menu.jsp">
                <tiles:putList name="menu">
                    <tiles:add value="Hello"/>
                    <tiles:add value="Show Orders"/>
                    <tiles:add value="Show Clients"/>
                    <tiles:add value="Add Order"/>
                    <tiles:add value="Add Client"/>
                </tiles:putList>
            </tiles:insert>
        </td>
        <td valign="top" width="70%">
            <tiles:insert attribute='body'/>
        </td>
    </tr>
</table>
<!--<hr>-->
<%--<tiles:insert attribute='footer'/>--%>
<tiles:insert component="tiles/footer.jsp"/>
<%--<tiles:importAttribute/>--%>
<%--<logic:iterate id="menu" name="menu">--%>
<%--</logic:iterate>--%>
</BODY>
</HTML>