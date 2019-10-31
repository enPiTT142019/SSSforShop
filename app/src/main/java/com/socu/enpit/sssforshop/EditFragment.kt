package com.socu.enpit.sssforshop


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_edit.*

class EditFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
      menuButton.setOnClickListener {
            val fragment = EditMenuFragment()
            val fragmentManager = this.childFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.container, fragment).addToBackStack(null).commit()
        }

        newsButton.setOnClickListener {
            val fragment = EditNewsFragment()
            val fragmentManager = this.childFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.container, fragment).addToBackStack(null).commit()
        }
        return inflater.inflate(R.layout.fragment_edit, container, false)
    }
}
