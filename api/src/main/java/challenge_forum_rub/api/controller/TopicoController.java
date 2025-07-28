package challenge_forum_rub.api.controller;

import challenge_forum_rub.api.repository.TopicoRepository;
import challenge_forum_rub.api.topico.DadosAtualizacaoTopico;
import challenge_forum_rub.api.topico.Topico;
import challenge_forum_rub.api.topico.TopicoRequest;
import challenge_forum_rub.api.topico.TopicoResponse;
import challenge_forum_rub.api.usuario.Usuario;
import challenge_forum_rub.api.usuario.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicoController {


    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

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
        Optional<Topico> optionalTopico = topicoRepository.findById(id);
        if (optionalTopico.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Topico topico = optionalTopico.get();

        String usuarioLogado = SecurityContextHolder.getContext().getAuthentication().getName();

        Usuario usuario = usuarioRepository.findByLogin(usuarioLogado);
        if (!topico.getAutor().equals(usuario.getNome())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Você só pode deletar tópicos que você criou.");
        }

        topico.setAtivo(false); // Soft delete
        topicoRepository.save(topico);

        return ResponseEntity.ok("Tópico inativado com sucesso.");
    }


    @GetMapping
    public List<TopicoResponse> listar() {
        return topicoRepository.findByAtivoTrue().stream()
                .map(TopicoResponse::new)
                .toList();
    }




    @PutMapping("/{id}")
    public ResponseEntity<Topico> atualizar(@PathVariable Long id,
                                            @RequestBody DadosAtualizacaoTopico dados,
                                            @AuthenticationPrincipal Usuario usuarioLogado) {

        Optional<Topico> topicoOptional = topicoRepository.findById(id);

        if (topicoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Topico topico = topicoOptional.get();

        // Verifica se o usuário logado é o autor
        if (!topico.getAutor().equalsIgnoreCase(usuarioLogado.getNome())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        // Atualiza os campos
        topico.setTitulo(dados.titulo());
        topico.setMensagem(dados.mensagem());

        topicoRepository.save(topico);

        return ResponseEntity.ok(topico);
    }




}
