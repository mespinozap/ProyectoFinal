package com.gimnasio.repository;

import com.gimnasio.model.Clase;
import com.gimnasio.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClaseRepository extends JpaRepository<Clase, Long> {

    List<Clase> findByEntrenador(Usuario entrenador);
}

