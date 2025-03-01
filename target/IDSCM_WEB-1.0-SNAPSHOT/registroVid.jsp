<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Registro de Videos</title>
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
            <h1 class="text-center">Registro de Videos</h1>

            <!-- Añadir y cerrar sesion -->
            <div class="d-flex justify-content-between mb-3">
                <!-- Botón para añadir video -->
                <button class="btn btn-success" onclick="window.location.href='listadoVid.jsp'">Listado de Videos</button>

                <!-- Botón para cerrar sesión -->
                <form action="logout" method="post">
                    <button class="btn btn-danger">Cerrar sesión</button>
                </form>
            </div>
            <div class="d-flex justify-content-center">
                <div class="card p-4" style="width: 100%; max-width: 50%;">
                    <form>
                        <div class="mb-3">
                            <label for="titulo" class="form-label">Título</label>
                            <input type="text" class="form-control" id="titulo" required>
                        </div>
                        <div class="mb-3">
                            <label for="autor" class="form-label">Autor</label>
                            <input type="text" class="form-control" id="autor" required>
                        </div>
                        <div class="mb-3">
                            <label for="fecha" class="form-label">Fecha de creación</label>
                            <input type="date" class="form-control" id="fecha" required>
                        </div>
                        <div class="mb-3">
                            <label for="duracion" class="form-label">Duración (minutos)</label>
                            <input type="number" class="form-control" id="duracion" required>
                        </div>
                        <div class="mb-3">
                            <label for="reproducciones" class="form-label">Número de reproducciones</label>
                            <input type="number" class="form-control" id="reproducciones" required>
                        </div>
                        <div class="mb-3">
                            <label for="descripcion" class="form-label">Descripción</label>
                            <textarea class="form-control" id="descripcion" rows="3" required></textarea>
                        </div>
                        <div class="mb-3">
                            <label for="formato" class="form-label">Formato</label>
                            <select class="form-select" id="formato" required>
                                <option value="mp4">MP4</option>
                                <option value="avi">AVI</option>
                                <option value="mkv">MKV</option>
                                <option value="mov">MOV</option>
                            </select>
                        </div>
                        <button type="submit" class="btn btn-primary">Registrar Video</button>
                    </form>
                </div>
            </div>
        </div>

        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

    </body>
</html>
