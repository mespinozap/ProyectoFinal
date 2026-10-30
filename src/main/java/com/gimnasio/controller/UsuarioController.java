package com.gimnasio.controller;

import com.gimnasio.model.Usuario;
import com.gimnasio.service.UsuarioService;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    // LISTAR USUARIOS
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("usuarios", service.listar());
        return "usuarios/lista";
    }

    // NUEVO USUARIO
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("modo", "crear");

        return "usuarios/formulario";
    }

    // GUARDAR USUARIO
    @PostMapping("/guardar")
    public String guardar(
            @Valid @ModelAttribute("usuario") Usuario usuario, BindingResult errores, Model model, RedirectAttributes flash) {

        if (errores.hasErrors()) {
            model.addAttribute("modo", "crear");
            return "usuarios/formulario";
        }

        try {
            service.crear(usuario);
            flash.addFlashAttribute(
                    "mensaje",
                    "Usuario creado correctamente");
            return "redirect:/usuarios";

        } catch (IllegalArgumentException e) {
            model.addAttribute("modo", "crear");
            model.addAttribute("error", e.getMessage());
            return "usuarios/formulario";
        }
    }

    // EDITAR
    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {

        Usuario u = service.buscarPorId(id);

        u.setPassword("");

        model.addAttribute("usuario", u);
        model.addAttribute("modo", "editar");

        return "usuarios/formulario";
    }

    // ACTUALIZAR
    @PostMapping("/{id}/actualizar")
    public String actualizar(
            @PathVariable Long id,
            @Valid @ModelAttribute("usuario") Usuario usuario,
            BindingResult errores,
            Model model,
            RedirectAttributes flash) {

        if (errores.hasErrors()) {
            usuario.setId(id);
            model.addAttribute("modo", "editar");
            return "usuarios/formulario";
        }

        try {
            service.actualizar(id, usuario);
            flash.addFlashAttribute( "mensaje", "Usuario actualizado correctamente");
            return "redirect:/usuarios";

        } catch (IllegalArgumentException e) {
            usuario.setId(id);
            model.addAttribute("modo", "editar");
            model.addAttribute("error", e.getMessage());
            return "usuarios/formulario";
        }
    }

    // DESACTIVAR
    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
        service.desactivar(id);

        flash.addFlashAttribute(
                "mensaje",
                "Usuario desactivado correctamente");

        return "redirect:/usuarios";
    }
}
