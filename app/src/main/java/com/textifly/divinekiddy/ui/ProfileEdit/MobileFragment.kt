package com.textifly.divinekiddy.ui.ProfileEdit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.textifly.divinekiddy.R
import com.textifly.divinekiddy.databinding.FragmentMobileBinding
import com.textifly.divinekiddy.databinding.FragmentNameChangeBinding

class MobileFragment : Fragment(),View.OnClickListener {
    private var _binding : FragmentMobileBinding? = null
    private val  binding get() = _binding!!

    override fun onResume() {
        super.onResume()
        try {
            var bottomNav : BottomNavigationView?  = activity?.findViewById(R.id.bottom_nav)
            bottomNav?.visibility = View.GONE
        }catch (e:Exception){}
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMobileBinding.inflate(inflater,container,false)
        try {
            var bottomNav : BottomNavigationView?  = activity?.findViewById(R.id.bottom_nav)
            bottomNav?.visibility = View.GONE
        }catch (e:Exception){}
        btnClick()
        return binding.root
    }

    private fun btnClick() {
        binding.llMenu.setOnClickListener(this)
        binding.tvSaveMobile.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.llMenu -> view.findNavController().navigate(R.id.nav_mobile_change_to_profile_details)
            R.id.tvSaveMobile -> view.findNavController().navigate(R.id.nav_mobile_change_to_profile_details)
        }
    }
}