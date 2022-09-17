package com.textifly.divinekiddy.ui.OrderSuccess

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.textifly.divinekiddy.R
import com.textifly.divinekiddy.databinding.FragmentAllDoneBinding

class AllDoneFragment : Fragment(),View.OnClickListener {
    private var _binding: FragmentAllDoneBinding? = null
    private val binding get() = _binding!!

    override fun onResume() {
        super.onResume()
        try {
            val bottom_nav: BottomNavigationView? = activity?.findViewById(R.id.bottom_nav)
            bottom_nav?.visibility = View.GONE
        } catch (e: Exception) {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAllDoneBinding.inflate(inflater, container, false)
        try {
            val bottom_nav: BottomNavigationView? = activity?.findViewById(R.id.bottom_nav)
            bottom_nav?.visibility = View.GONE
        } catch (e: Exception) {
        }
        btnClick()
        return binding.root
    }

    private fun btnClick() {
        binding.tvContinueShopping.setOnClickListener(this)
        binding.llMenu.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.tvContinueShopping -> view.findNavController().navigate(R.id.navigation_success_to_discover)
            R.id.llMenu ->activity?.onBackPressed()
        }
    }
}