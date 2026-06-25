/*


// Verifica si el usuario es administrador (ejemplo)

function esAdmin() {

    return localStorage.getItem("rol") === "admin";

}



// Muestra la sección de creación si es admin

if (esAdmin()) {

    document.getElementById("admin-section").style.display = "block";

}

// Función para crear un blog
function crearBlog() {
    const titulo = document.getElementById("titulo").value;
    const contenido = document.getElementById("contenido").value;
    const imagenInput = document.getElementById("imagen");
    const imagenURL = imagenInput.files.length > 0 ? URL.createObjectURL(imagenInput.files[0]) : "";

    if (titulo && contenido) {
        const nuevoBlog = {
            id: Date.now(),
            titulo,
            contenido,
            imagen: imagenURL,
            fecha: new Date().toLocaleString(),
            comentarios: []
        };

        blogs.push(nuevoBlog);
        guardarBlogs();
        cargarBlogs();
        document.getElementById("titulo").value = "";
        document.getElementById("contenido").value = "";
        imagenInput.value = "";
    } else {
        alert("Por favor, completa todos los campos.");
    }
}

// Guarda los blogs en localStorage (simulación de base de datos)
function guardarBlogs() {
    localStorage.setItem("blogs", JSON.stringify(blogs));
}

// Carga los blogs al iniciar la página
function cargarBlogs() {
    const container = document.getElementById("blogs-container");
    container.innerHTML = "";
    blogs = JSON.parse(localStorage.getItem("blogs")) || [];

    blogs.forEach(blog => {
        const blogElement = document.createElement("div");
        blogElement.classList.add("blog");
        blogElement.innerHTML = `
            <h3>${blog.titulo}</h3>
            <p>${blog.contenido}</p>
            <img src="${blog.imagen}" alt="Imagen del blog">
            <p><small>Publicado el: ${blog.fecha}</small></p>
            <div class="comment-section">
                <h4>Comentarios</h4>
                <div id="comentarios-${blog.id}"></div>
                <input type="text" id="comentario-${blog.id}" placeholder="Escribe un comentario">
                <button onclick="agregarComentario(${blog.id})">Comentar</button>
            </div>
        `;

        container.appendChild(blogElement);
        mostrarComentarios(blog.id);
    });
}

// Agregar comentarios
function agregarComentario(blogId) {
    const comentarioInput = document.getElementById(`comentario-${blogId}`);
    const comentarioTexto = comentarioInput.value.trim();
    const usuario = localStorage.getItem("usuario") || "Anónimo";

    if (comentarioTexto) {
        const blog = blogs.find(b => b.id === blogId);
        blog.comentarios.push({
            id: Date.now(),
            usuario,
            texto: comentarioTexto,
            fecha: new Date().toLocaleString()
        });

        guardarBlogs();
        mostrarComentarios(blogId);
        comentarioInput.value = "";
    }
}

// Mostrar comentarios en cada blog
function mostrarComentarios(blogId) {
    const comentariosDiv = document.getElementById(`comentarios-${blogId}`);
    comentariosDiv.innerHTML = "";
    const blog = blogs.find(b => b.id === blogId);

    blog.comentarios.forEach(comentario => {
        const comentarioElement = document.createElement("div");
        comentarioElement.classList.add("comment");
        comentarioElement.innerHTML = `
            <p><strong>${comentario.usuario}</strong> (${comentario.fecha}): ${comentario.texto}</p>
            <button onclick="eliminarComentario(${blogId}, ${comentario.id})">Eliminar</button>
        `;
        comentariosDiv.appendChild(comentarioElement);
    });
}

// Eliminar comentario (solo administradores)
function eliminarComentario(blogId, comentarioId) {
    if (!esAdmin()) return;

    const blog = blogs.find(b => b.id === blogId);
    blog.comentarios = blog.comentarios.filter(c => c.id !== comentarioId);
    
    guardarBlogs();
    mostrarComentarios(blogId);
}

// Cerrar sesión
function cerrarSesion() {
    localStorage.removeItem("usuario");
    localStorage.removeItem("rol");
    window.location.href = "login"; //login.html
}

*/
