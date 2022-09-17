package com.textifly.divinekiddy.ui.Offer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.textifly.divinekiddy.R
import com.textifly.divinekiddy.databinding.FragmentOfferBinding

class OfferFragment : Fragment(),View.OnClickListener {
    private var _binding : FragmentOfferBinding? = null
    private val binding get() = _binding!!
    var isCard1Clicked = false
    var isCard2Clicked = false
    var isCard3Clicked = false

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
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentOfferBinding.inflate(inflater,container,false)
        try {
            val bottomNav : BottomNavigationView?  = activity?.findViewById(R.id.bottom_nav)
            bottomNav?.visibility = View.GONE
        }catch (e:Exception){}
        btnClink()
        return binding.root
    }

    private fun btnClink() {
        binding.llCard1.setOnClickListener(this)
        binding.llCard2.setOnClickListener(this)
        binding.llCard3.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.llCard1 -> animateCard1()
            R.id.llCard2 -> animateCard2()
            R.id.llCard3 -> animateCard3()
        }
    }

    private fun animateCard3() {
        if(isCard3Clicked) {
            binding.iCvard3Dropdown.startAnimation(
                AnimationUtils.loadAnimation(
                    activity,
                    R.anim.rotate_anticlockwise
                )
            )
            binding.tvCoupon3.visibility = View.GONE
            isCard3Clicked = false
        }
        else {
            binding.iCvard3Dropdown.startAnimation(
                AnimationUtils.loadAnimation(
                    activity,
                    R.anim.rotate_clockwise
                )
            )
            binding.tvCoupon3.visibility = View.VISIBLE
            //view.findNavController().navigate(R.id.navigation_payment_to_success)
            isCard3Clicked = true
        }
    }

    private fun animateCard2() {
        if(isCard2Clicked) {
            binding.iCvard2Dropdown.startAnimation(
                AnimationUtils.loadAnimation(
                    activity,
                    R.anim.rotate_anticlockwise
                )
            )
            binding.tvCoupon2.visibility = View.GONE
            isCard2Clicked = false
        }
        else {
            binding.iCvard2Dropdown.startAnimation(
                AnimationUtils.loadAnimation(
                    activity,
                    R.anim.rotate_clockwise
                )
            )
            binding.tvCoupon2.visibility = View.VISIBLE
            //view.findNavController().navigate(R.id.navigation_payment_to_success)
            isCard2Clicked = true
        }
    }

    private fun animateCard1() {
        if(isCard1Clicked) {
            binding.iCvard1Dropdown.startAnimation(
                AnimationUtils.loadAnimation(
                    activity,
                    R.anim.rotate_anticlockwise
                )
            )
            binding.tvCoupon1.visibility = View.GONE
            isCard1Clicked = false
        }
        else {
            binding.iCvard1Dropdown.startAnimation(
                AnimationUtils.loadAnimation(
                    activity,
                    R.anim.rotate_clockwise
                )
            )
            binding.tvCoupon1.visibility = View.VISIBLE
            //view.findNavController().navigate(R.id.navigation_payment_to_success)
            isCard1Clicked = true
        }
    }
}