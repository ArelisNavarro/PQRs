package com.example.pqrs.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pqrs.R
import com.example.pqrs.databinding.ItemListaPqrBinding
import com.example.pqrs.model.response.PQRs

class AdapterPQR:RecyclerView.Adapter<AdapterPQR.Miviewholder>(){

     var lista = ArrayList<PQRs>()

    class Miviewholder( v: View) : RecyclerView.ViewHolder(v){

         var binding:ItemListaPqrBinding = ItemListaPqrBinding.bind(v)

    }

     override fun onCreateViewHolder(parent:ViewGroup,viewType:Int):Miviewholder{
         return Miviewholder(LayoutInflater.from(parent.context).inflate(R.layout.item_lista_pqr,parent,false))

     }

     override fun onBindViewHolder(holder: Miviewholder, position: Int) {
        var pqr= lista[position]

         holder.binding.pqr=PQRitem("${pqr.tipo}","Usuario: ${pqr.usuario.nombre} ${pqr.usuario.apellido}","Estado: ${pqr.estado}")

       var v= holder.itemView

     }

     override fun getItemCount():Int{
         var sise=lista.size
         return sise
     }

    fun setListPqrs(list:ArrayList<PQRs>){
        lista=list
        notifyDataSetChanged()
    }

 }


data class PQRitem(var tipo:String,var usuario:String,var estado:String)