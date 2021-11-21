package com.example.pqrs.service

import com.example.pqrs.model.recuest.CrearUsuario
import com.example.pqrs.model.response.RespuestaPQRs
import com.example.pqrs.model.response.Usuario
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {


    @POST("user")
    fun login(
        @Query("username") user:String,
        @Query("password")contrasena:String):Call<Usuario>


    @POST("/usuario/createUsuario")
    fun registro(
        @Body usuario: CrearUsuario
    ):Call<Usuario>


    @GET("/pqr/getAll")
    fun obtenerPQRsAdmin(
        @Header("Authorization") token:String):Call<RespuestaPQRs>



    @GET("/pqr/getAllByUser")
    fun obtenerPQRsbyUser(
        @Header("Authorization") token:String):Call<RespuestaPQRs>

}

