package com.example.pqrs.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pqrs.R
import com.example.pqrs.databinding.FragmentDetallePQRBinding


class FragmentDetallesPQR : BaseFragment() {

    lateinit var b:FragmentDetallePQRBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        b= FragmentDetallePQRBinding.bind(inflater.inflate(R.layout.fragment_detalle_p_q_r, container, false))
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {



    }
}