package com.example.pqrs.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.pqrs.R
import com.example.pqrs.databinding.FragmentAgregarEditarPQRBinding
import com.example.pqrs.model.User
import com.example.pqrs.model.recuest.ActualizarPQR
import com.example.pqrs.model.recuest.Usuario
import com.example.pqrs.model.response.RespuestaPQRs
import com.example.pqrs.service.ApiRest
import com.example.pqrs.utils.log
import com.example.pqrs.utils.toast
import com.example.pqrs.utils.validateFieldsToCreatePqr
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class FragmentEditar : BaseFragment() {

    lateinit var b:FragmentAgregarEditarPQRBinding
    val argument:FragmentEditarArgs by navArgs()
    var idPQR:Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view=inflater.inflate(R.layout.fragment_agregar_editar_p_q_r, container, false)
        b= FragmentAgregarEditarPQRBinding.bind(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var user=getUserLogueado()
        b.btGuardarPqr.visibility=View.GONE
        b.butActualizar.visibility=View.VISIBLE
        b.spEstado.visibility=View.VISIBLE
        b.textView3.visibility=View.VISIBLE

        if (user.rol==2){
            b.tvEstado.visibility=View.VISIBLE
            b.spEstado.visibility=View.GONE
        }

        if ( argument.id==-1){ volver()
        }else{
           peticionGetPqr()
        }
        b.butActualizar.setOnClickListener{

            actualizarPqr()
        }

    }

    private fun actualizarPqr() {
        var user=getUserLogueado()
        var asuntopqr = b.asuntopqr.text.toString()
        var tipoPqr = b.spTipoPqr.selectedItem as String
        var fechaPqr = b.btExpDate.text.toString().replace("/", "").trim()
        var autorPqr = Usuario(user.username)

        var textos = arrayListOf<String>(asuntopqr, tipoPqr, fechaPqr, autorPqr.username,)
        validateFieldsToCreatePqr(textos, requireContext()) {

            if (user.rol == 2) {
                actualizarPqrByUser(idPQR,asuntopqr, tipoPqr, autorPqr, fechaPqr, user)

            } else {
                actualizarPqrByAdmin(idPQR,asuntopqr, tipoPqr,autorPqr, fechaPqr, user)
            }
        }
    }

    private fun actualizarPqrByAdmin(id: Int,asuntopqr: String, tipoPqr: String, autorPqr: Usuario, fechaPqr: String, user: User) {

        var estadopqr=b.spEstado.selectedItem as String
        var usuarioCreadorPqr = b.edtusuariopqr.text.toString()
        var usuario = Usuario(usuarioCreadorPqr)
        var actualizacionPqr= ActualizarPQR(id,asuntopqr,tipoPqr.uppercase(),estadopqr,usuario,autorPqr,fechaPqr)

        var call =ApiRest.updatePQR(actualizacionPqr,user.token)
        call.enqueue(object : Callback<RespuestaPQRs>{
            override fun onResponse(call: Call<RespuestaPQRs>, response: Response<RespuestaPQRs>) {

                if (response.isSuccessful) {
                    var pqrActualizada = response.body()

                    pqrActualizada?.let {
                        if (it.status.code == 3) {
                            toast(it.content.error)
                        } else {

                            toast("PQR Actualizada con exito")

                            requireActivity().onBackPressed()
                        }
                    }
                } else {
                    toast("error")
                }
            }

            override fun onFailure(call: Call<RespuestaPQRs>, t: Throwable) {
                log(t.message)
                if (t.message!!.contains("Too many")) {
                    reloguearse(this@FragmentEditar::actualizarPqr)
                }
            }
        })
    }

    private fun actualizarPqrByUser(id: Int,asuntopqr:String ,tipoPqr: String, autorPqr: Usuario, fechaPqr: String, user: User) {

        var stadopqr=b.tvEstado.text.toString()
        var actualizacionPqr=ActualizarPQR(id,asuntopqr,tipoPqr.uppercase(),stadopqr,autorPqr,autorPqr,fechaPqr)

        var call =ApiRest.updatePQR(actualizacionPqr,user.token)
        call.enqueue(object : Callback<RespuestaPQRs>{
            override fun onResponse(call: Call<RespuestaPQRs>, response: Response<RespuestaPQRs>) {

                if (response.isSuccessful) {
                    var pqrActualizada = response.body()

                    pqrActualizada?.let {
                        if (it.status.code == 3) {
                            toast(it.content.error)
                        } else {

                            toast("PQR Actualizada con exito")

                            requireActivity().onBackPressed()
                        }
                    }
                } else {
                    toast("error")
                }
            }

            override fun onFailure(call: Call<RespuestaPQRs>, t: Throwable) {
                log(t.message)
                if (t.message!!.contains("Too many")) {
                    reloguearse(this@FragmentEditar::actualizarPqr)
                }
            }
        })
    }

    fun peticionGetPqr(){
        var user=getUserLogueado()
        var call: Call<RespuestaPQRs> = ApiRest.obtenerPQRById(argument.id, user.token)

        call.enqueue(object : Callback<RespuestaPQRs> {
            override fun onResponse(call: Call<RespuestaPQRs>, response: Response<RespuestaPQRs>) {

                if (response.isSuccessful) {
                    var datosR: RespuestaPQRs? = response.body()

                    datosR?.let {

                        if (datosR.status.code == 3) {
                            toast(datosR.content.error)
                            return
                        } else {

                            var pqr = datosR.content.pqrs[0]
                            b.asuntopqr.setText(pqr.asunto)
                            b.edtusuariopqr.setText(pqr.usuario.nombre)
                            b.btExpDate.setText(pqr.fechaCreacion)
                            b.tvEstado.setText(pqr.estado)

                          var position=  when(pqr.tipo){
                                "PETICION"->0
                                "QUEJA"->1
                                "RECLAMO"->2
                              else -> 0
                          }
                            b.spTipoPqr.setSelection(position)


                            var estado=  when(pqr.estado){
                                "NUEVO"->0
                                "EN_EJECUCION"->1
                                "CERRADO"->2
                                else -> 0
                            }
                            b.spEstado.setSelection(estado)

                            idPQR=pqr.id
                        }
                    }
                }
            }

            override fun onFailure(call: Call<RespuestaPQRs>, t: Throwable) {

                log(t.message)
                if (t.message!!.contains("Too many")) {
                    reloguearse(this@FragmentEditar::peticionGetPqr)
                }
            }
        })
    }



}