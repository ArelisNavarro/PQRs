package com.example.pqrs.model.response

import com.example.pqrs.model.general.UsuarioDTO

data class RespuestaPQRs(

    var status:Status,
    var content:ContentPQR

)


data class ContentPQR(
    var pqrs:ArrayList<PQRs>,
    var error: String
)


data class PQRs(
    var id:Int,
    var tipo:String,
    var asunto:String,
    var estado:String,
    var fechaCreacion:String,
    var fechaLimite:String,
    var vigencia:String,
    var usuario: UsuarioDTO,
    var autor: UsuarioDTO,
    var error: String
)
