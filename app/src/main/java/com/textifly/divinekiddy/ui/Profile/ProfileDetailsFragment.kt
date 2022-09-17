package com.textifly.divinekiddy.ui.Profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.textifly.divinekiddy.R
import com.textifly.divinekiddy.databinding.FragmentAddressListBinding
import com.textifly.divinekiddy.databinding.FragmentProfileDetailsBinding

class ProfileDetailsFragment : Fragment(),View.OnClickListener {
    private var _binding : FragmentProfileDetailsBinding? = null
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
        _binding = FragmentProfileDetailsBinding.inflate(inflater,container,false)
        try {
            var bottomNav : BottomNavigationView?  = activity?.findViewById(R.id.bottom_nav)
            bottomNav?.visibility = View.GONE
        }catch (e:Exception){}
        btnClick()
        return binding.root
    }

    private fun btnClick() {
        binding.ivBack.setOnClickListener(this)
        binding.rlName.setOnClickListener(this)
        binding.rlMobile.setOnClickListener(this)
        binding.rlEmail.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.ivBack -> view.findNavController().navigate(R.id.nav_profile_details_to_account)
            R.id.rlName -> view.findNavController().navigate(R.id.nav_profile_details_to_name_change)
            R.id.rlMobile -> view.findNavController().navigate(R.id.nav_profile_details_to_mobile_change)
        }
    }
}