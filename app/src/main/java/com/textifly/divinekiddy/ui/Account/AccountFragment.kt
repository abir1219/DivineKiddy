package com.textifly.divinekiddy.ui.Account

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences.Editor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.textifly.divinekiddy.CustomDialog.CustomProgressDialog
import com.textifly.divinekiddy.Helper
import com.textifly.divinekiddy.MainActivity
import com.textifly.divinekiddy.R
import com.textifly.divinekiddy.databinding.FragmentAccountBinding

class AccountFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    override fun onResume() {
        super.onResume()
        try {
            val bottomNav: BottomNavigationView? = activity?.findViewById(R.id.bottom_nav)
            bottomNav?.visibility = View.VISIBLE
        } catch (e: Exception) {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        try {
            val bottomNav: BottomNavigationView? = activity?.findViewById(R.id.bottom_nav)
            bottomNav?.visibility = View.VISIBLE
        } catch (e: Exception) {

        }
        btnClick()
        initView()
        return binding.root
    }

    private fun initView() {
        val sharedPreference = requireActivity().getSharedPreferences("PREFERENCE",Context.MODE_PRIVATE)
        if(sharedPreference.contains("uid")){
            binding.llBeforeLoginTop.visibility = View.GONE
            binding.llBeforeLoginBottom.visibility = View.GONE
            binding.llAfterLoginTop.visibility = View.VISIBLE
            binding.llAfterLoginBottom.visibility = View.VISIBLE

            binding.tvName.text = sharedPreference.getString("uname","")
            binding.tvMobileNo.text = sharedPreference.getString("mobile","")
        }else{
            binding.llBeforeLoginTop.visibility = View.VISIBLE
            binding.llBeforeLoginBottom.visibility = View.VISIBLE
            binding.llAfterLoginTop.visibility = View.GONE
            binding.llAfterLoginBottom.visibility = View.GONE
        }

        if(sharedPreference.contains("image")){
            Toast.makeText(requireContext(),"Image Have",Toast.LENGTH_SHORT).show()
            if(sharedPreference.getString("image","")!=null){
                Toast.makeText(requireContext(),"Image -> ${sharedPreference.getString("image","")}",Toast.LENGTH_SHORT).show()
                Glide.with(requireContext())
                    .load("https://divinekiddy.com/uploads/profile/${sharedPreference.getString("image","")}")
                    .into(binding.ivProfileImg)
            }else{
                Glide.with(requireContext())
                    .load(R.drawable.avatar)
                    .into(binding.ivProfileImg)
            }
        }

    }//ivProfileImg

    private fun btnClick() {
        binding.rlOrderList.setOnClickListener(this)
        binding.llSavedAddress.setOnClickListener(this)
        binding.rlWishlist.setOnClickListener(this)
        binding.rlCancelOrder.setOnClickListener(this)
        binding.tvSignIn.setOnClickListener(this)
        binding.tvJoinUs.setOnClickListener(this)
        binding.tvSignOut.setOnClickListener(this)
        binding.rlProfileDetails.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        val sharedPreference =  requireActivity().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)

        when (view?.id) {
            R.id.llSavedAddress -> //Navigation.findNavController()?.navigate(R.id.nav_account_to_saved_address)
            {
                if (sharedPreference.contains("uid")){
                    view.findNavController().navigate(R.id.nav_account_to_saved_address)
                }else{
                    view.findNavController().navigate(R.id.nav_account_to_signin_otp)
                }
            }
            R.id.rlWishlist -> view.findNavController().navigate(R.id.nav_account_to_navigation_wishlist)

            R.id.rlOrderList -> {
                val sharedPreference = requireActivity().getSharedPreferences("PREFERENCE",Context.MODE_PRIVATE)
                if(sharedPreference.contains("uid")) {
                    view.findNavController().navigate(R.id.nav_account_to_order_list)
                }else{
                    view.findNavController().navigate(R.id.nav_account_to_signin_otp)
                }
            }

            R.id.rlCancelOrder -> {
                if (sharedPreference.contains("uid")){
                    view.findNavController().navigate(R.id.nav_account_to_navigation_cancel_order)
                }else{
                    view.findNavController().navigate(R.id.nav_account_to_signin_otp)
                }
            }
            R.id.tvSignIn -> view.findNavController().navigate(R.id.nav_account_to_signin_otp)
            R.id.tvJoinUs -> view.findNavController().navigate(R.id.nav_account_to_join_us)
            R.id.rlProfileDetails -> {
                if (sharedPreference.contains("uid")) {
                    view.findNavController().navigate(R.id.nav_account_to_profile_details)
                } else {
                    view.findNavController().navigate(R.id.nav_account_to_signin_otp)
                }
            }
            R.id.tvSignOut -> {
                /*Helper.isSignIn = false
                activity?.recreate()*/
                CustomProgressDialog.showDialog(requireContext(),true)
                val sharedPreference = requireActivity().getSharedPreferences("PREFERENCE",Context.MODE_PRIVATE)
                if(sharedPreference.contains("uid")){
                    val editor = sharedPreference.edit()
                    editor.clear()
                    editor.commit()
                    CustomProgressDialog.showDialog(requireContext(),false)
                    Toast.makeText(requireContext(),"Logout Successful",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(requireActivity(), MainActivity::class.java))
                    requireActivity().finish()
                }else{
                    Toast.makeText(requireContext(),"Getting some troubles",Toast.LENGTH_SHORT).show()
                    CustomProgressDialog.showDialog(requireContext(),false)
                }
            }
        }
    }
}