package com.appacademia.model;

public class Academia {
    private int id;
    private String nome;
    private String cnpj;
    private String localizacao;
    private String telefone;

    // ✅ Construtor vazio (obrigatório para o JavaFX e frameworks)
    public Academia() {}

    // ✅ Construtor completo (necessário para o DAO)
    public Academia(int id, String nome, String cnpj, String localizacao, String telefone) {
        this.id = id;
        this.nome = nome;
        this.cnpj = cnpj;
        this.localizacao = localizacao;
        this.telefone = telefone;
    }

    // ✅ Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    public String getLocalizacao() { return localizacao; }
    public void setLocalizacao(String localizacao) { this.localizacao = localizacao; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
}
