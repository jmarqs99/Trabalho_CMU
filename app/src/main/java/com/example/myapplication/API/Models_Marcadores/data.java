package com.example.myapplication.API.Models_Marcadores;

public class data {

    private int pos;

    private player player;

    private team team;

    private int league_id;

    private int season_id;

    private int matches_played;

    private int minutes_played;

    private int substituted_in;

    private goals goals;

    private int penalties;

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public com.example.myapplication.API.Models_Marcadores.player getPlayer() {
        return player;
    }

    public void setPlayer(com.example.myapplication.API.Models_Marcadores.player player) {
        this.player = player;
    }

    public com.example.myapplication.API.Models_Marcadores.team getTeam() {
        return team;
    }

    public void setTeam(com.example.myapplication.API.Models_Marcadores.team team) {
        this.team = team;
    }

    public int getLeague_id() {
        return league_id;
    }

    public void setLeague_id(int league_id) {
        this.league_id = league_id;
    }

    public int getSeason_id() {
        return season_id;
    }

    public void setSeason_id(int season_id) {
        this.season_id = season_id;
    }

    public int getMatches_played() {
        return matches_played;
    }

    public void setMatches_played(int matches_played) {
        this.matches_played = matches_played;
    }

    public int getMinutes_played() {
        return minutes_played;
    }

    public void setMinutes_played(int minutes_played) {
        this.minutes_played = minutes_played;
    }

    public int getSubstituted_in() {
        return substituted_in;
    }

    public void setSubstituted_in(int substituted_in) {
        this.substituted_in = substituted_in;
    }

    public com.example.myapplication.API.Models_Marcadores.goals getGoals() {
        return goals;
    }

    public void setGoals(com.example.myapplication.API.Models_Marcadores.goals goals) {
        this.goals = goals;
    }

    public int getPenalties() {
        return penalties;
    }

    public void setPenalties(int penalties) {
        this.penalties = penalties;
    }
}
