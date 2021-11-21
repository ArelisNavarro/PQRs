package com.example.pqrs.model.response

import com.example.pqrs.model.general.UsuarioDTO

data class Usuario(

    var status:Status,
    var content: Content
)

data class Content(
    val username:String,
    val token:String,
    val password:String,
    val usuarioDTO: UsuarioDTO,
    val error:String

)

data class Rol(
    val id: Int,
    val nombre: String
)