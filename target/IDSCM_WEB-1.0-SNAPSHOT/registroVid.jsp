<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page session="true" %>
<%
    // Verificar si el usuario está logueado
    String username = (String) session.getAttribute("user");
    if (username == null) {
        // Si el usuario no está logueado, redirigir a la página de login
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Registro de Videos</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="css/registroVid.css">
    </head>
    <body>

        <div class="container">
            <h1 class="text-center">Registro de Videos</h1>

            <!-- Añadir y cerrar sesión -->
            <div class="d-flex justify-content-between mb-3">
                <!-- Botón para volver a la página de listado de Videos -->
                <button class="btn btn-success" onclick="window.location.href='listadoVid.jsp'">Listado de Videos</button>

                <!-- Botón para cerrar sesión -->
                <form action="logout" method="post">
                    <button class="btn btn-danger">Cerrar sesión</button>
                </form>
            </div>
            <div class="d-flex justify-content-center">
                <div class="card p-4" style="width: 100%; max-width: 50%;">
                    <!-- Mensaje de error -->
                    <% 
                        String errorMessage = (String) request.getAttribute("error");
                        if (errorMessage != null) { 
                    %>
                        <div class="alert alert-danger text-center" role="alert">
                            <%= errorMessage %>
                        </div>
                    <% } %>
                    <form action="registroVideo" method="post" enctype="multipart/form-data">

                        <div class="mb-3">
                            <label for="titulo" class="form-label">Título</label>
                            <input type="text" class="form-control" id="titulo" name="titulo" required>
                        </div>

                        <div class="mb-3">
                            <label for="autor" class="form-label">Autor</label>
                            <input type="text" class="form-control" id="autor" name="autor" required>
                        </div>

                        <div class="mb-3">
                            <label for="descripcion" class="form-label">Descripción</label>
                            <textarea class="form-control" id="descripcion" name="descripcion" rows="3" required></textarea>
                        </div>                   
                        
                        <div class="mb-3">
                            <label for="video" class="form-label">Upload Video</label>
                            <input type="file" class="form-control" id="video" name="video" accept="video/*" required onchange="handleFileSelect(event)">
                        </div>
                        
                        <div class="mb-3">
                            <label for="duracion" class="form-label">Duración (segundos)</label>
                            <input type="number" class="form-control" id="duracion" name="duracion" readonly required>
                        </div>

                        <div class="mb-3">
                            <label for="formato" class="form-label">Formato</label>
                            <input type="text" class="form-control" id="formato" name="formato" readonly required>
                        </div>

                        <button type="submit" class="btn btn-primary">Registrar Video</button>
                    </form>
                </div>
            </div>

        </div>

        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
        <script src="js/registroVid.js"></script>
    </body>
</html>
