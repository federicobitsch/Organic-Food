function actualizarNavbarDesdeBackend() {
    fetch("/perfil/navbar")
        .then(response => response.json())
        .then(data => {
            const navUsername = document.getElementById('nav-username');
            const navProfilePic = document.getElementById('nav-profile-pic');

            if (data.username && navUsername) {
                navUsername.textContent = data.username;
            }
            if (data.profilePic && navProfilePic) {
                navProfilePic.src = data.profilePic;
            }
        })
        .catch(error => console.error("Error al obtener datos del navbar:", error));
}

document.addEventListener('DOMContentLoaded', function() {
    actualizarNavbarDesdeBackend(); // Llama a la función al cargar la página
});