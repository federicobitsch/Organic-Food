document.addEventListener("DOMContentLoaded", () => {

    const form = document.getElementById("contact-form");
    const responseMessage = document.getElementById("response-message");

    form.addEventListener("submit", function (event) {
        event.preventDefault();

        const nombre = document.getElementById("name").value.trim();
        const email = document.getElementById("email").value.trim();
        const asunto = document.getElementById("subject").value.trim();
        const mensaje = document.getElementById("message").value.trim();

        // Reset estado mensaje
        responseMessage.style.display = "none";
        responseMessage.className = "";

        if (!nombre || !email || !asunto || !mensaje) {
            mostrarMensaje(
                "Todos los campos son obligatorios.",
                "error"
            );
            return;
        }

        // Simulación de envío (más adelante backend)
        mostrarMensaje("Enviando mensaje...", "success");

        setTimeout(() => {
            mostrarMensaje(
                "Mensaje enviado con éxito. Nuestro equipo de soporte se pondrá en contacto contigo a la brevedad.",
                "success"
            );
            form.reset();
        }, 1200);
    });

    function mostrarMensaje(texto, tipo) {
        responseMessage.textContent = texto;
        responseMessage.classList.add(tipo);
        responseMessage.style.display = "block";
    }
});
