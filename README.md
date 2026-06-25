#  Organic Food - Sistema de Gestión de Chacras (Río Grande)

Este proyecto es una plataforma web desarrollada y diseñada para conectar a productores locales de Río Grande con la comunidad. 
Permite la gestión de publicaciones de chacras, un blog de noticias orgánicas y administración de usuarios.

## 🚀 Tecnologías Utilizadas

* **Backend:** Java 17 con **Spring Boot 3.3.5**.
* **Seguridad:** **Spring Security** (manejo de roles ADMINISTRADOR y USUARIO).
* **Persistencia:** **Spring Data JPA** con MySQL.
* **Frontend:** **Thymeleaf**, HTML5, CSS3 y JavaScript.
* **Base de Datos:** MySQL (XAMPP).

## 🛠️ Funcionalidades Principales

* **Gestión de Publicaciones (Soft-Delete):** Las publicaciones de las chacras no se eliminan físicamente; se marcan como `OFFLINE` para que desaparezcan de la vista pública pero permanezcan en los registros.
* **Blog de Noticias:** Panel de administración para crear, editar y "pausar" artículos del blog.
* **Carga de Imágenes en Base64:** Permite subir fotos de perfil y portadas de blog directamente desde la PC, almacenándolas de forma eficiente en la base de datos como `LONGTEXT`.
* **Carga Inicial Automática:** El sistema crea automáticamente un Administrador general vinculado a un perfil de Productor y datos de prueba (blogs y publicaciones) al iniciar por primera vez.

## 📋 Requisitos Previos

1.  **XAMPP** instalado con el servicio de MySQL activo.
2.  **Java 17** (JDK).
3.  **IntelliJ IDEA** (recomendado).

## ⚙️ Configuración e Instalación

1.  **Base de Datos:**
    * Crea una base de datos llamada `proyectochacras` en phpMyAdmin.
    * **IMPORTANTE:** Para permitir la carga de imágenes en Base64, ejecuta el siguiente comando en la pestaña SQL de phpMyAdmin:
        ```sql
        SET GLOBAL max_allowed_packet = 33554432;
        ```

2.  **Configuración del Proyecto:**
    * Clona el repositorio: `git clone https://github.com/federicobitsch/Organic-Food.git`
    * Importa el proyecto en IntelliJ como un proyecto Maven/Gradle.
    * Verifica los datos de conexión en `src/main/resources/application.properties`.

3.  **Ejecución:**
    * Corre la aplicación desde la clase `FoodOrganicApplication`.
    * Accede a: `http://localhost:8080`

## 🔐 Credenciales de Acceso (Admin Inicial)

* **Usuario:** `admin@admin.com`
* **Contraseña:** `admin123`

---

Desarrollado por **Federico Bitsch** - 2026.