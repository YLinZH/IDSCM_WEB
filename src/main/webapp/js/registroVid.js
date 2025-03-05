// Función para manejar la selección del archivo de video
function handleFileSelect(event) {
    const fileInput = event.target;
    const file = fileInput.files[0];

    if (file) {
        const fileExtension = file.name.split('.').pop();
        const fechaCreacion = new Date().toLocaleString();

        const video = document.createElement('video');
        video.src = URL.createObjectURL(file);
        video.onloadedmetadata = function() {
            const duracion = video.duration.toFixed(2);
            document.getElementById('duracion').value = duracion;
            document.getElementById('formato').value = fileExtension;
            document.getElementById('reproducciones').value = 0;
        };
    }
}

// Función para mostrar el pop-up y redirigir después de un tiempo
document.addEventListener("DOMContentLoaded", function () {
    // Verifica si en la URL hay un parámetro "success"
    const urlParams = new URLSearchParams(window.location.search);
    if (urlParams.has('success')) {
        // Crear un pop-up con Bootstrap
        const successPopup = document.createElement("div");
        successPopup.innerHTML = `
            <div class="modal fade show" id="successModal" tabindex="-1" aria-labelledby="successModalLabel" style="display: block;" aria-modal="true">
                <div class="modal-dialog">
                    <div class="modal-content bg-success text-white">
                        <div class="modal-header text-center">
                            <h5 class="modal-title" id="successModalLabel">Video Registration Successful</h5>
                        </div>
                        <div class="modal-body text-center">
                            Redirecting to login...
                        </div>
                    </div>
                </div>
            </div>
        `;
        document.body.appendChild(successPopup);

        // Después de 2 segundos, redirigir al listado de videos
        setTimeout(() => {
            window.location.href = "listadoVid.jsp"; 
        }, 2000);
    }
});
