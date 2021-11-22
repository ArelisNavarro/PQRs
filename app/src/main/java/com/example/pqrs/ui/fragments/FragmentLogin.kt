package com.example.pqrs.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.pqrs.R
import com.example.pqrs.databinding.FragmentLoginBinding
import com.example.pqrs.model.User
import com.example.pqrs.model.response.Usuario
import com.example.pqrs.service.ApiRest
import com.example.pqrs.utils.log
import com.example.pqrs.utils.notifyErrorWithAction
import com.example.pqrs.utils.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FragmentLogin : BaseFragment() {

    lateinit var b:FragmentLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var user=getUserLogueado()
        if (user.id == -1){}else{
            irAGeneral()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b= FragmentLoginBinding.bind(inflater.inflate(R.layout.fragment_login, container, false))
        return b.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        b.fragment=this

    }

    fun irARegistro(v:View){
       var accion= FragmentLoginDirections.actionFragmentLoginToFragmentRegistro()
            findNavController().navigate(accion)
    }


    fun loguear(v:View){

        v.visibility=View.GONE

        var usuarioText= b.edtUsuario.text.toString()
        var contrasenaText=b.edtContraseA.text.toString()

        if (usuarioText.isNullOrBlank() || contrasenaText.isNullOrBlank()) {
            b.edtUsuario.error="llene los campos"
            b.edtContraseA.error="llene los campos"
            v.visibility=View.VISIBLE

        }else{

            var call:Call<Usuario> = ApiRest.loguearse(usuarioText,contrasenaText)

            call.enqueue(object :Callback<Usuario>{
                override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {

                    v.visibility=View.VISIBLE

                    if (response.isSuccessful){
                        var usuario: Usuario? =response.body()

                        usuario?.let {

                            if (usuario.status.code==3){
                                toast(usuario.content.error)
                                return
                            }

                            if (it.content==null){
                                notifyErrorWithAction("Registrese","ir a",{irARegistro(v)})
                            }else{

                                var user=User(
                                    0,
                                    it.content.usuarioDTO.id,
                                    it.content.usuarioDTO.nombre,
                                    it.content.usuarioDTO.apellido,
                                    it.content.username,
                                    it.content.password,
                                    it.content.token,
                                    it.content.usuarioDTO.rol.id

                                )
                                log("Se ha logueado exitoxamente ${usuario.content.usuarioDTO.nombre}")
                                updateUserLogueado(user)

                                irAGeneral()

                            }
                        }

                    }
                }

                override fun onFailure(call: Call<Usuario>, t: Throwable) {
                    v.visibility=View.VISIBLE
                    notifyErrorWithAction("ERROR: ${t.message}","REINTENTAR",{})
                    log(t.message)
                }

            })

        }

    }

    private fun irAGeneral() {
        var accion = FragmentLoginDirections.actionFragmentLoginToFragmentVistaGeneral()
        findNavController().navigate(accion)
    }


}