package com.example.myapplication.API;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SportsDataAPI {

    String API_KEY = "a1f35c60-5728-11eb-8b88-cf8b61066a50";

    @GET("standings?apikey=" + API_KEY + "&season_id=496")
    Call<Classificacao> getClassificacao();

}
