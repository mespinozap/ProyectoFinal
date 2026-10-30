package com.gimnasio.config;

import com.gimnasio.model.Rol;
import com.gimnasio.model.Usuario;
import com.gimnasio.repository.UsuarioRepository;
import com.gimnasio.service.UsuarioService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//Crear Usuarios para Poder Acceder al Sistema por primera vez 

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner cargarUsuarios(UsuarioRepository repo, UsuarioService service) {

        return args -> {
            if (repo.count() == 0) {
                service.crear(
                    new Usuario(
                        "Administrador",
                        "111111111",
                        "88888888",
                        "admin@gimnasio.com",
                        "admin123",
                        Rol.ADMIN
                    )
                );

                service.crear(
                    new Usuario(
                        "Carlos Cliente",
                        "222222222",
                        "77777777",
                        "cliente@gimnasio.com",
                        "cliente123",
                        Rol.CLIENTE
                    )
                );
            }
        };
    }
}
