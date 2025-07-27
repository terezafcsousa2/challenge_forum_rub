package challenge_forum_rub.api.topico;


import jakarta.validation.constraints.NotBlank;

public record DadosAtualizacaoTopico(
        @NotBlank String titulo,
        @NotBlank String mensagem
) {}

