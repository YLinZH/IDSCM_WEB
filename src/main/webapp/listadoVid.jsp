<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Listado de Videos</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body {
                background-color: #f8f9fa;
            }
            .container {
                margin-top: 30px;
            }
            .video-card {
                margin-bottom: 20px;
            }
        </style>
    </head>
    <body>

        <div class="container">
            <h1 class="text-center">Listado de Videos</h1>

            <!-- Botón para cerrar sesión -->
            <div class="text-end mb-3">
                <form action="logout" method="post">
                    <button class="btn btn-danger">Cerrar sesión</button>
                </form>
            </div>

            <!-- Ejemplo de listado de videos -->
            <div class="row">
                <div class="col-md-4">
                    <div class="card video-card">
                        <img src="https://via.placeholder.com/300x200" class="card-img-top" alt="Video 1">
                        <div class="card-body">
                            <h5 class="card-title">Video 1</h5>
                            <p class="card-text">Descripción del video 1.</p>
                            <a href="#" class="btn btn-primary">Ver Video</a>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card video-card">
                        <img src="https://via.placeholder.com/300x200" class="card-img-top" alt="Video 2">
                        <div class="card-body">
                            <h5 class="card-title">Video 2</h5>
                            <p class="card-text">Descripción del video 2.</p>
                            <a href="#" class="btn btn-primary">Ver Video</a>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card video-card">
                        <img src="https://via.placeholder.com/300x200" class="card-img-top" alt="Video 3">
                        <div class="card-body">
                            <h5 class="card-title">Video 3</h5>
                            <p class="card-text">Descripción del video 3.</p>
                            <a href="#" class="btn btn-primary">Ver Video</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

    </body>
</html>
