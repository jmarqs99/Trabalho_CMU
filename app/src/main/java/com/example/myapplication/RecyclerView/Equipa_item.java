package com.example.myapplication.RecyclerView;

public class Equipa_item {

    /**
     * Nome da equipa
     */
    private String nome;

    /**
     * Pontos da equipa
     */
    private int pontos;

    /**
     * Posição na classificação da equipa
     */
    private int posicao;

    /**
     * Logo da equipa
     */
    private String logo;

    /**
     * Número de jogos disputados de equipa
     */
    private int num_jogos;

    /**
     * Número de golos marcados da equipa
     */
    private int golos_marcados;

    /**
     * Número de golos sofridos da equipa
     */
    private int golos_sofridos;

    /**
     * Construtor de Equipa item
     * @param nome nome da equipa
     * @param pontos pontos da equipa
     * @param posicao posição na classificação da equipa
     * @param logo logotipo da equipa
     * @param num_jogos Número de jogos da equipa
     * @param golos_marcados Número de golos marcados da equipa
     * @param golos_sofridos Número de golos sofridos da equipa
     */
    public Equipa_item(String nome, int pontos, int posicao, String logo, int num_jogos, int golos_marcados, int golos_sofridos) {
        this.nome = nome;
        this.pontos = pontos;
        this.posicao = posicao;
        this.logo = logo;
        this.num_jogos = num_jogos;
        this.golos_marcados = golos_marcados;
        this.golos_sofridos = golos_sofridos;
    }

    /**
     * Metodo para retornar posição da equipa
     * @return posição da equipa
     */
    public int getPosicao() {
        return posicao;
    }

    /**
     * Método para definir uma posição
     * @param posicao posição a ser definida
     */
    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }

    /**
     * Método para retornar o logotipo da equipa
     * @return
     */
    public String getLogo() {
        return logo;
    }

    /**
     * Método para definir logotipo da equipa
     * @param logo
     */
    public void setLogo(String logo) {
        this.logo = logo;
    }

    /**
     * Método para retornar número de jogos da equipa
     * @return número de jogos da equipa
     */
    public int getNum_jogos() {
        return num_jogos;
    }

    /**
     * Método para definir número de jogos da equipa
     * @param num_jogos número de jogos a definir
     */
    public void setNum_jogos(int num_jogos) {
        this.num_jogos = num_jogos;
    }

    /**
     * Método para retornar número de golos marcados da equipa
     * @return número de golos marcados
     */
    public int getGolos_marcados() {
        return golos_marcados;
    }

    /**
     * Define o número de golos marcados da equipa
     * @param golos_marcados número de golos marcados a definir
     */
    public void setGolos_marcados(int golos_marcados) {
        this.golos_marcados = golos_marcados;
    }

    /**
     * Método para retornar número de golos sofridos
     * @return número de golos sofridos da equipa
     */
    public int getGolos_sofridos() {
        return golos_sofridos;
    }

    /**
     * Método para definir o número de golos da equipa
     * @param golos_sofridos número de golos sofridos pela equipa
     */
    public void setGolos_sofridos(int golos_sofridos) {
        this.golos_sofridos = golos_sofridos;
    }

    /**
     * Metodo para retornar nome da equipa
     * @return nome da equipa
     */
    public String getNome() {
        return nome;
    }

    /**
     * Metodo para definir nome da equipa
     * @param nome nome da equipa
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Metodo para retornar os pontos da equipa
     * @return número de pontos da equipa
     */
    public int getPontos() {
        return pontos;
    }

    /**
     * Metodo para definir número de pontos da equipa
     * @param pontos pontos da equipa
     */
    public void setPontos(int pontos) {
        this.pontos = pontos;
    }
}
