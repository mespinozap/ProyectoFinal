package com.gimnasio.service;

import com.gimnasio.model.Usuario;
import com.gimnasio.repository.UsuarioRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository repo;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(
            UsuarioRepository repo,
            PasswordEncoder passwordEncoder) {

        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    // LISTAR USUARIOS
    public List<Usuario> listar() {
        return repo.findAll();
    }

    // BUSCAR POR ID
    public Usuario buscarPorId(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException( "Usuario no encontrado: " + id));
    }

    // CREAR USUARIO
    @Transactional
    public Usuario crear(Usuario usuario) {

        if (repo.existsByEmail(usuario.getEmail())) {
            throw new IllegalArgumentException(
                    "El correo ya está registrado");
        }

        if (repo.existsByCedula(usuario.getCedula())) {
            throw new IllegalArgumentException(
                    "La cédula ya está registrada");
        }

        if (usuario.getPassword() == null
                || usuario.getPassword().isBlank()) {

            throw new IllegalArgumentException(
                    "La contraseña es obligatoria");
        }

        usuario.setPassword(
                passwordEncoder.encode(
                        usuario.getPassword()));

        return repo.save(usuario);
    }

    // ACTUALIZAR USUARIO
    @Transactional
    public Usuario actualizar(Long id, Usuario datos) {

        Usuario existente = buscarPorId(id);

        if (!existente.getEmail().equals(datos.getEmail())
                && repo.existsByEmail(datos.getEmail())) {

            throw new IllegalArgumentException(
                    "El correo ya existe");
        }

        if (!existente.getCedula().equals(datos.getCedula())
                && repo.existsByCedula(datos.getCedula())) {

            throw new IllegalArgumentException(
                    "La cédula ya existe");
        }

        existente.setNombre(datos.getNombre());
        existente.setCedula(datos.getCedula());
        existente.setTelefono(datos.getTelefono());
        existente.setEmail(datos.getEmail());
        existente.setRol(datos.getRol());

        if (datos.getPassword() != null
                && !datos.getPassword().isBlank()) {

            existente.setPassword(
                    passwordEncoder.encode(
                            datos.getPassword()));
        }

        return repo.save(existente);
    }

    // DESACTIVAR USUARIO
    @Transactional
    public void desactivar(Long id) {

        Usuario usuario = buscarPorId(id);

        usuario.setActivo(false);

        repo.save(usuario);
    }
}