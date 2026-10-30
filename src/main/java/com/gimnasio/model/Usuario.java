package com.gimnasio.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es requerido")
    @Size(max = 100)
    private String nombre;

    @NotBlank(message = "La cédula es requerida")
    @Column(unique = true, nullable = false)
    private String cedula;

    @NotBlank(message = "El teléfono es requerido")
    private String telefono;

    @NotBlank(message = "El email es requerido")
    @Email(message = "Correo no valido")
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol = Rol.CLIENTE;

    private boolean activo = true;

    public Usuario() {
    }

    public Usuario(String nombre, String cedula, String telefono, String email, String password, Rol rol) {

        this.nombre = nombre;
        this.cedula = cedula;
        this.telefono = telefono;
        this.email = email;
        this.password = password;
        this.rol = rol;
    }

    // getters y setters
    public Long getId() {
    return id;
    }

    public void setId(Long id) {
    this.id = id;
    }

    public String getNombre() {
    return nombre;
    }

    public void setNombre(String nombre) {
    this.nombre = nombre;
    }

    public String getCedula() {
    return cedula;
    }

    public void setCedula(String cedula) {
    this.cedula = cedula;
    }

    public String getTelefono() {
    return telefono;
    }

    public void setTelefono(String telefono) {
    this.telefono = telefono;
    }

    public String getEmail() {
    return email;
    }

    public void setEmail(String email) {
    this.email = email;
    }

    public String getPassword() {
    return password;
    }

    public void setPassword(String password) {
    this.password = password;
    }

    public Rol getRol() {
    return rol;
    }

    public void setRol(Rol rol) {
    this.rol = rol;
    }

    public boolean isActivo() {
    return activo;
    }

    public void setActivo(boolean activo) {
    this.activo = activo;
    }
}
