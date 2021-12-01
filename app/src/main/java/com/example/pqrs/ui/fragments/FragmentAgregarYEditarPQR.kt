package com.example.pqrs.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.pqrs.R
import com.example.pqrs.databinding.FragmentAgregarEditarPQRBinding
import com.example.pqrs.model.recuest.CrearPQR
import com.example.pqrs.model.recuest.Usuario
import com.example.pqrs.model.response.RespuestaPQRs
import com.example.pqrs.service.ApiRest
import com.example.pqrs.utils.getFormattedDateShort
import com.example.pqrs.utils.log
import com.example.pqrs.utils.toast
import com.wdullaer.materialdatetimepicker.Utils
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class FragmentAgregarYEditarPQR : BaseFragment() {

    lateinit var b:FragmentAgregarEditarPQRBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b= FragmentAgregarEditarPQRBinding.bind(inflater.inflate(R.layout.fragment_agregar_editar_p_q_r, container, false))
       return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        b.fragmento=this
        var user=getUserLogueado()

        if (user.rol==2){

            b.edtusuariopqr.setText(user.username)
        }


        var asuntopqr=b.asuntopqr.text.toString()
        var tipoPqr=b.spinner.selectedItem.toString()
        var fechaPqr=b.btExpDate.text.toString()
        var autorPqr=Usuario(user.username)
        var estado= String()

        if (user.rol==2){
            var pqr=CrearPQR(asuntopqr,tipoPqr,estado,autorPqr,autorPqr,fechaPqr)

            var cal= ApiRest.createPQR(pqr,user.token)
            cal.enqueue(object: Callback<RespuestaPQRs> {
                override fun onResponse(call: Call<RespuestaPQRs>, response: Response<RespuestaPQRs>) {

                   if (response.isSuccessful){
                       var pqrCreada=response.body()

                       pqrCreada?.let {
                           if (it.status.code==3){toast(it.content.error)
                           }else{

                               toast("PQR creada con exito")
                           }
                       }
                   }
                }

                override fun onFailure(call: Call<RespuestaPQRs>, t: Throwable) {
                    log(t.message) }
            })

        } else{

            var usuarioCreadorPqr=b.edtusuariopqr.toString()

            var resulta= db.rawQuery("SELECT username FROM UsuarioRemotos WHERE username='$usuarioCreadorPqr'",null)

           if (resulta.moveToFirst()){
               var usuario=Usuario(resulta.getString(0))
               var pqr=CrearPQR(asuntopqr,tipoPqr,estado,usuario,autorPqr,fechaPqr)


               var cal= ApiRest.createPQR(pqr,user.token)
               cal.enqueue(object: Callback<RespuestaPQRs> {
                   override fun onResponse(call: Call<RespuestaPQRs>, response: Response<RespuestaPQRs>) {

                       if (response.isSuccessful){
                           var pqrCreada=response.body()

                           pqrCreada?.let {
                               if (it.status.code==3){toast(it.content.error)
                               }else{

                                   toast("PQR creada con exito")
                               }
                           }
                       }
                   }

                   override fun onFailure(call: Call<RespuestaPQRs>, t: Throwable) {
                       log(t.message) }
               })

           } else{toast("no llego nada")}
        }
    }

        fun dialogDatePickerLight(v:View){
            var calendar= Calendar.getInstance()
            var datePickerDialog= DatePickerDialog.newInstance(
                { view,year,monthOfYear,dayOfMonth->
                    val calendar = Calendar.getInstance()
                    calendar[Calendar.YEAR] = year
                    calendar[Calendar.MONTH] = monthOfYear
                    calendar[Calendar.DAY_OF_MONTH] = dayOfMonth
                    val date = calendar.timeInMillis
                    (v as EditText).setText(getFormattedDateShort(date))
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.setThemeDark(false)
            datePickerDialog.setAccentColor(resources.getColor(R.color.purple_200))
            datePickerDialog.setMinDate(calendar)
            datePickerDialog.show(requireActivity().supportFragmentManager,"TimePiker")
            //.show(requireActivity().supportFragmentManager, "Expiration Date")
        }

}