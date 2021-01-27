package com.example.myapplication.API.Models_Equipa;

public class data {

    private int team_id;
    private String name;
    private String short_code;
    private String logo;
    private country country;

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShort_code() {
        return short_code;
    }

    public void setShort_code(String short_code) {
        this.short_code = short_code;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public com.example.myapplication.API.Models_Equipa.country getCountry() {
        return country;
    }

    public void setCountry(com.example.myapplication.API.Models_Equipa.country country) {
        this.country = country;
    }
}
