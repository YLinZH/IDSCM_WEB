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
                            <h5 class="modal-title" id="successModalLabel">Registration Successful</h5>
                        </div>
                        <div class="modal-body text-center">
                            Your account has been successfully created! Redirecting to login...
                        </div>
                    </div>
                </div>
            </div>
        `;
        document.body.appendChild(successPopup);

        // Después de 3 segundos, redirigir al login
        setTimeout(() => {
            window.location.href = "login.jsp"; 
        }, 2000);
    }
});
