package com.textifly.divinekiddy.ui.SignIn

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.textifly.divinekiddy.Helper
import com.textifly.divinekiddy.R
import com.textifly.divinekiddy.databinding.FragmentSigninMobileBinding

class SigninMobileFragment : Fragment(),View.OnClickListener {
    private var _binding : FragmentSigninMobileBinding? = null
    private val binding get() = _binding!!

    override fun onResume() {
        super.onResume()
        try {
            val bottomNav: BottomNavigationView? = activity?.findViewById(R.id.bottom_nav)
            bottomNav?.visibility = View.GONE
        } catch (e: Exception) {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSigninMobileBinding.inflate(inflater,container,false)
        try {
            val bottomNav: BottomNavigationView? = activity?.findViewById(R.id.bottom_nav)
            bottomNav?.visibility = View.GONE
        } catch (e: Exception) {

        }
        btnClick()
        return binding.root
    }

    private fun btnClick() {
        binding.llClose.setOnClickListener(this)
        binding.tvJoin.setOnClickListener(this)
        binding.tvUseEmail.setOnClickListener(this)
        binding.tvSendOtp.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.llClose -> view.findNavController().navigate(R.id.nav_signin_mobile_to_account)
            R.id.tvJoin -> view.findNavController().navigate(R.id.nav_signin_mobile_to_join_us)
            R.id.tvUseEmail -> view.findNavController().navigate(R.id.nav_signin_mobile_to_signin_otp)
            R.id.tvSendOtp -> {
                //Helper.isSignIn = true
                val bundle = Bundle()
                bundle.putString("mobile", "${binding.etMobile.text}")
                bundle.putString("page", "login")
                view.findNavController().navigate(R.id.nav_signin_mobile_to_verify_otp,bundle)
            }
        }
    }

}