package com.example.myapplication.API.Models;

public class query {

    private String apikey;
    private String season_id;

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public String getSeason_id() {
        return season_id;
    }

    public void setSeason_id(String season_id) {
        this.season_id = season_id;
    }

    @Override
    public String toString() {
        return "query{" +
                "apikey='" + apikey + '\'' +
                ", season_id='" + season_id + '\'' +
                '}';
    }

}
