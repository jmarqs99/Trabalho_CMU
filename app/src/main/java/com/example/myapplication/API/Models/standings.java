package com.example.myapplication.API.Models;

public class standings {

    private int team_id;
    private int position;
    private int points;
    private String status;
    private String result;
    private overall overall;
    private home home;
    private away away;

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public overall getOverall() {
        return overall;
    }

    public void setOverall(overall overall) {
        this.overall = overall;
    }

    public home getHome() {
        return home;
    }

    public void setHome(home home) {
        this.home = home;
    }

    public away getAway() {
        return away;
    }

    public void setAway(away away) {
        this.away = away;
    }

    @Override
    public String toString() {
        return "standings{" +
                "team_id=" + team_id +
                ", position=" + position +
                ", points=" + points +
                ", status='" + status + '\'' +
                ", result='" + result + '\'' +
                ", overall=" + overall +
                ", home=" + home +
                ", away=" + away +
                '}';
    }

}
