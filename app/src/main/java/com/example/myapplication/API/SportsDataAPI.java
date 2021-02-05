package com.example.myapplication.API;

import com.example.myapplication.API.Models.Models_Classificacao.Classificacao;
import com.example.myapplication.API.Models_Equipa.Equipa;
import com.example.myapplication.API.Models_Jogo.Partida;

import java.util.Date;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SportsDataAPI {

    String API_KEY = "08b89ed0-67d3-11eb-b910-0d614bb4fd27";

    @GET("standings?apikey=" + API_KEY + "&season_id=496")
    Call<Classificacao> getClassificacao();

    @GET("teams/{team_id}?apikey=" + API_KEY)
    Call<Equipa> getEquipa(@Path(value = "team_id", encoded = true) int teamID);

    @GET("matches?apikey=" + API_KEY + "&season_id=496")
    Call<Partida> getJogos(@Query("date_from") String data_inicio , @Query("date_to") String data_fim);

    @GET("matches?apikey=" + API_KEY + "&season_id=496" + "&live=true")
    Call<Partida> getJogosLive();
}
