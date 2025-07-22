package model;

import java.time.LocalDateTime;

/**
 * Representa um ticket aberto quando a resposta do chatbot não é satisfatória.
 * <p>
 * Os tickets contêm informações sobre a pergunta original, data de criação,
 * status atual e o ID relacionado à base de conhecimento.
 * </p>
 */

public class Ticket {
    private int id;
    private String pergunta;
    private LocalDateTime dataCriacao;
    private int pergunta_id;
    private int status;

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPergunta() {
        return pergunta;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPergunta_id() {
        return pergunta_id;
    }

    public void setPergunta_id(int pergunta_id) {
        this.pergunta_id = pergunta_id;
    }
}


