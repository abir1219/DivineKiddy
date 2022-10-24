package com.textifly.divinekiddy.ui.Cancel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.textifly.divinekiddy.CustomDialog.CustomProgressDialog
import com.textifly.divinekiddy.R
import com.textifly.divinekiddy.databinding.FragmentCancelSuccessBinding

class CancelSuccessFragment : Fragment(),View.OnClickListener {
    private var _binding :FragmentCancelSuccessBinding? = null
    private val  binding get() = _binding!!

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
        _binding = FragmentCancelSuccessBinding.inflate(inflater,container,false)
        try {
            val bottomNav : BottomNavigationView?  = activity?.findViewById(R.id.bottom_nav)
            bottomNav?.visibility = View.GONE
        }catch (e:Exception){}
        btnClick()
        loadProduct()
        return binding.root
    }

    private fun loadProduct() {
        CustomProgressDialog.showDialog(requireContext(), true)
        binding.tvProdName.text = arguments?.getString("prod_name")
        binding.tvProdAge.text = arguments?.getString("prod_age")
        binding.tvQuantity.text = arguments?.getString("prod_qty")
        Glide.with(requireContext())
            .load("https://divinekiddy.com/uploads/product/" + arguments?.getString("prod_img"))
            .placeholder(R.drawable.loader).into(binding.ivImage)
        CustomProgressDialog.showDialog(requireContext(), false)
    }

    private fun btnClick() {
        binding.tvContinueShopping.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.tvContinueShopping->view.findNavController().navigate(R.id.navigation_cancel_success_to_discover)
        }
    }
}