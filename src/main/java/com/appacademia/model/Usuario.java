package com.appacademia.model;

import java.time.LocalDate;

public class Usuario {
    private int id;
    private String nome;
    private String cpf;
    private String email;
    private LocalDate dataNascimento;
    private String tipoUsuario;

    public Usuario() {}

    // getters/setters omitted for brevity (same as scaffold)
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id=id;
    }
    public String getNome(){
        return nome;
    }
    public void setNome(String nome){
        this.nome=nome;
    }
    public String getCpf(){
        return cpf;
    }
    public void setCpf(String cpf){
        this.cpf=cpf
        ;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email=email;
    }
    public LocalDate getDataNascimento(){
        return dataNascimento;
    }
    public void setDataNascimento(LocalDate d){
        this.dataNascimento=d;
    }
    public String getTipoUsuario(){
        return tipoUsuario;
    }
    public void setTipoUsuario(String t)
    {this.tipoUsuario=t;
    }
}
