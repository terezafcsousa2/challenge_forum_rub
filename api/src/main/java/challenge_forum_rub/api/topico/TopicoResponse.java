package challenge_forum_rub.api.topico;

public class TopicoResponse {
    private Long id;
    private String titulo;
    private String mensagem;
    private String autor;
    private String curso;


    public TopicoResponse(Topico topico) {
        this.id = topico.getId();
        this.titulo = topico.getTitulo();
        this.mensagem = topico.getMensagem();
        this.autor = topico.getAutor();
        this.curso = topico.getCurso();
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public String getAutor() {
        return autor;
    }

    public String getCurso() {
        return curso;
    }
}

