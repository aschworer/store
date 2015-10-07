<%@ page contentType="text/html;charset=cp1251" language="java" %>
<%@ page isErrorPage="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="tiles/index.css">
    <title>Error occured</title></head>
<body><b><H1>Some error occured during processing your request.</h1></b>
${pageContext.exception.cause}
<br>
</body>
</html>