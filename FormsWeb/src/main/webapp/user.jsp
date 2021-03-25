
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="assets/css/css.html"%>
        <link href="assets/img/forms.png" rel="icon" type="image/png">
        <title>${user.user}</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <h1>${user.user}</h1>
        <h1>${user.creationDate}</h1>

        <%@include file="assets/js/js.html" %>
    </body>
</html>
