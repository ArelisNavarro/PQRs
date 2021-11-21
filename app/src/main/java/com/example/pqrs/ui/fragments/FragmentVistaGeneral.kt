package com.example.pqrs.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pqrs.R
import com.example.pqrs.databinding.FragmentVistaGeneralBinding
import com.example.pqrs.model.response.PQRs
import com.example.pqrs.model.response.RespuestaPQRs
import com.example.pqrs.service.ApiRest
import com.example.pqrs.ui.adapters.AdapterPQR
import com.example.pqrs.utils.log
import com.example.pqrs.utils.notifyErrorWithAction
import com.example.pqrs.utils.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentVistaGeneral : BaseFragment() {

    lateinit var b:FragmentVistaGeneralBinding
    lateinit var adapterPQR: AdapterPQR


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b= FragmentVistaGeneralBinding.bind(inflater.inflate(R.layout.fragment_vista_general, container, false))
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        b.reciclerpqr.layoutManager=LinearLayoutManager(requireContext())
        b.reciclerpqr.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        adapterPQR=AdapterPQR()
        b.reciclerpqr.adapter=adapterPQR


        var user=getUserLogueado()

        val call:Call<RespuestaPQRs> =ApiRest.obtenerPQRs(user)

        call.enqueue(object:Callback<RespuestaPQRs>{
            override fun onResponse(call: Call<RespuestaPQRs>, response: Response<RespuestaPQRs>) {

                if (response.isSuccessful){
                   var data:RespuestaPQRs?=response.body()

                    data?.let {

                        if (it.status.code==3){

                            toast(it.content.error)
                        }else{
                            adapterPQR.setListPqrs(it.content.pqrs)
                        }
                    }

                }
            }

            override fun onFailure(call: Call<RespuestaPQRs>, t: Throwable) {
                log(t.message)

                notifyErrorWithAction("ERROR: ${t.message}","REINTENTAR",{})
                log(t.message)
            }


        })

    }
}