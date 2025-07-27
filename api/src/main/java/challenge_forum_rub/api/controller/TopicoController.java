package challenge_forum_rub.api.controller;

import challenge_forum_rub.api.repository.TopicoRepository;
import challenge_forum_rub.api.topico.DadosAtualizacaoTopico;
import challenge_forum_rub.api.topico.Topico;
import challenge_forum_rub.api.topico.TopicoRequest;
import challenge_forum_rub.api.topico.TopicoResponse;
import challenge_forum_rub.api.usuario.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<TopicoResponse> cadastrar(@RequestBody @Valid TopicoRequest dados, UriComponentsBuilder uriBuilder) {
        Topico topico = new Topico(dados); // construtor recebe todos os campos, inclusive autor

        topicoRepository.save(topico);

        return ResponseEntity
                .created(URI.create("/topicos/" + topico.getId()))
                .body(new TopicoResponse(topico));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        if (!topicoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        topicoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping
    public List<TopicoResponse> listar() {
        return topicoRepository.findAll().stream()
                .map(TopicoResponse::new)
                .toList();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<TopicoResponse> atualizar(@PathVariable Long id, @RequestBody @Valid DadosAtualizacaoTopico dados) {
        Topico topico = topicoRepository.getReferenceById(id);

        // Pega o usuário logado via Spring Security
        Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Verifica se o autor do tópico é o mesmo do usuário logado
        if (!topico.getAutor().equals(usuarioLogado)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        topico.atualizar(dados);
        return ResponseEntity.ok(new TopicoResponse(topico));
    }




}
