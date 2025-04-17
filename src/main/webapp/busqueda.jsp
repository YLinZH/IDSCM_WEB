<% 
    String usuario = (String) session.getAttribute("user");
    if (usuario == null) {
        response.sendRedirect("login.jsp"); // Redirige al login si no hay sesión
    }
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Búsqueda de Videos</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h1>Buscar vídeos</h1>
    <form method="post" action="ServletREST" class="mb-4">
        <div class="mb-3">
            <label for="tipoBusqueda">Buscar por:</label>
            <select name="tipoBusqueda" id="tipoBusqueda" class="form-select">
                <option value="titulo" <c:if test="${tipoBusqueda == 'titulo'}">selected</c:if>>Título</option>
                <option value="autor" <c:if test="${tipoBusqueda == 'autor'}">selected</c:if>>Autor</option>
                <option value="fecha" <c:if test="${tipoBusqueda == 'fecha'}">selected</c:if>>Fecha</option>
            </select>
        </div>
        <div class="mb-3">
            <input type="text" name="criterio" class="form-control" value="${criterio}" placeholder="Escribe aquí el texto de búsqueda (Si es la fecha el formato tiene que ser yyyy o mm/yyyy o dd/mm/yyyy)" />
        </div>
        <button type="submit" class="btn btn-primary">Buscar</button>
        <a href="busqueda.jsp" class="btn btn-secondary ms-2">Limpiar filtros</a>
    </form>

    <c:if test="${not empty criterio}">
        <p class="text-muted">Resultados para la búsqueda por <strong>${tipoBusqueda}</strong> con el valor <strong>${criterio}</strong>:</p>

        <c:choose>
            <c:when test="${not empty videos}">
                <h2>Resultados:</h2>
                <table class="table table-bordered table-striped">
                    <thead>
                        <tr>
                            <th>Título</th>
                            <th>Autor</th>
                            <th>Fecha</th>
                            <th>Duración</th>
                            <th>Reproducciones</th>
                            <th>Formato</th>
                            <th>Descripción</th>
                            <th>Ruta</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="v" items="${videos}">
                            <tr>
                                <td>${v.title}</td>
                                <td>${v.author}</td>
                                <td>${v.creationDate}</td>
                                <td>${v.duration}</td>
                                <td>${v.views}</td>
                                <td>${v.format}</td>
                                <td>${v.description}</td>
                                <td>${v.path}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <div class="alert alert-warning mt-4">
                    No se encontraron vídeos que coincidan con tu búsqueda.
                </div>
            </c:otherwise>
        </c:choose>
    </c:if>

</div>
</body>
</html>


