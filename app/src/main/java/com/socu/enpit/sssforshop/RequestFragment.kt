package com.socu.enpit.sssforshop


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_request.*
import kotlinx.android.synthetic.main.fragment_request.view.*
import kotlinx.android.synthetic.main.fragment_request.view.YoubouButton

class RequestFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_request, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        YoubouButton.setOnClickListener {
            val fragment = YoubouFragment()
            val fragmentManager = this.childFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            getChildFragmentManager().beginTransaction().add(R.id.container,fragment).commit()
        }

        //view!!.KaizenButton.setOnClickListener{
            //val fragment = KaizenFragment()
            //val fragmentManager = this.childFragmentManager
            //val fragmentTransaction = fragmentManager.beginTransaction()
            //fragmentTransaction.replace(R.id.requestContainer,fragment).commit()
        //}

       //view!!.KansouButton.setOnClickListener{
            //val fragment = KansouFragment()
            //val fragmentManager = this.childFragmentManager
            //val fragmentTransaction = fragmentManager.beginTransaction()
            //fragmentTransaction.replace(R.id.requestContainer,fragment).commit()
        //}
    }
}

