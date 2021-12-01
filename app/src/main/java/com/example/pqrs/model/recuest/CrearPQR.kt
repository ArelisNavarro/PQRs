package com.example.pqrs.model.recuest

data class CrearPQR(
    var asunto:String,
    var tipo:String,
    var estado:String,
    var usuario:Usuario,
    var autor:Usuario,
    var strFechaCreacion:String

)


data class Usuario(
    var username:String
)