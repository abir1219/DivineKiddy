package com.textifly.divinekiddy.ui.Cancel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.textifly.divinekiddy.R
import com.textifly.divinekiddy.databinding.FragmentCancelOrderBinding

class CancelOrderFragment : Fragment(),View.OnClickListener {
    private var _binding : FragmentCancelOrderBinding? = null
    private val binding get() = _binding!!

    override fun onResume() {
        super.onResume()
        try {
            val bottomNav : BottomNavigationView?  = activity?.findViewById(R.id.bottom_nav)
            bottomNav?.visibility = View.GONE
        }catch (e:Exception){}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCancelOrderBinding.inflate(inflater,container,false)
        try {
            val bottomNav : BottomNavigationView?  = activity?.findViewById(R.id.bottom_nav)
            bottomNav?.visibility = View.GONE
        }catch (e:Exception){}
        btnClick()
        return binding.root
    }

    private fun btnClick() {
        binding.tvCancel.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.tvCancel -> view.findNavController().navigate(R.id.navigation_cancel_order_to_cancel_success)
        }
    }

}