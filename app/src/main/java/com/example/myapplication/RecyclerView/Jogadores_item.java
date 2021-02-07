package com.example.myapplication.RecyclerView;

public class Jogadores_item {

    private String posicao;

    private String email;

    private Long pontos;

    public Jogadores_item(String posicao, String email, Long pontos) {
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

    public Long getPontos() {
        return pontos;
    }

    public void setPontos(Long pontos) {
        this.pontos = pontos;
    }
}
