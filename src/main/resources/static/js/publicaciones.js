// Variable global para configurar si usamos imágenes o no por ahora
const USAR_IMAGENES = false;

async function cargarPublicaciones() {
    try {
        const r = await fetch('/api/publicaciones');
        if (!r.ok) throw new Error("No se pudo obtener la lista");

        const data = await r.json();
        const container = document.getElementById('publicaciones-container');
        if (!container) return;

        container.innerHTML = '';

        // Estilos de Marketplace fluido (Grid)
        container.style.display = 'grid';
        container.style.gridTemplateColumns = 'repeat(auto-fill, minmax(280px, 1fr))';
        container.style.gap = '25px';
        container.style.padding = '20px';

        data.forEach(p => {
            const div = document.createElement('div');

            // Estilos de la tarjeta (Card) tipo Marketplace
            div.style.border = '2px solid #2e7d32'; // Borde verde como en tu diseño
            div.style.borderRadius = '12px';
            div.style.backgroundColor = '#ffffff';
            div.style.boxShadow = '0 6px 12px rgba(0,0,0,0.08)';
            div.style.overflow = 'hidden';
            div.style.cursor = 'pointer'; // Manito al pasar el mouse
            div.style.transition = 'transform 0.2s, box-shadow 0.2s';

            // Efecto Hover (se levanta la tarjeta al pasar el mouse)
            div.onmouseover = () => {
                div.style.transform = 'translateY(-5px)';
                div.style.boxShadow = '0 12px 20px rgba(0,0,0,0.15)';
            };
            div.onmouseout = () => {
                div.style.transform = 'translateY(0)';
                div.style.boxShadow = '0 6px 12px rgba(0,0,0,0.08)';
            };

            // Evento Click: Redirige al detalle de la chacra (Podés crear esta vista después)
            div.onclick = (e) => {
                // Evitamos redirigir si el click fue en los botones de Admin
                if (!e.target.closest('.admin-actions')) {
                    window.location.href = `/chacra/${p.id}`;
                }
            };

            let htmlContent = '';

            // Lógica de imágenes (Desactivada por ahora como pediste)
            if (USAR_IMAGENES && p.imagenPrincipal) {
                htmlContent += `<img src="${p.imagenPrincipal}" alt="${p.nombre}" style="width: 100%; height: 180px; object-fit: cover;">`;
            } else {
                // Encabezado decorativo si no hay imagen
                htmlContent += `<div style="height: 10px; background-color: #2e7d32; width: 100%;"></div>`;
            }

            // Cuerpo de la tarjeta (Textos)
            htmlContent += `
                <div style="padding: 20px;">
                    <h3 style="margin: 0 0 10px 0; color: #1b5e20; font-size: 1.4rem;">${p.nombre}</h3>
                    <p style="color: #666; font-size: 0.95rem; margin-bottom: 15px; min-height: 40px;">${p.descripcion}</p>
                    <p style="margin: 5px 0; font-size: 0.9rem; color: #333;"><strong> Ubicación:</strong><br>${p.ubicacion}</p>
                    <p style="margin: 15px 0 0 0; font-size: 0.9rem;">
                        <strong>Estado:</strong>
                        <span style="color: #2e7d32; font-weight: bold; text-transform: uppercase;">${p.estadoChacra}</span>
                    </p>
                </div>
            `;

            // Botones de Administrador / Productor (Solo visibles si es la ruta /admin)
            if (window.location.pathname.includes('/admin')) {
                htmlContent += `
                    <div class="admin-actions" style="padding: 15px; background-color: #f1f8e9; border-top: 1px solid #c8e6c9; display: flex; justify-content: space-between;">
                        <button onclick="prepararEdicion(${p.id}, '${p.nombre}', '${p.descripcion}', '${p.ubicacion}', '${p.estadoChacra}')" style="background-color: #0277bd; color: white; border: none; padding: 8px 12px; border-radius: 4px; cursor: pointer; font-weight: bold;">Editar</button>
                        <button onclick="eliminarPublicacion(${p.id})" style="background-color: #d32f2f; color: white; border: none; padding: 8px 12px; border-radius: 4px; cursor: pointer; font-weight: bold;">Eliminar</button>
                    </div>
                `;
            }

            div.innerHTML = htmlContent;
            container.appendChild(div);
        });
    } catch (e) {
        console.error("Error al cargar:", e);
        const container = document.getElementById('publicaciones-container');
        if (container) container.innerHTML = '<p style="color: red; padding: 20px;">No se pudieron cargar las chacras. Verifique la conexión al servidor.</p>';
    }
}

async function guardarPublicacion(event) {
    if (event) event.preventDefault(); // Evita que el formulario recargue la página

    const nombreInput = document.getElementById('nombreChacra').value.trim();
    if (!nombreInput) { alert("El nombre de la chacra es obligatorio."); return; }

    try {
        // Validación de nombres duplicados
        const responseList = await fetch('/api/publicaciones');
        const publicaciones = await responseList.json();

        if (Array.isArray(publicaciones)) {
            const existe = publicaciones.some(p => p.nombre.toLowerCase() === nombreInput.toLowerCase());
            if (existe) {
                alert("¡Error! Ya tienes registrada una chacra con ese nombre.");
                return;
            }
        }

        // Construimos los datos (Sin procesar imagen Base64 por ahora)
        const data = {
            nombre: nombreInput,
            descripcion: document.getElementById('descripcion').value,
            ubicacion: document.getElementById('ubicacionChacra').value,
            estadoChacra: document.getElementById('estadoChacra').value,
            imagenPrincipal: null
        };

        const responseCreate = await fetch('/api/publicaciones/crear', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(data)
        });

        if(responseCreate.ok) {
            alert("¡Chacra publicada exitosamente en Organic Food!");
            cargarPublicaciones();

            // Limpiar formulario
            document.getElementById('nombreChacra').value = "";
            document.getElementById('descripcion').value = "";
            document.getElementById('ubicacionChacra').value = "";
            document.getElementById('estadoChacra').value = "DISPONIBLE"; // Reset a default
        } else {
            alert("Error al guardar: " + await responseCreate.text());
        }

    } catch (error) {
        console.error("Error:", error);
        alert("Ocurrió un error de conexión con el servidor.");
    }
}

async function eliminarPublicacion(id) {
    if (!confirm('¿Estás seguro de que querés dar de baja esta chacra? Esta acción no se puede deshacer.')) return;
    try {
        const response = await fetch(`/api/publicaciones/eliminar/${id}`, { method: 'DELETE' });
        if (response.ok) {
            alert("Chacra eliminada del marketplace.");
            cargarPublicaciones();
        } else {
            alert("Error interno al intentar eliminar la chacra.");
        }
    } catch (error) { console.error("Error en la conexión:", error); }
}

// Cargar las chacras apenas inicie la página
document.addEventListener("DOMContentLoaded", cargarPublicaciones);