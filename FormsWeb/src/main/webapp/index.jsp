
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="assets/css.html"%>
        <link href="assets/img/forms.png" rel="icon" type="image/png">
        <title>Forms Web</title>
    </head>
    <body>

        <nav class="navbar navbar-light bg-light">
            <div class="container">
                <a class="navbar-brand" href="#">
                    <img src="assets/img/forms.png" alt="Forms Web" width="30" height="30" class="d-inline-block align-text-top">
                    Forms Web
                </a>
            </div>
        </nav>

        <section>
            <div class="container">
                <div class="row align-items-center">
                    <div class="col-12 col-lg-6 my-4 py-4 bg-light">
                        <form action="UserController" method="post" class="text-center">
                            <div class="row">
                                <div class="col text-center">
                                    <img class="text-center" src="assets/img/login.png" width="35%">
                                </div>
                            </div>
                            <div class="form-floating mb-3">
                                <input type="text" class="form-control" id="floatingInput" name="user" placeholder="Usuario" required>
                                <label for="floatingInput">Usuario</label>
                            </div>
                            <div class="form-floating">
                                <input type="password" class="form-control" id="floatingPassword" name="password" placeholder="Password" required>
                                <label for="floatingPassword">Password</label>
                            </div>
                            <button class="btn btn-outline-success btn-lg my-4" type="submit" name="action" value="login">Iniciar Sesion</button>
                        </form>
                    </div>

                    <div class="col-12 col-lg-5 offset-lg-1 my-4 bg-light">
                        <form class="text-center my-4">
                            <h1 class="text-success">Formularios</h1>
                            <div class="form-floating mb-3">
                                <input type="text" class="form-control" id="floatingInput" placeholder="URL o ID">
                                <label for="floatingInput">URL o ID</label>
                            </div>
                            <button type="button" class="btn btn-outline-dark btn-lg">Buscar</button>
                        </form>
                    </div> 


                </div>
            </div>

            <div class="row">

            </div>
        </section>



        <%@include file="assets/js.html" %>
    </body>
</html>


<!--
<html>
<body>
    <h2>Jersey RESTful Web Application!</h2>
    <p><a href="webapi/myresource">Jersey resource</a>
    <p>Visit <a href="http://jersey.java.net">Project Jersey website</a>
    for more information on Jersey!
</body>
</html>
-->
