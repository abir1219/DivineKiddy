package com.textifly.divinekiddy.ui.ShippingAddress

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.textifly.divinekiddy.R
import com.textifly.divinekiddy.databinding.FragmentShippingAddressBinding

class ShippingAddressFragment : Fragment(),View.OnClickListener {
    private var _binding : FragmentShippingAddressBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentShippingAddressBinding.inflate(inflater,container,false)
        btnClick()
        return binding.root
    }

    private fun btnClick() {
        binding.tvContinue.setOnClickListener(this)
        binding.ivBack.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.tvContinue -> view.findNavController().navigate(R.id.navigation_shipping_address_to_order_summery)
            R.id.ivBack -> activity?.onBackPressed()
        }
    }
}