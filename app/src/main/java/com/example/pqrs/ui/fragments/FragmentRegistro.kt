package com.example.pqrs.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pqrs.R
import com.example.pqrs.databinding.FragmentRegistroBinding


class FragmentRegistro : BaseFragment() {

    lateinit var b:FragmentRegistroBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b =FragmentRegistroBinding.bind(inflater.inflate(R.layout.fragment_registro, container, false))
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


    }


}