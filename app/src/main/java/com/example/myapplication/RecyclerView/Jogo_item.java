package com.example.myapplication.RecyclerView;

public class Jogo_item {

    public Jogo_item(String equipa_casa, String equipa_fora, String logo_casa, String logo_fora, int golos_casa, int golos_fora , int status_code, String data_inicio) {
        this.equipa_casa = equipa_casa;
        this.equipa_fora = equipa_fora;
        this.logo_casa = logo_casa;
        this.logo_fora = logo_fora;
        this.golos_casa = golos_casa;
        this.golos_fora = golos_fora;
        this.status_code = status_code;
        this.data_inicio = data_inicio;
    }

    private String equipa_casa;

    private String equipa_fora;

    private String logo_casa;

    private String logo_fora;

    private int golos_casa;

    private int golos_fora;

    private String estado;

    private String data_inicio;

    private int status_code;

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
}
