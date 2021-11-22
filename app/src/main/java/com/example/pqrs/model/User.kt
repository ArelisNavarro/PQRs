package com.example.pqrs.model

data class User(
    val id:Int,
    val idRemoto:Int,
    val nombre:String,
    val apellido:String,
    val username:String,
    val contrasena:String,
    val token:String,
    val rol:Int
){
    constructor(id:Int) : this(id,-1,"","","","","",-1)
}