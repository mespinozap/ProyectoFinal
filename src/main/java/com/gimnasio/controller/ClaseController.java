package com.gimnasio.controller;

import com.gimnasio.model.Clase;
import com.gimnasio.service.ClaseService;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/clases")
public class ClaseController {

    private final ClaseService service;

    public ClaseController(ClaseService service) {
        this.service = service;
    }

    // LISTAR
    @GetMapping
    public String listar(Model model) {

        model.addAttribute("clases", service.listar());

        return "clases/lista";
    }

    // NUEVA CLASE
    @GetMapping("/nuevo")
    public String nuevo(Model model) {

        model.addAttribute("clase", new Clase());
        model.addAttribute("modo", "crear");

        return "clases/formulario";
    }

    // GUARDAR
    @PostMapping("/guardar")
    public String guardar(
            @Valid @ModelAttribute("clase") Clase clase,
            BindingResult errores,
            Model model,
            RedirectAttributes flash) {

        if (errores.hasErrors()) {

            model.addAttribute("modo", "crear");

            return "clases/formulario";
        }

        try {

            service.crear(clase);

            flash.addFlashAttribute(
                    "mensaje",
                    "Clase creada correctamente");

            return "redirect:/clases";

        } catch (IllegalArgumentException e) {

            model.addAttribute("modo", "crear");
            model.addAttribute("error", e.getMessage());

            return "clases/formulario";
        }
    }

    // EDITAR
    @GetMapping("/{id}/editar")
    public String editar(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "clase",
                service.buscarPorId(id));

        model.addAttribute("modo", "editar");

        return "clases/formulario";
    }

    // ACTUALIZAR
    @PostMapping("/{id}/actualizar")
    public String actualizar(
            @PathVariable Long id,
            @Valid @ModelAttribute("clase") Clase clase,
            BindingResult errores,
            Model model,
            RedirectAttributes flash) {

        if (errores.hasErrors()) {

            model.addAttribute("modo", "editar");

            return "clases/formulario";
        }

        try {

            service.actualizar(id, clase);

            flash.addFlashAttribute(
                    "mensaje",
                    "Clase actualizada correctamente");

            return "redirect:/clases";

        } catch (IllegalArgumentException e) {

            model.addAttribute("modo", "editar");
            model.addAttribute("error", e.getMessage());

            return "clases/formulario";
        }
    }

    // ELIMINAR
    @PostMapping("/{id}/eliminar")
    public String eliminar(
            @PathVariable Long id,
            RedirectAttributes flash) {

        service.eliminar(id);

        flash.addFlashAttribute(
                "mensaje",
                "Clase eliminada correctamente");

        return "redirect:/clases";
    }
}
