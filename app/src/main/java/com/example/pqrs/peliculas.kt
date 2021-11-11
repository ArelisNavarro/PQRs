package com.example.pqrs

import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface PeliculasInterface {

    @GET("3/movie/popular")
    fun listaPeliculas(
        @Query("api_key") api_key: String = "09963e300150f9831c46a1828a82a984",
        @Query("language") idioma: String = "en-US"
    ): Call<Repuesta>
}


interface TeamsOfLeagueInterface {

    @GET("api/v1/json/1/search_all_teams.php")
    fun teams(
        @Query("s") s: String = "Soccer",
        @Query("c") c: String = "Spain"
    ): Call<RespuestaTeams>
}