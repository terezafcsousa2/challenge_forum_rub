package challenge_forum_rub.api.topico;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "topicos")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String mensagem;
    private LocalDate dataCriacao = LocalDate.now();

    @Enumerated(EnumType.STRING)
    private Estado estado = Estado.NAO_RESOLVIDO;

    private String autor;
    private String curso;
    @Column(nullable = false)
    private boolean ativo = true;


    public Topico(TopicoRequest dados) {
        this.titulo = dados.getTitulo();
        this.mensagem = dados.getMensagem();
        this.dataCriacao= LocalDate.now();
        this.autor = dados.getAutor();
        this.curso = dados.getCurso();
    }

    public Topico() {

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getCurso() {
        return curso;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }
    public void atualizar(DadosAtualizacaoTopico dados) {
        this.titulo = dados.titulo();
        this.mensagem = dados.mensagem();
    }
}
