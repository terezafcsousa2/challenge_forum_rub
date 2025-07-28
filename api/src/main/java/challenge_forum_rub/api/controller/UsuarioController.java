package challenge_forum_rub.api.controller;


import challenge_forum_rub.api.usuario.DadosCadastroUsuario;
import challenge_forum_rub.api.usuario.Usuario;
import challenge_forum_rub.api.usuario.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {


    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<Void> cadastrar(@RequestBody @Valid DadosCadastroUsuario dados) {
        var usuario = new Usuario(dados, passwordEncoder);
        usuarioRepository.save(usuario);

        var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(usuario.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }




    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        var usuarios = usuarioRepository.findAll();
        return ResponseEntity.ok(usuarios);
    }
}
