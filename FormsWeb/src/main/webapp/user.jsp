
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="resources/assets/css/css.html"%>
        <link href="resources/assets/img/forms.png" rel="icon" type="image/png">
        <title>${user.user}</title>
    </head>
    <body>

        <!--Nav Bar-->
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
            <div class="container">
                <a class="navbar-brand" href="UserController?action=profile">
                    <img src="assets/img/forms.png" alt="Forms Web" width="30" height="30" class="d-inline-block align-text-top">
                    ${user.user}
                </a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="#">Home</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">Link</a>
                        </li>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                Acciones
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <li><a class="dropdown-item" href="UserController?action=signoff">Cerrar Sesión</a></li>
                            </ul>
                        </li>
                    </ul>
                    <form action="UserController" method="post"  class="d-flex">
                        <input class="form-control me-2" name="id-form" type="search" placeholder="Search" aria-label="Search" required>
                        <button class="btn btn-outline-success" name="action" value="searchForm" type="submit">Search</button>
                    </form>
                </div>
            </div>
        </nav>





        <section id="profile">
            <div class="container mt-4">
                <div class="card text-center">
                    <div class="card-header">
                        <h1 class="display-1 text-success">Forms Web</h1>
                    </div>
                    <div class="card-body">
                        <h5 class="card-title">Usuario: ${user.user}</h5>
                    </div>
                    <div class="card-footer text-muted">
                        <p class="card-text">Fecha de creaci&oacute;n: ${user.creationDate}</p>
                    </div>
                </div>
            </div>
        </section>

        <section id="my-forms">
            <div class="container my-4">
                <div class="row">
                    <div class="col">
                        <h2 class="text-center text-success">Mis formularios</h2>
                    </div>
                </div>

                <div class="row">
                    <div class="col">
                        <table class="table table-striped">
                            <thead>
                                <tr>
                                    <th scope="col">Id</th>
                                    <th scope="col">Nombre del Formulario</th>
                                    <th scope="col">Componentes</th>
                                    <th scope="col">Tema</th>
                                    <th scope="col">URL</th>
                                    <th scope="col">Fecha de Creacion</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${forms}" var="f">
                                    <tr>
                                        <th scope="row">
                                            <a class="btn btn-light" href="ExportForm?id-form=${f.id_form}" target="_blank" role="button">${f.id_form}</a>
                                        </th>
                                        <td>${f.name}</td>
                                        <td>${f.components.size()}</td>
                                        <td>${f.theme}</td>
                                        <td><a href="Form?id=${f.id_form}" target="_blank">http://localhost:8080/FormsWeb/Form?id=${f.id_form}</a></td>
                                        <td>${f.creationDate}</td>
                                    </tr>                                    
                                </c:forEach>
                            </tbody>
                        </table>
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
                modalP.textContent = "Gracias por responder el formulario, puedes seguir repondiendo más formularios o ir a nuestra aplicacion de escritorio para crear tu propio formulario.";

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
