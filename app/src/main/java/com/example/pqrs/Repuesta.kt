package com.example.pqrs

import com.google.gson.annotations.SerializedName

data class Repuesta (
    var page: Int,
    var results:List<Peliculas>,
    var total_pages:Int,
    var total_results:Int

    )


data class Peliculas(
    var id:Int,
    @SerializedName("original_title")
    var nombre:String,
    @SerializedName("overview")
    var resumen:String
)



data class RespuestaTeams(
var teams:ArrayList<Teams>
)

data class Teams(
    var idTeam:Int,
    var strTeam:String,
    @SerializedName("intFormedYear")
    var formedYear:Int,
)