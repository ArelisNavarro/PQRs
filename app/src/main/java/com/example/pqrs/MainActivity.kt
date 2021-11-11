package com.example.pqrs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.BufferedInputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var call =service.listaPeliculas()

        var listenerRed=object :Callback<Repuesta>{

            override fun onResponse(call: Call<Repuesta>, response: Response<Repuesta>) {

                if (response.isSuccessful){
                    var body:Repuesta= response.body() as Repuesta
                    body.toString()
                }
            }
            override fun onFailure(call: Call<Repuesta>, t: Throwable) {

                var error= t.message
                t.printStackTrace()
            }
        }
        call.enqueue(listenerRed)





        var called = servis.teams()

        var escuchadorcall=object :Callback<RespuestaTeams>{

            override fun onResponse(call: Call<RespuestaTeams>,respuesta: Response<RespuestaTeams>
            ) {
                if (respuesta.isSuccessful){

                    var cuerpo:RespuestaTeams= respuesta.body() as RespuestaTeams

                    cuerpo.toString()
                }
            }

            override fun onFailure(call: Call<RespuestaTeams>, t: Throwable) {
                var error= t.message
                t.printStackTrace()
            }
        }
        called.enqueue(escuchadorcall)
    }
}