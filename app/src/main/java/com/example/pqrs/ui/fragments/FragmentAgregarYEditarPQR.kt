package com.example.pqrs.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pqrs.R
import com.example.pqrs.databinding.FragmentAgregarYEditarPQRBinding

class FragmentAgregarYEditarPQR : BaseFragment() {

    lateinit var b:FragmentAgregarYEditarPQRBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b= FragmentAgregarYEditarPQRBinding.bind(inflater.inflate(R.layout.fragment_agregar_y_editar_p_q_r, container, false))
       return b.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


    }
}