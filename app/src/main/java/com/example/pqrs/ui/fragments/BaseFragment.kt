package com.example.pqrs.ui.fragments

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pqrs.App
import com.example.pqrs.model.User

open class BaseFragment:Fragment() {

    lateinit var db:SQLiteDatabase

    override fun onAttach(context: Context) {
        super.onAttach(context)
        db=(requireActivity().application as App).db
    }
}

fun BaseFragment.updateUserLogueado(user:User):Long{

    db.execSQL("DELETE FROM Usuario")

    var values=ContentValues()
    values.put("id",1)
    values.put("idRemoto",user.idRemoto)
    values.put("nombre",user.nombre)
    values.put("apellido",user.apellido)
    values.put("username",user.username)
    values.put("contrasena",user.contrasena)
    values.put("token",user.token)
    values.put("rol",user.rol)
    return db.insert("Usuario",null,values)
}


fun BaseFragment.getUserLogueado():User{

    var cursor= db.rawQuery("SELECT * FROM Usuario WHERE id=1",null,null)


    if (cursor.moveToFirst()){
        var id= cursor.getInt(0)
        var idRemoto=cursor.getInt(1)
        var name= cursor.getString(2)
        var apellido= cursor.getString(3)
        var username= cursor.getString(4)
        var contrasena= cursor.getString(5)
        var token= cursor.getString(6)
        var rol= cursor.getInt(7)
        var user=User(id,idRemoto,name,apellido,username,contrasena,token,rol)
        return user
    }else{
        return User(-1)
    }

}

fun BaseFragment.Desloguear(){
    db.execSQL("DELETE FROM Usuario")
    when(this){
        is FragmentVistaGeneral->{
            var accion=FragmentVistaGeneralDirections.actionFragmentVistaGeneralToFragmentLogin()
            findNavController().navigate(accion)
        }
        is FragmentAgregarYEditarPQR->{
            var accion=FragmentAgregarYEditarPQRDirections.actionFragmentAgregarYEditarPQRToFragmentLogin()
            findNavController().navigate(accion)
        }
        is FragmentDetallesPQR->{
            var accion=FragmentDetallesPQRDirections.actionFragmentDetallesPQRToFragmentLogin()
            findNavController().navigate(accion)
        }
        else->{}
    }
}

