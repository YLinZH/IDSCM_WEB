<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="models.Video" %> 
<% 
    String usuario = (String) session.getAttribute("user");
    if (usuario == null) {
        response.sendRedirect("login.jsp"); // Redirige al login si no hay sesión
    }
%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Listado de Videos</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="css/listadoVid.css">
    </head>
    <body>

        <div class="container">
            <h1 class="text-center">Listado de Videos</h1>

            <!-- Añadir y cerrar sesion -->
            <div class="d-flex justify-content-between mb-3">
                <!-- Botón para añadir video -->
                <button class="btn btn-success" onclick="window.location.href='registroVid.jsp'">Añadir Video</button>
                <!-- Botón para cerrar sesión -->
                <form action="logout" method="post">
                    <button class="btn btn-danger">Cerrar sesión</button>
                </form>
            </div>
            
            <div class="row">
                <%

                    if (request.getAttribute("videos") == null) {
                        response.sendRedirect("listadoVideos");
                        return;
                    }

                    List<Video> listaVideos = (List<Video>) request.getAttribute("videos"); // Obtener la lista desde el request
                    if (listaVideos != null && !listaVideos.isEmpty()) { // Verificar que la lista no sea null ni esté vacía
                        for (Video video : listaVideos) {
                %>
                            <div class="col-md-4">
                                <div class="card">
                                    <div class="card-body">
                                        <h5 class="card-title"><%= video.getTitle() %></h5>
                                        <p class="card-text"><%= video.getAuthor() %></p>
                                        <p class="card-text"><%= video.getDescription() %></p>
                                    </div>
                                </div>
                            </div>
                <%
                        }
                    } else { 
                %>
                    <!-- Mostrar mensaje si no hay videos -->
                    <div class="col-12 text-center">
                        <p class="alert alert-warning">No hay videos disponibles.</p>
                    </div>
                <%
                    }
                %>
            </div>
        </div>

        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

    </body>
</html>
