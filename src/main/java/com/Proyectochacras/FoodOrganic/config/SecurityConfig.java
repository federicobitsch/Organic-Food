package com.Proyectochacras.FoodOrganic.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

                        // Archivos estáticos y multimedia
                        .requestMatchers("/css/**", "/js/**", "/imagenes/**", "/uploads/**", "/static/**").permitAll()

                        // Rutas públicas
                        .requestMatchers("/", "/index", "/login", "/register","/blog/**",
                                "/contactoSoporte","/productosOrganicos","/publicaciones","/api/publicaciones/**").permitAll()

                        // Sección de blog pública (solo ver)
                        .requestMatchers("/blog", "/blog/{id}", "/blog/detalle/**").permitAll()

                        // Acciones del blog (solo admin)
                        .requestMatchers("/blog/nuevo", "/blog/editar/**", "/blog/eliminar/**")
                        .hasRole("ADMINISTRADOR")

                        // Panel admin completo
                        .requestMatchers("/admin/**","/admin/blogs/**")
                        .hasRole("ADMINISTRADOR")

                        // Perfil requiere login
                        .requestMatchers("/perfil/**").authenticated()

                        //Todo lo demas requiere login
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/index", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        return http.build();
    }
}
