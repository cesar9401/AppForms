
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="assets/css/css.html"%>
        <link href="assets/img/forms.png" rel="icon" type="image/png">
        <title>${form.title}</title>
    </head>
    <body>

        <!-- NavBar-->
        <jsp:include page="WEB-INF/nav.jsp"></jsp:include>

            <section id="form-title">
                <div class="container my-3 py-3 text-center">
                    <div class="row">
                        <div class="col">
                            <h1 class="fs-1 text">${form.title}</h1>
                    </div>
                </div>
                <div class="row">
                    <div class="col">
                        <h3>${form.name}</h3>
                    </div>
                </div>
            </div>
        </section>

        <section id="form-body">
            <div class="container">
                <div class="row">
                    <div class="col">
                        <form action="Form" method="post" enctype="multipart/form-data">
                            <c:forEach items="${form.components}" var="cm">
                                <div class="row">
                                    <div class="col-12 col-lg-10 offset-lg-1">
                                        <div class="mb-3">
                                            <label for="${cm.fieldName}" class="form-label">${cm.text}</label>
                                            <c:choose>
                                                <c:when test="${cm.kind == 'CAMPO_TEXTO'}">
                                                    <input type="text" class="form-control inpt" id="${cm.id_component}" name="${cm.id_component}" required="${cm.required}">
                                                </c:when>

                                                <c:when test="${cm.kind == 'AREA_TEXTO'}">
                                                    <br>
                                                    <textarea class="inpt" id="${cm.id_component}" name="${cm.id_component}" rows="${cm.rows}" cols="${cm.columns}" required="${cm.required}"></textarea>
                                                </c:when>

                                                <c:when test="${cm.kind == 'CHECKBOX'}">
                                                    <c:forEach items="${cm.options}" var="s">
                                                        <div class="form-check">
                                                            <input class="form-check-input inpt" type="checkbox" value="${s}" id="${s}" name="${cm.id_component}">
                                                            <label class="form-check-label" for="defaultCheck1">
                                                                ${s}
                                                            </label>
                                                        </div>
                                                    </c:forEach>
                                                </c:when>

                                                <c:when test="${cm.kind == 'RADIO'}">
                                                    <c:forEach items="${cm.options}" var="s">
                                                        <div class="form-check">
                                                            <input class="form-check-input inpt" type="radio" id="${cm.id_component}" name="${cm.id_component}" value="${s}" required="${cm.required}">
                                                            <label class="form-check-label" for="${cm.id_component}">
                                                                ${s}
                                                            </label>
                                                        </div>                                                
                                                    </c:forEach>
                                                </c:when>

                                                <c:when test="${cm.kind == 'FICHERO'}">
                                                    <input class="form-control inpt" type="file" name="${cm.id_component}" id="${cm.id_component}" required="${cm.required}">
                                                </c:when>

                                                <c:when test="${cm.kind == 'IMAGEN'}">
                                                    <div class="form-check">
                                                        <figure class="figure">
                                                            <img src="${cm.url}" class="figure-img img-fluid rounded" alt="${cm.text}">
                                                            <figcaption class="figure-caption">${cm.text}</figcaption>
                                                        </figure>
                                                    </div>    
                                                </c:when>

                                                <c:when test="${cm.kind == 'COMBO'}">
                                                    <select class="form-select inpt" aria-label="Default select example" id="${cm.id_component}" name="${cm.id_component}" required="${cm.required}">
                                                        <option value="" selected>Open this select menu</option>
                                                        <c:forEach items="${cm.options}" var="s">
                                                            <option value="${s}">${s}</option>
                                                        </c:forEach>
                                                    </select>
                                                </c:when>

                                                <c:when test="${cm.kind == 'BOTON'}">
                                                    <br>
                                                    <button type="button" class="btn btn-outline-primary btn-lg">${cm.text}</button>
                                                </c:when>
                                            </c:choose>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                            <div class="text-center">
                                <button class="btn btn-outline-success btn-lg my-4" type="submit" name="action" value="${form.id_form}">Enviar Respuesta</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </section>

        <section id="footer" class="p-3 mb-2 bg-dark text-white">
            <div class="container">
                <div class="row text-center">
                    <div class="col">
                        <p>By @${form.user_creation}</p>
                    </div>
                </div>
            </div>
        </section>

        <%@include file="assets/js/js.html" %>
        <c:if test="${form.theme == 'DARK'}">
            <script>
                let body = document.body;
                let nav = document.getElementById("nav");
                let list = document.querySelectorAll(".inpt");

                for (let i = 0; i < list.length; i++) {
                    list[i].classList.add("bg-secondary");
                    list[i].classList.add("text-white");
                }

                nav.classList.remove("navbar-light");
                nav.classList.remove("bg-light");

                nav.classList.add("navbar-dark");
                nav.classList.add("bg-dark");

                body.classList.add("bg-dark");
                body.classList.add("text-white");
            </script>
        </c:if>
    </body>
</html>
