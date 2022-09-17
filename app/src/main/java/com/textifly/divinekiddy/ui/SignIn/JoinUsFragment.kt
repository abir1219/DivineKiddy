package com.textifly.divinekiddy.ui.SignIn

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.textifly.divinekiddy.Helper
import com.textifly.divinekiddy.R
import com.textifly.divinekiddy.Utils.WebService
import com.textifly.divinekiddy.databinding.FragmentJoinUsBinding
import com.textifly.divinekiddy.ApiManager.RetrofitHelper
import com.textifly.divinekiddy.ui.Products.Adapter.ProductsAdapter
import com.textifly.divinekiddy.ui.Products.Model.ProductsModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class JoinUsFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentJoinUsBinding? = null
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
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentJoinUsBinding.inflate(inflater, container, false)
        try {
            val bottomNav: BottomNavigationView? = activity?.findViewById(R.id.bottom_nav)
            bottomNav?.visibility = View.GONE
        } catch (e: Exception) {
        }
        btnClick()
        return binding.root
    }

    private fun btnClick() {
        binding.llBack.setOnClickListener(this)
        binding.tvSignIn.setOnClickListener(this)
        binding.tvSendOtp.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.llBack -> activity?.onBackPressed()
            R.id.tvSignIn ->
                view.findNavController().navigate(R.id.nav_join_us_to_signin)
            R.id.tvSendOtp -> {
                Helper.isSignIn = true
                dataVaidation()
            }
        }
    }

    private fun dataVaidation() {
        if (binding.etFullName.text.isNotEmpty() && binding.etEmail.text.isNotEmpty() && binding.etMobile.text.isNotEmpty()) {
            val bundle = Bundle()
            bundle.putString("mobile", "${binding.etMobile.text}")
            bundle.putString("name", "${binding.etFullName.text}")
            bundle.putString("email", "${binding.etEmail.text}")
            bundle.putString("page", "join")

            view?.findNavController()?.navigate(R.id.nav_join_us_to_otp_verify, bundle)

            //joinUser()
        } else {
            Toast.makeText(activity, "Fill all the fields", Toast.LENGTH_LONG).show()
        }
    }

}