
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="assets/css/css.html"%>
       	<link type="text/css" rel="stylesheet" href="assets/css/style404.css" />
        <link href="assets/img/forms.png" rel="icon" type="image/png">
        <title>Error</title>
    </head>
    <body>

        <div id="notfound">
            <div class="notfound">
                <div class="notfound-404">
                    <div></div>
                    <h1>404</h1>
                </div>
                <h2>Page not found</h2>
                <p>El formulario que busca no está disponible, revise si la url ingresada es la correcta o pregunte al autor del formulario si aún esta disponible.</p>
                <a href="index.jsp">home page</a>
            </div>
        </div>

        <%@include file="assets/js/js.html" %>
    </body>
</html>
