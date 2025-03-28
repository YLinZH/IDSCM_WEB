<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${empty sessionScope.user}">
    <c:redirect url="login.jsp" />
</c:if>
<html>
<head>
    <title>Reproduciendo: ${video.title}</title>
    <!-- Incluye el CSS y JS de VideoJS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://vjs.zencdn.net/7.11.4/video-js.css" rel="stylesheet" />
    <script src="https://vjs.zencdn.net/7.11.4/video.min.js"></script>
</head>
<body>
    <div class="container">
        <h1 class="text-center">Reproducción de video</h1>

        <!-- Añadir y cerrar sesión -->
        <div class="d-flex justify-content-between mb-3">
            <button class="btn btn-success" onclick="window.location.href='listadoVid.jsp'">Listado de Videos</button>
        </div>

        <div class="row">
            <c:choose>
                <c:when test="${not empty video}">
                    <div class="col-2">
                    </div>
                    <div class="col-8">
                        <h2>${video.title}</h2>
                        <!-- Reproductor VideoJS con ancho completo -->
                        <video id="my-video" class="video-js w-100" controls preload="auto" height="400"
                               data-setup="{}">
                            <source src="ServletVideoStream?path=${video.path}" type="video/${video.format}" />
                            <source src="ServletVideoStream?path=${video.path}" type="video/mp4" />
                            Tu navegador no soporta la reproducción de vídeo HTML5.
                        </video>
                    </div>
                    <div class="col-2">
                    </div>
                </c:when>
                <c:otherwise>
                    <c:redirect url="listadoVid.jsp" />
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
