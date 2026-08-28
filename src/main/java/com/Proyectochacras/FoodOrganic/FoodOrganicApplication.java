package com.Proyectochacras.FoodOrganic;

import com.Proyectochacras.FoodOrganic.models.*;
import com.Proyectochacras.FoodOrganic.repositories.BlogRepository;
import com.Proyectochacras.FoodOrganic.repositories.ProductorRepository;
import com.Proyectochacras.FoodOrganic.repositories.ChacraRepository;
import com.Proyectochacras.FoodOrganic.repositories.UsuarioRepository;
import com.Proyectochacras.FoodOrganic.service.UsuarioService; // Asegurate de que sea 'services' o 'service'
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.Proyectochacras.FoodOrganic")
public class FoodOrganicApplication implements CommandLineRunner {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private ChacraRepository chacraRepository;

	@Autowired
	private ProductorRepository productorRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private BlogRepository blogRepository;

	public static void main(String[] args) {
		SpringApplication.run(FoodOrganicApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		// ==========================================
		// 1. CREAR ADMIN GENERAL (Solo Usuario, sin Chacra)
		// ==========================================
		if (usuarioRepository.findByEmail("admin@admin.com").isEmpty()) {
			try {
				Usuario admin = new Usuario();
				admin.setNombreUsuario("Administrador");
				admin.setEmail("admin@admin.com");
				admin.setPassword("admin123"); // El UsuarioService debería encargarse de encriptar esto al guardar
				admin.setRole(Rol.ADMINISTRADOR);
				admin.setFotoPerfil("/imagenes/avatar-session.jpg");
				admin.setBio("Gestor principal de Organic Food.");

				usuarioService.saveUsuario(admin);
				System.out.println("****** ADMIN CREADO CON ÉXITO ******");
			} catch (Exception e) {
				System.out.println("Error al crear admin: " + e.getMessage());
			}
		}

		// ==========================================
		// 2. CREAR PRODUCTOR DE PRUEBA Y SU CHACRA
		// ==========================================
		if (usuarioRepository.findByEmail("productor1@correo.com").isEmpty()) {
			try {
				// A. Creamos el Usuario con Rol Productor
				Usuario userProductor = new Usuario();
				userProductor.setNombreUsuario("Juan Productor");
				userProductor.setEmail("productor1@correo.com");
				userProductor.setPassword("1234");
				userProductor.setRole(Rol.PRODUCTOR);

				// B. Creamos su perfil comercial
				Productor perfilProductor = new Productor();
				perfilProductor.setNombreCompleto("Juan Productor");
				perfilProductor.setTelefono("2964123456");
				perfilProductor.setDireccion("Ruta 3, Río Grande");
				perfilProductor.setDescripcion("Ventas de productos orgánicos locales.");

				// C. Los vinculamos y guardamos el usuario (Cascade guardará el productor)
				perfilProductor.setUsuario(userProductor);
				userProductor.setProductor(perfilProductor);
				usuarioService.saveUsuario(userProductor); // Usamos el service para que encripte la clave

				// D. Creamos su establecimiento (Chacra)
				Chacra chacra = new Chacra();
				chacra.setNombre("Chacra Bitsch");
				chacra.setDescripcion("Verduras y hortalizas frescas de Tierra del Fuego.");
				chacra.setUbicacion("Chacra 2");
				chacra.setEstadoChacra(EstadoChacra.DISPONIBLE); // CORREGIDO al nombre exacto
				chacra.setProductor(userProductor.getProductor());

				chacraRepository.save(chacra);
				System.out.println("****** PRODUCTOR Y CHACRA DE PRUEBA CREADOS ******");
			} catch (Exception e) {
				System.out.println("Error al crear productor: " + e.getMessage());
			}
		}

		// ==========================================
		// 3. CREAR BLOGS INICIALES
		// ==========================================
		if (blogRepository.count() == 0) {
			Usuario admin = usuarioRepository.findByEmail("admin@admin.com").orElse(null);

			if (admin != null) {
				Blog b1 = new Blog();
				b1.setTitulo("Beneficios de la Nutrición Orgánica");
				b1.setContenido("Descripcion Test");
				b1.setImagenUrl("/imagenes/index-organic.jpg");
				b1.setAutor(admin);
				b1.setActivo(true);

				Blog b2 = new Blog();
				b2.setTitulo("Producción local");
				b2.setContenido("Nuestra ciudad cuenta con productores comprometidos con la tierra. Conocer el origen de tus vegetales es el primer paso para una vida sana.");
				b2.setImagenUrl("/imagenes/comida-organica-3.jpg");
				b2.setAutor(admin);
				b2.setActivo(true);

				blogRepository.save(b1);
				blogRepository.save(b2);

				System.out.println("****** BLOGS INICIALES CREADOS ******");
			}
		}
	}
}