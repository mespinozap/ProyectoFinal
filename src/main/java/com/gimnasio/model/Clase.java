package com.gimnasio.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "clases")
public class Clase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotNull(message = "La fecha es obligatoria")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fecha;

    @NotNull(message = "La hora de inicio es obligatoria")
    private LocalTime horaInicio;

    private LocalTime horaFin;

    private Integer cupoMaximo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "entrenador_id", nullable = false)
    private Usuario entrenador;

    public Clase() {
    }

    public Clase(String nombre, LocalDate fecha, LocalTime horaInicio, Integer cupoMaximo) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaInicio.plusHours(1);
        this.cupoMaximo = cupoMaximo;
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

    public LocalDate getFecha() {
    return fecha;
    }

    public void setFecha(LocalDate fecha) {
    this.fecha = fecha;
    }

    public LocalTime getHoraInicio() {
    return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
    this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
    return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
    this.horaFin = horaFin;
    }

    public Integer getCupoMaximo() {
    return cupoMaximo;
    }

    public void setCupoMaximo(Integer cupoMaximo) {
    this.cupoMaximo = cupoMaximo;
    }

    public Usuario getEntrenador() {
    return entrenador;
    }

    public void setEntrenador(Usuario entrenador) {
    this.entrenador = entrenador;
    }
}
