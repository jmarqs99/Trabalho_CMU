package com.example.myapplication.API.Models.Models_Classificacao;

public class data {

    private int season_id;
    private int league_id;
    private int has_groups;
    private standings[] standings;

    public int getSeason_id() {
        return season_id;
    }

    public void setSeason_id(int season_id) {
        this.season_id = season_id;
    }

    public int getLeague_id() {
        return league_id;
    }

    public void setLeague_id(int league_id) {
        this.league_id = league_id;
    }

    public int getHas_groups() {
        return has_groups;
    }

    public void setHas_groups(int has_groups) {
        this.has_groups = has_groups;
    }

    public standings[] getStandings() {
        return standings;
    }

    public void setStandings(standings[] standings) {
        this.standings = standings;
    }

    @Override
    public String toString() {
        return "data{" +
                "season_id=" + season_id +
                ", league_id=" + league_id +
                ", has_groups=" + has_groups +
                ", standings=" + standings[0].toString() +
                '}';
    }

}
