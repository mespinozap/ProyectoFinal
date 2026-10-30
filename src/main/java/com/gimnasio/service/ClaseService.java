package com.gimnasio.service;

import com.gimnasio.model.Clase;
import com.gimnasio.repository.ClaseRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;

@Service
public class ClaseService {

    private final ClaseRepository repo;

    public ClaseService(ClaseRepository repo) {
        this.repo = repo;
    }

    // LISTAR CLASES
    public List<Clase> listar() {
        return repo.findAll();
    }

    // BUSCAR UNA CLASE
    public Clase buscarPorId(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Clase no encontrada: " + id));
    }

    // CREAR CLASE
    @Transactional
    public Clase crear(Clase clase) {

        validarTraslape(clase);

        clase.setHoraFin(clase.getHoraInicio().plusHours(1));

        return repo.save(clase);
    }

    // ACTUALIZAR CLASE
    @Transactional
    public Clase actualizar(Long id, Clase datos) {

        Clase existente = buscarPorId(id);

        existente.setNombre(datos.getNombre());
        existente.setFecha(datos.getFecha());

        existente.setHoraInicio(datos.getHoraInicio());

        existente.setHoraFin(datos.getHoraInicio().plusHours(1));

        existente.setCupoMaximo(datos.getCupoMaximo());
        existente.setEntrenador(datos.getEntrenador());

        validarTraslape(existente);

        return repo.save(existente);
    }

    // ELIMINAR CLASE
    @Transactional
    public void eliminar(Long id) {
        repo.deleteById(id);
    }

    // VALIDAR TRASLAPE DE HORARIOS
    private void validarTraslape(Clase clase) {

        List<Clase> clasesEntrenador = repo.findByEntrenador(clase.getEntrenador());

        for (Clase c : clasesEntrenador) {

            if (clase.getId() != null &&
                clase.getId().equals(c.getId())) {
                continue;
            }

            if (clase.getFecha().equals(c.getFecha())) {

                LocalTime inicioNueva = clase.getHoraInicio();

                LocalTime finNueva = inicioNueva.plusHours(1);

                LocalTime inicioExistente = c.getHoraInicio();

                LocalTime finExistente = c.getHoraFin();

                boolean traslape = inicioNueva.isBefore(finExistente) && finNueva.isAfter(inicioExistente);

                if (traslape) {
                    throw new IllegalArgumentException(
                            "El entrenador ya tiene una clase en ese horario");
                }
            }
        }
    }
}
