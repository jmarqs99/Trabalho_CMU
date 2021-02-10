package com.example.myapplication.RecyclerView;

import java.util.Collections;

public class Jogadores_item implements Comparable< Jogadores_item > {

    /**
     * Posição na Classificação do Jogador
     */
    private String posicao;

    /**
     * Email do jogador
     */
    private String email;

    /**
     * Número de pontos do jogador
     */
    private Long pontos;

    /**
     * Construtor de Jogadores_item
     * @param posicao posição do jogador na classificação
     * @param email email do jogador
     * @param pontos pontos do jogador
     */
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


    @Override
    public int compareTo(Jogadores_item jogadores_item) {
        return this.getPontos().compareTo(jogadores_item.getPontos());
    }
}
