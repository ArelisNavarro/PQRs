package com.example.pqrs.service

import android.media.session.MediaSession
import com.example.pqrs.model.User
import com.example.pqrs.model.recuest.CrearUsuario
import com.example.pqrs.model.response.RespuestaPQRs
import com.example.pqrs.model.response.Usuario
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiRest {


    private val retrofit:Retrofit=Retrofit.Builder()
        .baseUrl("http://192.168.200.16:8090/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private var apiInterface:ApiInterface = retrofit.create(ApiInterface::class.java)


    fun loguearse(username:String,password:String):Call<Usuario>{

        var call:Call<Usuario> =apiInterface.login(username ,password)
        return call
    }

    fun registrarse(usuario: CrearUsuario):Call<Usuario>{

        return apiInterface.registro(usuario)
    }


    fun obtenerPQRs(user:User):Call<RespuestaPQRs>{

        if (user.rol==1){
            return apiInterface.obtenerPQRsAdmin(user.token)
        }else{
            return apiInterface.obtenerPQRsbyUser(user.idRemoto.toString())
        }
    }

}