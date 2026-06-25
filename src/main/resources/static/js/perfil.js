document.addEventListener('DOMContentLoaded', function () {
    cargarPerfilDesdeBackend();
    actualizarNavbarDesdeBackend();
});
function previewImage(event) {
    const file = event.target.files[0];
    if (file) {
        // Pre-validación visual del tamaño
        if (file.size > 2 * 1024 * 1024) {
            Swal.fire('Imagen muy grande', 'Esa foto supera los 2MB.', 'info');
        }

        const reader = new FileReader();
        reader.onload = function(){
            document.getElementById('profile-pic').src = reader.result;
        };
        reader.readAsDataURL(file);
    }
}
function guardarPerfil() {
    const formData = new FormData();
    const username = document.getElementById("username").value.trim();
    const bio = document.getElementById("bio").value.trim();
    const email = document.getElementById("email").value;

    // Validación básica de nombre
    if (!username) {
        Swal.fire('Atención', 'El nombre no puede estar vacío', 'warning');
        return;
    }

    formData.append("username", username);
    formData.append("email", email);
    formData.append("bio", bio);

    const foto = document.getElementById("profile-image").files[0];

    if (foto) {
        // VALIDACIÓN DE PESO (Ejemplo: 2MB máximo)
        const pesoMaximo = 2 * 1024 * 1024; // 2MB en bytes
        if (foto.size > pesoMaximo) {
            Swal.fire({
                title: 'Foto muy pesada',
                text: 'La imagen debe pesar menos de 2MB. Por favor, elegí otra.',
                icon: 'error',
                confirmButtonColor: '#2e8b57'
            });
            return; // Corta la ejecución aquí
        }
        formData.append("profilePic", foto);
    }

    // Mostrar un "Cargando..." mientras se procesa
    Swal.fire({
        title: 'Actualizando...',
        text: 'Estamos guardando tus cambios en Organic Food',
        allowOutsideClick: false,
        didOpen: () => { Swal.showLoading(); }
    });

    fetch("/perfil/actualizar", {
        method: "POST",
        body: formData
    })
    .then(response => {
        if (response.ok) {
            Swal.fire({
                title: '¡Excelente!',
                text: 'Tu perfil se actualizó correctamente.',
                icon: 'success',
                confirmButtonColor: '#2e8b57'
            }).then(() => {
                actualizarNavbarDesdeBackend();
                window.location.href = "/index"; // Redirigir al éxito
            });
        } else {
            throw new Error("Error en la respuesta");
        }
    })
    .catch(error => {
        console.error("Error:", error);
        Swal.fire('Error', 'No pudimos actualizar tu perfil. Intentá más tarde.', 'error');
    });
}

function cargarPerfilDesdeBackend() {
    console.log("Función cargarPerfilDesdeBackend() ejecutándose...");
    fetch("/perfil/datos")
        .then(response => response.json())
        .then(data => {
            console.log("Datos recibidos del backend:", data); // Agrega este log
            document.getElementById("username").value = data.username;
            document.getElementById("email").value = data.email;
            document.getElementById("bio").value = data.bio;
            document.getElementById("role").value = data.rol;
            document.getElementById("register-date").value = data.fechaRegistro;
            // En cargarPerfilDesdeBackend() dentro de tu perfil.js
            if (data.profilePic) {
                // Si la data ya viene con el prefijo 'data:image...', se asigna directo
                document.getElementById("profile-pic").src = data.profilePic;
            } else {
                document.getElementById("profile-pic").src = '/imagenes/avatar-session.jpg';
            }
        })
        .catch(error => console.error("Error al cargar el perfil:", error));
}

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

function cerrarSesion() {
    window.location.href = "/logout";
}

// Funciones para mostrar mensajes (opcional, para una mejor experiencia de usuario)
function mostrarMensajeExito(mensaje) {
    const mensajeDiv = document.createElement('div');
    mensajeDiv.className = 'mensaje-exito';
    mensajeDiv.textContent = mensaje;
    document.querySelector('.profile-container').insertBefore(mensajeDiv, document.querySelector('.profile-info'));
    setTimeout(() => {
        mensajeDiv.remove();
    }, 3000); // El mensaje desaparece después de 3 segundos
}

function mostrarMensajeError(mensaje) {
    const mensajeDiv = document.createElement('div');
    mensajeDiv.className = 'mensaje-error';
    mensajeDiv.textContent = mensaje;
    document.querySelector('.profile-container').insertBefore(mensajeDiv, document.querySelector('.profile-info'));
    setTimeout(() => {
        mensajeDiv.remove();
    }, 5000); // El mensaje desaparece después de 5 segundos
}

// Función para redirigir al usuario
function redirigirUsuario(url) {
    window.location.href = url;
}