package com.gimnasio.service;

import com.gimnasio.model.Usuario;
import com.gimnasio.repository.UsuarioRepository;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

@Service
public class UsuarioDetailsService implements UserDetailsService {

    private final UsuarioRepository repo;

    public UsuarioDetailsService(UsuarioRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        Usuario u = repo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario no existe: " + email));

        return User.withUsername(u.getEmail())
                .password(u.getPassword())
                .roles(u.getRol().name())
                .build();
    }
}
