package com.example.pqrs.model.general

import com.example.pqrs.model.response.Rol

data class UsuarioDTO(
    val id:Int,
    val nombre:String,
    val apellido:String,
    val username:String,
    val password: String,
    val rol: Rol
)
