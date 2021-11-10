package com.example.pqrs.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pqrs.R
import com.example.pqrs.databinding.FragmentDetallesPQRBinding


class FragmentDetallesPQR : BaseFragment() {

    lateinit var b:FragmentDetallesPQRBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        b= FragmentDetallesPQRBinding.bind(inflater.inflate(R.layout.fragment_detalles_p_q_r, container, false))
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


    }
}