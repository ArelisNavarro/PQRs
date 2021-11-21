package com.example.pqrs.utils

import android.text.Spanned
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import com.example.pqrs.R
import com.google.android.material.snackbar.Snackbar

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