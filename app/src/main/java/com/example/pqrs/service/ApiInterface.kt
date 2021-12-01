package com.example.pqrs.service

import com.example.pqrs.model.recuest.CrearPQR
import com.example.pqrs.model.recuest.CrearUsuario
import com.example.pqrs.model.response.*
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


    @GET("/usuario/getAllUsersNoAdmin")
    fun obtenerUserByUsername(
        @Header("Authorization") token:String): Call<RespuestaUsers>


    @GET("/pqr/getAll")
    fun obtenerPQRsAdmin(
        @Header("Authorization") token:String):Call<RespuestaPQRs>



    @GET("/pqr/getAllByUser")
    fun obtenerPQRsbyUser(
        @Query("id")id: Int,
        @Header("Authorization") token:String
    ):Call<RespuestaPQRs>



    @GET("/pqr/getById")
    fun obtenerPQRsbyId(
        @Header("Authorization") token:String,
        @Query ("id") id:Int):Call<RespuestaPQRs>


    @POST("/pqr/createPqr")
    fun crearPQR(
        @Header("Authorization") token:String,
        @Body  pqr:CrearPQR
    ):Call<RespuestaPQRs>

}



