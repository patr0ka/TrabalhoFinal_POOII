package com.appacademia.model;

import java.time.LocalDateTime;

public class Treino {
    private int id;
    private int idUsuario;
    private String tipoTreino;
    private byte[] pdfTreino;
    private LocalDateTime dataCriacao;
    private String observacoes;

    // ✅ Construtor vazio (usado pelo JavaFX e frameworks)
    public Treino() {}

    // ✅ Construtor completo (usado pelo TreinoDAO)
    public Treino(int id, int idUsuario, String tipoTreino, byte[] pdfTreino, LocalDateTime dataCriacao, String observacoes) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.tipoTreino = tipoTreino;
        this.pdfTreino = pdfTreino;
        this.dataCriacao = dataCriacao;
        this.observacoes = observacoes;
    }

    // ✅ Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getTipoTreino() {
        return tipoTreino;
    }

    public void setTipoTreino(String tipoTreino) {
        this.tipoTreino = tipoTreino;
    }

    public byte[] getPdfTreino() {
        return pdfTreino;
    }

    public void setPdfTreino(byte[] pdfTreino) {
        this.pdfTreino = pdfTreino;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}
