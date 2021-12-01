package com.example.pqrs.model.response

data class RespuestaUsers(
    var status:Status,
    var content:C
)


data class C(
    var lista:ArrayList<ContentUser>,
    var error:String
)

data class ContentUser(
    var id:Int,
    var nombre:String,
    var apellido:String,
    var username:String,
    var password:String,
    var rol:Rol,
)
