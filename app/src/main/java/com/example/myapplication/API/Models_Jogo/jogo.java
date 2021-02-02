package com.example.myapplication.API.Models_Jogo;

public class jogo {

    private int match_id;

    private int status_code;

    private String status;

    private String match_start;

    private String match_start_iso;

    private int minute;

    private stage stage;

    private group group;

    private round round;

    private int referee_id;

    private int league_id;

    private int season_id;

    private home_team home_team;

    private away_team away_team;

    private stats stats;

    private venue venue;

    public int getMatch_id() {
        return match_id;
    }

    public void setMatch_id(int match_id) {
        this.match_id = match_id;
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMatch_start() {
        return match_start;
    }

    public void setMatch_start(String match_start) {
        this.match_start = match_start;
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

    public home_team getHome_team() {
        return home_team;
    }

    public void setHome_team(home_team home_team) {
        this.home_team = home_team;
    }

    public away_team getAway_team() {
        return away_team;
    }

    public void setAway_team(away_team away_team) {
        this.away_team = away_team;
    }

    public stats getStats() {
        return stats;
    }

    public void setStats(stats stats) {
        this.stats = stats;
    }

    public venue getVenue() {
        return venue;
    }

    public void setVenue(venue venue) {
        this.venue = venue;
    }

    public String getMatch_start_iso() {
        return match_start_iso;
    }

    public void setMatch_start_iso(String match_start_iso) {
        this.match_start_iso = match_start_iso;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public stage getStage() {
        return stage;
    }

    public void setStage(stage stage) {
        this.stage = stage;
    }

    public group getGroup() {
        return group;
    }

    public void setGroup(group group) {
        this.group = group;
    }

    public round getRound() {
        return round;
    }

    public void setRound(round round) {
        this.round = round;
    }

    public int getReferee_id() {
        return referee_id;
    }

    public void setReferee_id(int referee_id) {
        this.referee_id = referee_id;
    }
}
