package com.example.myapplication.RecyclerView;

public class Jogo_item {

    /**
     * Nome da equipa que joga em casa
     */
    private String equipa_casa;

    /**
     * Nome da equipa que joga fora
     */
    private String equipa_fora;

    /**
     * Logo da equipa que joga em casa
     */
    private String logo_casa;

    /**
     * Logo da equipa que joga fora
     */
    private String logo_fora;

    /**
     * Número de golos marcados pelo equipa que joga em casa
     */
    private int golos_casa;

    /**
     * Número de golos marcados pela equipa que joga fora
     */
    private int golos_fora;

    /**
     * Estado do jogo (Intervalo, terminado, etc...)
     */
    private String estado;

    /**
     * Data de inicio do jogo
     */
    private String data_inicio;

    /**
     * Código de estado do jogo
     */
    private int status_code;

    /**
     * Minuto em que está o jogo
     */
    private String minuto;

    /**
     * Construtor de jogo item
     * @param equipa_casa Nome da equipa que joga em casa
     * @param equipa_fora Nome da equipa que joga em fora
     * @param logo_casa Logo da equipa que joga casa
     * @param logo_fora Logo da equipa que joga fora
     * @param golos_casa Número de golos marcados pela equipa que joga casa
     * @param golos_fora Número de golos marcados pela equipa que joga fora
     * @param status_code Código de estado do jogo
     * @param data_inicio Data de inicio do jogo
     * @param minuto Minuto em que está o jogo
     */
    public Jogo_item(String equipa_casa, String equipa_fora, String logo_casa, String logo_fora, int golos_casa, int golos_fora , int status_code, String data_inicio , String minuto) {
        this.equipa_casa = equipa_casa;
        this.equipa_fora = equipa_fora;
        this.logo_casa = logo_casa;
        this.logo_fora = logo_fora;
        this.golos_casa = golos_casa;
        this.golos_fora = golos_fora;
        this.status_code = status_code;
        this.data_inicio = data_inicio;
        this.minuto = minuto;
    }

    public String getEquipa_casa() {
        return equipa_casa;
    }

    public void setEquipa_casa(String equipa_casa) {
        this.equipa_casa = equipa_casa;
    }

    public String getEquipa_fora() {
        return equipa_fora;
    }

    public void setEquipa_fora(String equipa_fora) {
        this.equipa_fora = equipa_fora;
    }

    public String getLogo_casa() {
        return logo_casa;
    }

    public void setLogo_casa(String logo_casa) {
        this.logo_casa = logo_casa;
    }

    public String getLogo_fora() {
        return logo_fora;
    }

    public void setLogo_fora(String logo_fora) {
        this.logo_fora = logo_fora;
    }

    public int getGolos_casa() {
        return golos_casa;
    }

    public void setGolos_casa(int golos_casa) {
        this.golos_casa = golos_casa;
    }

    public int getGolos_fora() {
        return golos_fora;
    }

    public void setGolos_fora(int golos_fora) {
        this.golos_fora = golos_fora;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getData_inicio() {
        return data_inicio;
    }

    public void setData_inicio(String data_inicio) {
        this.data_inicio = data_inicio;
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public String getMinuto() {
        return minuto;
    }

    public void setMinuto(String minuto) {
        this.minuto = minuto;
    }
}
