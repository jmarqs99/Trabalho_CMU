package com.example.myapplication.RecyclerView;

public class Equipa_item {

    private String nome;

    private int pontos;

    private int posicao;

    private String logo;

    private int num_jogos;

    private int golos_marcados;

    private int golos_sofridos;

    public Equipa_item(String nome, int pontos, int posicao, String logo, int num_jogos, int golos_marcados, int golos_sofridos) {
        this.nome = nome;
        this.pontos = pontos;
        this.posicao = posicao;
        this.logo = logo;
        this.num_jogos = num_jogos;
        this.golos_marcados = golos_marcados;
        this.golos_sofridos = golos_sofridos;
    }

    public int getPosicao() {
        return posicao;
    }

    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public int getNum_jogos() {
        return num_jogos;
    }

    public void setNum_jogos(int num_jogos) {
        this.num_jogos = num_jogos;
    }

    public int getGolos_marcados() {
        return golos_marcados;
    }

    public void setGolos_marcados(int golos_marcados) {
        this.golos_marcados = golos_marcados;
    }

    public int getGolos_sofridos() {
        return golos_sofridos;
    }

    public void setGolos_sofridos(int golos_sofridos) {
        this.golos_sofridos = golos_sofridos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }
}
