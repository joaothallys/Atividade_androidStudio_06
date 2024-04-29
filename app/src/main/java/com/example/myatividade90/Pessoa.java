package com.example.myatividade90;

public class Pessoa {

    private int cod;
    private String nome;
    private String Telefone;
    private String email;

    public Pessoa() {
    }

    public Pessoa(String nome, String Telefone, String email) {
        this.nome = nome;
        this.Telefone = Telefone;
        this.email = email;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return Telefone;
    }

    public void setTelefone(String tel) {
        this.Telefone = Telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return nome;
    }
}
