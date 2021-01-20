package com.example.myapplication.API;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class Classificacao {

    private query query;
    private data data;
    private standings standings;

    public Classificacao.query getQuery() {
        return query;
    }

    public void setQuery(Classificacao.query query) {
        this.query = query;
    }

    public Classificacao.data getData() {
        return data;
    }

    public void setData(Classificacao.data data) {
        this.data = data;
    }



    private class query {
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

    private class data {
        private int season_id;
        private int league_id;
        private int has_groups;
        private standings standings;

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

        @Override
        public String toString() {
            return "data{" +
                    "season_id=" + season_id +
                    ", league_id=" + league_id +
                    ", has_groups=" + has_groups +
                    ", standings=" + standings +
                    '}';
        }
    }

    private class standings {
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

        public Classificacao.overall getOverall() {
            return overall;
        }

        public void setOverall(Classificacao.overall overall) {
            this.overall = overall;
        }

        public Classificacao.home getHome() {
            return home;
        }

        public void setHome(Classificacao.home home) {
            this.home = home;
        }

        public Classificacao.away getAway() {
            return away;
        }

        public void setAway(Classificacao.away away) {
            this.away = away;
        }

        @Override
        public String toString() {
            return "standings{" +
                    "team_id=" + team_id +
                    ", points=" + points +
                    ", status='" + status + '\'' +
                    ", result='" + result + '\'' +
                    ", overall=" + overall +
                    ", home=" + home +
                    ", away=" + away +
                    '}';
        }
    }

    private class overall {
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
    }

    private class home {
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
    }

    private class away {
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
    }

    @Override
    public String toString() {
        return "Classificacao{" +
                "query=" + query +
                ", data=" + data +
                ", standings=" + standings +
                '}';
    }
}
