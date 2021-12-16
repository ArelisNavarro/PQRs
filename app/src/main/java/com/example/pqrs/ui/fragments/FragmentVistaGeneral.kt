package com.example.pqrs.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pqrs.R
import com.example.pqrs.databinding.FragmentVistaGeneralBinding
import com.example.pqrs.model.User
import com.example.pqrs.model.response.RespuestaPQRs
import com.example.pqrs.model.response.RespuestaUsers
import com.example.pqrs.service.ApiRest
import com.example.pqrs.ui.adapters.AdapterPQR
import com.example.pqrs.utils.log
import com.example.pqrs.utils.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentVistaGeneral : BaseFragment() {

    lateinit var b:FragmentVistaGeneralBinding
    lateinit var adapterPQR: AdapterPQR

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var user=getUserLogueado()

        if (user.rol==1){
            guardarUserEndbLocal(user)
        }

    }

    private fun guardarUserEndbLocal(user: User) {
        var call: Call<RespuestaUsers> = ApiRest.getAllUsersNoAdmin(user.token)
        call.enqueue(object : Callback<RespuestaUsers> {
            override fun onResponse(
                call: Call<RespuestaUsers>,
                response: Response<RespuestaUsers>
            ) {

                if (response.isSuccessful) {

                    var usersObtenidos: RespuestaUsers? = response.body()

                    usersObtenidos?.let {

                        if (it.status.code == 3) {
                            toast(usersObtenidos.content.error)
                            return
                        } else {
                            db.execSQL("DELETE FROM UsuarioRemotos")
                            putUsers(it.content.lista)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<RespuestaUsers>, t: Throwable) {
                log(t.message)
            }

        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b= FragmentVistaGeneralBinding.bind(inflater.inflate(R.layout.fragment_vista_general, container, false))
        return b.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        b.fragmento=this

        setHasOptionsMenu(true)
        b.reciclerpqr.layoutManager=LinearLayoutManager(requireContext())
        b.reciclerpqr.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        adapterPQR=AdapterPQR()
        adapterPQR.clik={ id: Int -> findNavController().navigate(FragmentVistaGeneralDirections.actionFragmentVistaGeneralToFragmentDetallesPQR(id)) }
        b.reciclerpqr.adapter=adapterPQR

        peticion()
    }

    private fun peticion() {
        var user = getUserLogueado()
        val call: Call<RespuestaPQRs> = ApiRest.obtenerPQRs(user)
        call.enqueue(object : Callback<RespuestaPQRs> {
            override fun onResponse(call: Call<RespuestaPQRs>, response: Response<RespuestaPQRs>) {

                if (response.isSuccessful) {
                    var data: RespuestaPQRs? = response.body()

                    data?.let {

                        if (it.status.code == 3) {

                            toast(it.content.error)
                        } else {
                            adapterPQR.setListPqrs(it.content.pqrs)
                        }
                    }

                }
            }

            override fun onFailure(call: Call<RespuestaPQRs>, t: Throwable) {
                log(t.message)

                if (t.message!!.contains("Too many")) {
                    reloguearse(this@FragmentVistaGeneral::peticion)
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_principal, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){

            R.id.menuCerrarSesion ->{Desloguear()}
            R.id.menuInformation ->{toast("Esta es la informacion de la app.")}
        }
        return super.onOptionsItemSelected(item)
    }


    fun iraAgregar(v:View){
        var accion= FragmentVistaGeneralDirections.actionFragmentVistaGeneralToFragmentAgregarYEditarPQR()
        findNavController().navigate(accion)
    }



}