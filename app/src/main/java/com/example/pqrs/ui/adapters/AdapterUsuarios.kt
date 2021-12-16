package com.example.pqrs.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pqrs.R
import com.example.pqrs.model.response.PQRs

class AdapterUsuarios(lista:ArrayList<String>):RecyclerView.Adapter<AdapterUsuarios.TheViewholder>() {


    var list=lista

    lateinit var listener:Onclick

    class TheViewholder(v:View):RecyclerView.ViewHolder(v)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TheViewholder {
        return TheViewholder(LayoutInflater.from(parent.context).inflate(R.layout.simpleitemusers,parent,false))
    }

    override fun onBindViewHolder(holder: AdapterUsuarios.TheViewholder, position: Int) {
       var ad=holder.itemView

        var user=list[position]
        var cuadroUsers=ad.findViewById<TextView>(R.id.tvUsers)
        cuadroUsers.text=user

          ad.setOnClickListener {listener.click(user)}
    }

    override fun getItemCount()=list.size


    fun addOnClickListener(listener:Onclick) {
        this.listener=listener

    }
}

interface Onclick {

    fun click(item:String)
}