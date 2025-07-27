package challenge_forum_rub.api.topico;

import java.time.LocalDate;

public record DadosDetalhamentoTopico(long id, String titulo,String autor, String mensagem, LocalDate dataCriacao,Estado estado,  String curso) {
    public DadosDetalhamentoTopico(Topico topico){
        this(topico.getId(), topico.getTitulo(), topico.getAutor(), topico.getMensagem() ,topico.getDataCriacao(), topico.getEstado(), topico.getCurso());

    }
}
