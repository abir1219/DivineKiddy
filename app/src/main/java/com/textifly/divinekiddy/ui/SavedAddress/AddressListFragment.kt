package com.textifly.divinekiddy.ui.SavedAddress

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.textifly.divinekiddy.R
import com.textifly.divinekiddy.databinding.FragmentAddressListBinding

class AddressListFragment : Fragment(),View.OnClickListener {
    private var _binding : FragmentAddressListBinding? = null
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
        // Inflate the layout for this fragment
        _binding = FragmentAddressListBinding.inflate(inflater,container,false)
        try {
            var bottomNav : BottomNavigationView?  = activity?.findViewById(R.id.bottom_nav)
            bottomNav?.visibility = View.GONE
        }catch (e:Exception){}

        btnClick()
        return binding.root
    }

    private fun btnClick() {
        binding.ivBack.setOnClickListener(this)
        binding.llAddAddress.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.ivBack -> view.findNavController().navigate(R.id.nav_address_list_to_account)
            R.id.llAddAddress -> view.findNavController().navigate(R.id.nav_address_list_to_add_address)
        }
    }

}