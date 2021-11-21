package com.example.pqrs.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.example.pqrs.R
import com.example.pqrs.databinding.FragmentRegistroBinding
import com.example.pqrs.model.recuest.CrearUsuario
import com.example.pqrs.model.User
import com.example.pqrs.model.response.Usuario
import com.example.pqrs.service.ApiRest
import com.example.pqrs.utils.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FragmentRegistro : BaseFragment() {

    lateinit var b:FragmentRegistroBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b =FragmentRegistroBinding.bind(inflater.inflate(R.layout.fragment_registro, container, false))
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        b.fragment=this
        b.adapterSpiner= ArrayAdapter(requireContext(),android.R.layout.simple_list_item_1, listOf("Seleccione Rol",Constantes.ROL_USUARIO,Constantes.ROL_ADMIN))

    }

    fun registrar(v:View){

        v.v(false)

        var nombre =b.edtRegistroNombre.text.toString()
        var apellido =b.edtRegistroApellido.text.toString()
        var username =b.edtRegistroUsuario.text.toString()
        var contrasena =b.edtRegistroContraseA.text.toString()
        var rol=b.listaRol.selectedItem.toString()

        if (nombre.isNullOrBlank()) {b.edtRegistroNombre.error="Introduzca el nombre" ;v.v(true) ; return}
        if (apellido.isNullOrBlank()) {b.edtRegistroApellido.error="Introduzca el apellido" ;v.v(true) ; return}
        if (username.isNullOrBlank()) {b.edtRegistroUsuario.error="Introduzca el username" ;v.v(true) ; return}
        if (contrasena.isNullOrBlank()) {b.edtRegistroContraseA.error="Introduzca el contrasena" ;v.v(true) ; return}
        if (rol.equals("Seleccione Rol")){toast("Seleccione el rol");v.v(true) ; return}



        var usuario= CrearUsuario(
            nombre,
            apellido,
            username,
            contrasena,
            if (rol.equals(Constantes.ROL_ADMIN))  rol_admin else rol_usuario
        )
     var call: Call<Usuario> = ApiRest.registrarse(usuario)

        call.enqueue(object :Callback<Usuario>{
            override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {

                if ( response.isSuccessful){
                    var usuario: Usuario? =response.body()

                    usuario?.let {
                        if (it.content==null){
                            notifyErrorWithAction("Ya esta registrado","Iniciar Sesion",{volver()})
                        }else{

                            var user= User(
                                1,
                                it.content.usuarioDTO.id,
                                it.content.usuarioDTO.nombre,
                                it.content.usuarioDTO.apellido,
                                it.content.username,
                                it.content.password,
                                it.content.token,
                                it.content.usuarioDTO.rol.id
                            )
                           var logueado= updateUserLogueado(user)
                            v.visibility=View.VISIBLE

                            if (logueado>0){

                                var accion= FragmentRegistroDirections.actionFragmentRegistroToFragmentVistaGeneral()
                                findNavController().navigate(accion)

                            }else{ notifyErrorWithAction("Registro Exitoso","Iniciar Sesion",{volver()}) }
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

    private fun volver() {
        requireActivity().onBackPressed()
    }



}