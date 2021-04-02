
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="resources/assets/css/css.html"%>
        <link href="resources/assets/img/forms.png" rel="icon" type="image/png">
        <title>Forms Web</title>
    </head>
    <body>


        <!-- NavBar-->
        <jsp:include page="WEB-INF/nav.jsp"></jsp:include>

            <section>
                <div class="container">
                    <div class="row align-items-center">
                        <div class="col-12 col-lg-6 my-4 py-4 bg-light">
                            <form action="UserController" method="post" class="text-center">
                                <div class="row">
                                    <div class="col text-center">
                                        <img class="text-center" src="resources/assets/img/login.png" width="35%">
                                    </div>
                                </div>
                                <div class="form-floating mb-3">
                                    <input type="text" class="form-control" name="user" placeholder="Usuario" required>
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
                            <form action="UserController" method="post" class="text-center my-4">
                                <h1 class="text-success">Formularios</h1>
                                <div class="form-floating mb-3">
                                    <input type="text" class="form-control" id="id-form" name="id-form" placeholder="URL o ID" required>
                                    <label for="floatingInput">URL o ID</label>
                                </div>
                                <button type="submit" name="action" value="searchForm" class="btn btn-outline-dark btn-lg">Buscar</button>
                            </form>
                        </div> 


                    </div>
                </div>
            </section>

            <!-- Modal -->
            <div class="modal fade" id="modal-info" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="modal-title">Forms Web</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <p id="modal-p"></p>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        </div>
                    </div>
                </div>
            </div>

        <%@include file="resources/assets/js/js.html" %>

        <c:if test="${answer != null}">
            <script>
                let myModal = new bootstrap.Modal(document.getElementById('modal-info'), {focus: true});
                let modalTitle = document.getElementById("modal-title");
                let modalP = document.getElementById("modal-p");

                modalTitle.textContent = "Formulario Contestado";
                modalP.textContent = "Gracias por responder el formulario, puedes seguir repondiendo más formularios o iniciar sesión.";

                myModal.show();
            </script>
        </c:if>

        <c:if test="${error != null}">
            <script>
                let myModal = new bootstrap.Modal(document.getElementById('modal-info'), {focus: true});
                let modalTitle = document.getElementById("modal-title");
                let modalP = document.getElementById("modal-p");

                modalTitle.textContent = "Credenciales incorrectas";
                modalP.textContent = "Verifique que sus credenciales estén escritas correctamente.";

                myModal.show();
            </script>
        </c:if>

        <c:if test="${noMatch != null}">
            <script>
                let myModal = new bootstrap.Modal(document.getElementById('modal-info'), {focus: true});
                let modalTitle = document.getElementById("modal-title");
                let modalP = document.getElementById("modal-p");

                modalTitle.textContent = "Error en Busqueda";
                modalP.textContent = "No se encontro el formulario, verifique que la URL o Id ingresados sean los correctos.";

                myModal.show();
            </script>
        </c:if>

    </body>
</html>
