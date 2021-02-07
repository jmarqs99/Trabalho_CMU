package com.example.myapplication.RecyclerView;

public class Jogadores_item {

    private String posicao;

    private String email;

    private String pontos;

    public Jogadores_item(String posicao, String email, String pontos) {
        this.posicao = posicao;
        this.email = email;
        this.pontos = pontos;
    }

    public String getPosicao() {
        return posicao;
    }

    public void setPosicao(String posicao) {
        this.posicao = posicao;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPontos() {
        return pontos;
    }

    public void setPontos(String pontos) {
        this.pontos = pontos;
    }
}
