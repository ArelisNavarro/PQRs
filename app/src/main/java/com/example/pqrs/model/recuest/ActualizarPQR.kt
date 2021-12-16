package com.example.pqrs.model.recuest

data class ActualizarPQR(
    var id:Int,
    var asunto:String,
    var tipo:String,
    var estado:String,
    var usuario:Usuario,
    var autor:Usuario,
    var strFechaCreacion:String)
