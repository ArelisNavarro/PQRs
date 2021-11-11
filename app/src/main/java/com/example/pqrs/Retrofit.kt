package com.example.pqrs

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


var retrofit:Retrofit = Retrofit.Builder()
    .baseUrl("https://api.themoviedb.org/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

var service: PeliculasInterface = retrofit.create(PeliculasInterface::class.java)




var retrofit_: Retrofit = Retrofit.Builder()

    .baseUrl("https://www.thesportsdb.com/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

var servis: TeamsOfLeagueInterface = retrofit_.create(TeamsOfLeagueInterface::class.java)