package com.example.pqrs.utils

import android.content.Context
import android.text.Html
import android.text.Spannable
import android.text.Spanned
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import com.example.pqrs.R
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

fun String.toHtmlText() : Spanned {
    return HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_LEGACY)
}

fun Fragment.notifyErrorWithAction(
    message: String,
    actionText: String,
    action: () -> Any
){
    val snackBar = Snackbar.make(this.requireView(), message, Snackbar.LENGTH_INDEFINITE)
    snackBar.setAction(actionText) { _ -> action.invoke() }
    snackBar.duration=Snackbar.LENGTH_LONG
    snackBar.setActionTextColor(ContextCompat.getColor(this.requireContext(), R.color.design_default_color_on_primary))
    snackBar.show()

}

fun Fragment.log(mensage:String?){

    mensage?.let {
        Log.e("MIS_LOGS",it)
    }

}

fun Fragment.toast(mensage: String){

    android.widget.Toast.makeText(requireContext(),mensage, android.widget.Toast.LENGTH_SHORT).show()
}


fun View.v(v:Boolean){

    visibility= if (v)View.VISIBLE else View.GONE
}

fun Fragment.getHtml(recurso:Int,texto:String):Spanned{

    return Html.fromHtml(requireContext().resources.getString(recurso,texto))
}

fun getFormattedDateShort(fecha:Long):String{
    val newFormat = SimpleDateFormat("MMM dd, yyyy")
    return newFormat.format(Date(fecha))
}


