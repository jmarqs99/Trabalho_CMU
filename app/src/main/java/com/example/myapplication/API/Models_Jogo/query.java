package com.example.myapplication.API.Models_Jogo;

public class query {

    private String season_id;

    private String dataInicio;

    private String apikey;

    private data data;

    public String getSeason_id() {
        return season_id;
    }

    public void setSeason_id(String season_id) {
        this.season_id = season_id;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public com.example.myapplication.API.Models_Jogo.data getData() {
        return data;
    }

    public void setData(com.example.myapplication.API.Models_Jogo.data data) {
        this.data = data;
    }
}
