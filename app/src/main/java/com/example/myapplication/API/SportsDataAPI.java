package com.example.myapplication.API;

import com.example.myapplication.API.Models.Models_Classificacao.Classificacao;
import com.example.myapplication.API.Models_Equipa.Equipa;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SportsDataAPI {

    String API_KEY = "7604fa60-6178-11eb-94f3-df312b78f61a";
    @GET("standings?apikey=" + API_KEY + "&season_id=496")
    Call<Classificacao> getClassificacao();

    @GET("teams/{team_id}?apikey=" + API_KEY)
    Call<Equipa> getEquipa(@Path(value = "team_id", encoded = true) int teamID);

}
