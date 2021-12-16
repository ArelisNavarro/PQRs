package com.example.pqrs.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.pqrs.R
import com.example.pqrs.databinding.FragmentDetallePQRBinding
import com.example.pqrs.model.response.RespuestaPQRs
import com.example.pqrs.service.ApiRest
import com.example.pqrs.utils.getHtml
import com.example.pqrs.utils.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FragmentDetallesPQR : BaseFragment() {

    lateinit var b:FragmentDetallePQRBinding

    val argument:FragmentDetallesPQRArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        b= FragmentDetallePQRBinding.bind(inflater.inflate(R.layout.fragment_detalle_p_q_r, container, false))
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        var idpqr = argument.idPQR
        b.fragment=this

        b.id=idpqr

     if ( argument.idPQR==-1){ volver()
     }else{
         peticion()}
    }

    private fun peticion() {
        var user = getUserLogueado()
        var call: Call<RespuestaPQRs> = ApiRest.obtenerPQRById(argument.idPQR, user.token)

        call.enqueue(object : Callback<RespuestaPQRs> {
            override fun onResponse(call: Call<RespuestaPQRs>, response: Response<RespuestaPQRs>) {

                if (response.isSuccessful) {
                    var datosR: RespuestaPQRs? = response.body()

                    datosR?.let {

                        if (datosR.status.code==3){
                            toast(datosR.content.error)
                            return
                        }
                         else {

                            var pqr0 = datosR.content.pqrs[0]

                            b.tipo.text = getHtml(R.string.Tipo_pqr, pqr0.tipo)
                            b.tvAsuntoPqr.text = getHtml(R.string.asunto, pqr0.asunto)
                            b.tvFechaCreacion.text =
                                getHtml(R.string.fecha_creacion, pqr0.fechaCreacion)
                            b.tvUsuarioPQr.text = getHtml(R.string.usuario_pqr, pqr0.usuario.nombre)
                            b.tvEstadoPqr.text = getHtml(R.string.estado, pqr0.estado)
                            b.tvFechaLimitePqr.text =
                                getHtml(R.string.fecha_limite, pqr0.fechaLimite)
                            b.tvVigenciaPqr.text = getHtml(R.string.vigencia, pqr0.vigencia)
                        }
                    }
                }
            }
            override fun onFailure(call: Call<RespuestaPQRs>, t: Throwable) {
                if (t.message!!.contains("Too many")) {
                    reloguearse(this@FragmentDetallesPQR::peticion)

                }
            }
        })
    }

    fun irAEditar(id:Int){
        findNavController().navigate(FragmentDetallesPQRDirections.actionFragmentDetallesPQRToFragmentEditar(id))
    }
}


