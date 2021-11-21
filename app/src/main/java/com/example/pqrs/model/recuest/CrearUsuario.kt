package com.example.pqrs.model.recuest

import com.example.pqrs.model.response.Rol

data class CrearUsuario(

    var nombre:String,
    var apellido:String,
    var username:String,
    var password:String,
    var rol: Rol
)