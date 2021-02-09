package com.example.myapplication.API;

import com.example.myapplication.API.Models.Models_Classificacao.Classificacao;
import com.example.myapplication.API.Models_Equipa.Equipa;
import com.example.myapplication.API.Models_Jogo.Partida;
import com.example.myapplication.API.Models_Marcadores.Marcador;

import java.util.Date;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SportsDataAPI {

    String API_KEY = "0aafcf80-667c-11eb-be0a-ed3adc0cd6a4";

    @GET("standings?apikey=" + API_KEY + "&season_id=496")
    Call<Classificacao> getClassificacao();

    @GET("teams/{team_id}?apikey=" + API_KEY)
    Call<Equipa> getEquipa(@Path(value = "team_id", encoded = true) int teamID);

    @GET("matches?apikey=" + API_KEY + "&season_id=496")
    Call<Partida> getJogos(@Query("date_from") String data_inicio , @Query("date_to") String data_fim);

    @GET("matches?apikey=" + API_KEY +  "&live=true")
    Call<Partida> getJogosLive();

    @GET("topscorers?apikey="+ API_KEY + "&season_id=496")
    Call<Marcador> getMarcadores();
}
