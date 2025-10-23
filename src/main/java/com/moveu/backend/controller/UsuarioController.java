package com.moveu.backend.controller;

import com.moveu.backend.entity.Usuario;
import com.moveu.backend.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*") // Permite llamadas desde Android, frontend web, etc.
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // Obtener todos los usuarios
    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    // Obtener usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuario(@PathVariable Long id) {
        return usuarioRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear nuevo usuario
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Map<String, Object>> crearUsuario(@RequestBody Usuario usuario) {
        Map<String, Object> respuesta = new HashMap<>();

        // Validar email único
        Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());
        if (usuarioExistente.isPresent()) {
            respuesta.put("mensaje", "El correo ya está registrado.");
            return ResponseEntity.status(409).body(respuesta);
        }

        // Guardar usuario
        Usuario nuevoUsuario = usuarioRepository.save(usuario);
        respuesta.put("mensaje", "Usuario registrado exitosamente.");
        respuesta.put("usuario", nuevoUsuario);

        return ResponseEntity.ok(respuesta);
    }

    // ✅ Actualizar usuario
    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Map<String, Object>> actualizarUsuario(
            @PathVariable Long id,
            @RequestBody Usuario body
    ) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setNombre(body.getNombre());
            usuario.setEmail(body.getEmail());

            Usuario actualizado = usuarioRepository.save(usuario);

            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("mensaje", "Usuario actualizado correctamente.");
            respuesta.put("usuario", actualizado);

            return ResponseEntity.ok(respuesta);
        }).orElseGet(() -> {
            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("mensaje", "Usuario no encontrado.");
            return ResponseEntity.status(404).body(respuesta);
        });
    }

    // ✅ Eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminarUsuario(@PathVariable Long id) {
        Map<String, String> respuesta = new HashMap<>();

        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            respuesta.put("mensaje", "Usuario eliminado correctamente.");
            return ResponseEntity.ok(respuesta);
        } else {
            respuesta.put("mensaje", "Usuario no encontrado.");
            return ResponseEntity.status(404).body(respuesta);
        }
    }

    // ✅ Login (autenticación simple)
    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> datos) {
        String email = datos.get("email");
        String password = datos.get("password");

        Map<String, Object> respuesta = new HashMap<>();

        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);

        if (usuarioOpt.isEmpty()) {
            respuesta.put("mensaje", "Correo no registrado.");
            return ResponseEntity.status(404).body(respuesta);
        }

        Usuario usuario = usuarioOpt.get();

        if (!usuario.getPassword().equals(password)) {
            respuesta.put("mensaje", "Contraseña incorrecta.");
            return ResponseEntity.status(401).body(respuesta);
        }

        respuesta.put("mensaje", "Inicio de sesión exitoso.");
        respuesta.put("usuario", usuario);
        return ResponseEntity.ok(respuesta);
    }
}
