package com.example.ProyectoIntegradorClinica.controller.restcontroller.imp.auth;

import com.example.ProyectoIntegradorClinica.persistence.entities.auth.Usuario;
import com.example.ProyectoIntegradorClinica.service.imp.auth.UsuarioService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    UsuarioService us;

    private final Logger logger = Logger.getLogger(UsuarioController.class);

    @PostMapping("/nuevo")
    public ResponseEntity<Usuario> crearNuevo(@RequestBody Usuario usuario){
        logger.debug("Iniciando el método 'crearNuevo' para usuarios ");

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        String encodePass = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(encodePass);

        return ResponseEntity.ok(us.crear(usuario));
    }

    @GetMapping("/todos")
    public ResponseEntity<Set<Usuario>> consultarTodos() {
        logger.debug("Iniciando el método 'consultarTodos' para usuarios ");

        return ResponseEntity.ok(us.consultarTodos());
    }
}
