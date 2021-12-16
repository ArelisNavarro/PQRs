package com.example.pqrs.ui.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pqrs.R
import com.example.pqrs.databinding.FragmentAgregarEditarPQRBinding
import com.example.pqrs.model.User
import com.example.pqrs.model.recuest.CrearPQR
import com.example.pqrs.model.recuest.Usuario
import com.example.pqrs.model.response.RespuestaPQRs
import com.example.pqrs.service.ApiRest
import com.example.pqrs.ui.adapters.AdapterUsuarios
import com.example.pqrs.ui.adapters.Onclick
import com.example.pqrs.utils.getFormattedDateShort
import com.example.pqrs.utils.log
import com.example.pqrs.utils.toast
import com.example.pqrs.utils.validateFieldsToCreatePqr
import com.wdullaer.materialdatetimepicker.Utils
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class FragmentAgregarYEditarPQR : BaseFragment() {

    lateinit var b: FragmentAgregarEditarPQRBinding
    lateinit var adapterPQR: AdapterUsuarios


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b = FragmentAgregarEditarPQRBinding.bind(
            inflater.inflate(
                R.layout.fragment_agregar_editar_p_q_r,
                container,
                false
            )
        )
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        b.fragmento = this

        var user = getUserLogueado()
        if (user.rol == 2) {

            b.edtusuariopqr.setText(user.username)
        } else{
            b.edtusuariopqr.setOnClickListener() {
                showCustomDialog()
            }
        }

        b.btGuardarPqr.setOnClickListener {
            peticionAgregarpqr()
        }
    }

    private fun peticionAgregarpqr() {
        var user = getUserLogueado()
        var asuntopqr = b.asuntopqr.text.toString()
        var tipoPqr = b.spTipoPqr.selectedItem as String
        var fechaPqr = b.btExpDate.text.toString().replace("/", "").trim()
        var autorPqr = Usuario(user.username)
        var estado = "NUEVO"

        var textos = arrayListOf<String>(asuntopqr, tipoPqr, fechaPqr, autorPqr.username, estado)
        validateFieldsToCreatePqr(textos, requireContext()) {

            if (user.rol == 2) {
                createPqrByUser(asuntopqr, tipoPqr, estado, autorPqr, fechaPqr, user)

            } else {
                createPqrByAdmin(asuntopqr, tipoPqr, estado,autorPqr, fechaPqr, user)
            }
        }
    }

    private fun createPqrByAdmin(
        asuntopqr: String,
        tipoPqr: String,
        estado: String,
        autorPqr: Usuario,
        fechaPqr: String,
        user: User
    ) {
        var usuarioCreadorPqr = b.edtusuariopqr.text.toString()

            var usuario = Usuario(usuarioCreadorPqr)
            var pqr = CrearPQR(asuntopqr, tipoPqr.uppercase(), estado, usuario, autorPqr, fechaPqr)


            var cal = ApiRest.createPQR(pqr, user.token)
            cal.enqueue(object : Callback<RespuestaPQRs> {
                override fun onResponse(
                    call: Call<RespuestaPQRs>,
                    response: Response<RespuestaPQRs>
                ) {

                    if (response.isSuccessful) {
                        var pqrCreada = response.body()

                        pqrCreada?.let {
                            if (it.status.code == 3) {
                                toast(it.content.error)
                            } else {

                                toast("PQR creada con exito")
                                requireActivity().onBackPressed()
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<RespuestaPQRs>, t: Throwable) {
                    log(t.message)
                    if (t.message!!.contains("Too many")) {
                        reloguearse(this@FragmentAgregarYEditarPQR::peticionAgregarpqr)
                    }
                }
            })

    }

    private fun createPqrByUser(
        asuntopqr: String,
        tipoPqr: String,
        estado: String,
        autorPqr: Usuario,
        fechaPqr: String,
        user: User
    ) {
        var pqr = CrearPQR(asuntopqr, tipoPqr.uppercase(), estado, autorPqr, autorPqr, fechaPqr)

        var cal = ApiRest.createPQR(pqr, user.token)
        cal.enqueue(object : Callback<RespuestaPQRs> {
            override fun onResponse(call: Call<RespuestaPQRs>, response: Response<RespuestaPQRs>
            ) {

                if (response.isSuccessful) {
                    var pqrCreada = response.body()

                    pqrCreada?.let {
                        if (it.status.code == 3) {
                            toast(it.content.error)
                        } else {

                            toast("PQR creada con exito")

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
                    reloguearse(this@FragmentAgregarYEditarPQR::peticionAgregarpqr)
                }
            }
        })
    }


    fun dialogDatePickerLight(v: View) {
        var calendar = Calendar.getInstance()
        var datePickerDialog = DatePickerDialog.newInstance(
            { view, year, monthOfYear, dayOfMonth ->
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
        datePickerDialog.show(requireActivity().supportFragmentManager, "TimePiker")
        //.show(requireActivity().supportFragmentManager, "Expiration Date")
    }


    fun showCustomDialog() {

        val dialogo = Dialog(requireContext())
        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogo.setContentView(R.layout.filtro_users)
        dialogo.setCancelable(true)


        var cursor = db.rawQuery(
            "SELECT * FROM UsuarioRemotos", null
        )

        var lista = arrayListOf<String>()

        if (cursor.moveToFirst()) {

            lista.add(cursor.getString(1))

            var hfgh = lista.toString()

            while (cursor.moveToNext()) {
                lista.add(cursor.getString(1))

                var hfoh = lista.toString()
            }

        } else {
            toast("no se pudo obtener la lista")
        }

        var buscador = dialogo.findViewById<EditText>(R.id.buscador)

        var reciclerUsers = dialogo.findViewById<RecyclerView>(R.id.reciclerForSelectUser)

        reciclerUsers.layoutManager = LinearLayoutManager(requireContext())
        reciclerUsers.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
        var adapterusers = AdapterUsuarios(lista = lista)
        reciclerUsers.adapter = adapterusers


        var listener = object : Onclick {

            override fun click(item: String) {
                b.edtusuariopqr.text = item

                dialogo.dismiss()
            }
        }

        adapterusers.addOnClickListener(listener)

        dialogo.show()
        dialogo.window!!.attributes = WindowManager.LayoutParams().apply {
            copyFrom(dialogo.window!!.attributes)
            width = WindowManager.LayoutParams.WRAP_CONTENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
        }
    }
}