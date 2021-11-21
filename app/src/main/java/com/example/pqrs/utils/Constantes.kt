package com.example.pqrs.utils

import com.example.pqrs.model.response.Rol

class Constantes{
    companion object{
        val ROL_ADMIN ="administrador"
        val ROL_USUARIO="usuario"
    }
}

var rol_admin= Rol(1, Constantes.ROL_ADMIN)

var rol_usuario=Rol(2,Constantes.ROL_USUARIO)