package com.example.myapplication.API.Models.Models_Classificacao;

public class overall {

    private int games_played;
    private int won;
    private int draw;
    private int lost;
    private int goal_diff;
    private int goals_scored;
    private int goals_against;

    public int getGames_played() {
        return games_played;
    }

    public void setGames_played(int games_played) {
        this.games_played = games_played;
    }

    public int getWon() {
        return won;
    }

    public void setWon(int won) {
        this.won = won;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getLost() {
        return lost;
    }

    public void setLost(int lost) {
        this.lost = lost;
    }

    public int getGoal_diff() {
        return goal_diff;
    }

    public void setGoal_diff(int goal_diff) {
        this.goal_diff = goal_diff;
    }

    public int getGoals_scored() {
        return goals_scored;
    }

    public void setGoals_scored(int goals_scored) {
        this.goals_scored = goals_scored;
    }

    public int getGoals_against() {
        return goals_against;
    }

    public void setGoals_against(int goals_against) {
        this.goals_against = goals_against;
    }

    @Override
    public String toString() {
        return "overall{" +
                "games_played=" + games_played +
                ", won=" + won +
                ", draw=" + draw +
                ", lost=" + lost +
                ", goal_diff=" + goal_diff +
                ", goals_scored=" + goals_scored +
                ", goals_against=" + goals_against +
                '}';
    }

}
