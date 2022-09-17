package com.textifly.divinekiddy.ui.Payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.textifly.divinekiddy.R
import com.textifly.divinekiddy.databinding.FragmentPaymentBinding

class PaymentFragment : Fragment() ,View.OnClickListener{
    private var _binding: FragmentPaymentBinding? = null
    private val binding get() = _binding!!
    var isUpiClicked = false
    var isCardClicked = false
    var isPaytmClicked = false
    var isNetBankigClicked = false
    var isCodClicked = false
    var isEmiClicked = false

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
        _binding = FragmentPaymentBinding.inflate(inflater,container,false)
        try {
            var bottomNav : BottomNavigationView?  = activity?.findViewById(R.id.bottom_nav)
            bottomNav?.visibility = View.GONE
        }catch (e:Exception){}
        btnClick()
        return binding.root
    }

    private fun btnClick() {
        binding.llUpi.setOnClickListener(this)
        binding.llCard.setOnClickListener(this)
        binding.llPayment.setOnClickListener(this)
        binding.llCod.setOnClickListener(this)
        binding.llNetBanking.setOnClickListener(this)
        binding.llEmi.setOnClickListener(this)
        binding.llMenu.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.llUpi -> openUpiLayout(view)
            R.id.llCard -> openCardLayout(view)
            R.id.llPayment -> openPaytmLayout(view)
            R.id.llCod -> openCodLayout(view)
            R.id.llNetBanking -> openNetBankingLayout(view)
            R.id.llEmi -> openEmiLayout(view)
            R.id.llMenu -> activity?.onBackPressed()
        }
    }
//navigation_payment_to_success
    private fun openUpiLayout(view: View) {
        if(isUpiClicked) {
            binding.ivUpiDropdown.startAnimation(
                AnimationUtils.loadAnimation(
                    activity,
                    R.anim.rotate_anticlockwise
                )
            )
            isUpiClicked = false
        }
        else {
            binding.ivUpiDropdown.startAnimation(
                AnimationUtils.loadAnimation(
                    activity,
                    R.anim.rotate_clockwise
                )
            )
            view.findNavController().navigate(R.id.navigation_payment_to_success)
            isUpiClicked = true
        }
    }

    private fun openCardLayout(view: View) {
        if(isCardClicked) {
            binding.ivCardDropdown.startAnimation(
                AnimationUtils.loadAnimation(
                    activity,
                    R.anim.rotate_anticlockwise
                )
            )
            isCardClicked = false
        }
        else {
            binding.ivCardDropdown.startAnimation(
                AnimationUtils.loadAnimation(
                    activity,
                    R.anim.rotate_clockwise
                )
            )
            view.findNavController().navigate(R.id.navigation_payment_to_success)
            isCardClicked = true
        }
    }

    private fun openPaytmLayout(view: View) {
        if(isPaytmClicked) {
            binding.ivPaytmDropdown.startAnimation(
                AnimationUtils.loadAnimation(
                    activity,
                    R.anim.rotate_anticlockwise
                )
            )
            isPaytmClicked = false
        }
        else {
            binding.ivPaytmDropdown.startAnimation(
                AnimationUtils.loadAnimation(
                    activity,
                    R.anim.rotate_clockwise
                )
            )
            view.findNavController().navigate(R.id.navigation_payment_to_success)
            isPaytmClicked = true
        }
    }

    private fun openCodLayout(view: View) {
        if(isCodClicked) {
            binding.ivCodDropdown.startAnimation(
                AnimationUtils.loadAnimation(
                    activity,
                    R.anim.rotate_anticlockwise
                )
            )
            isCodClicked = false
        }
        else {
            binding.ivCodDropdown.startAnimation(
                AnimationUtils.loadAnimation(
                    activity,
                    R.anim.rotate_clockwise
                )
            )
            view.findNavController().navigate(R.id.navigation_payment_to_success)
            isCodClicked = true
        }
    }

    private fun openNetBankingLayout(view: View) {
        if(isNetBankigClicked) {
            binding.ivNetBankingDropdown.startAnimation(
                AnimationUtils.loadAnimation(
                    activity,
                    R.anim.rotate_anticlockwise
                )
            )
            isNetBankigClicked = false
        }
        else {
            binding.ivNetBankingDropdown.startAnimation(
                AnimationUtils.loadAnimation(
                    activity,
                    R.anim.rotate_clockwise
                )
            )
            view.findNavController().navigate(R.id.navigation_payment_to_success)
            isNetBankigClicked = true
        }
    }

    private fun openEmiLayout(view: View) {
        if(isEmiClicked) {
            binding.ivEmiDropdown.startAnimation(
                AnimationUtils.loadAnimation(
                    activity,
                    R.anim.rotate_anticlockwise
                )
            )
            isEmiClicked = false
        }
        else {
            binding.ivEmiDropdown.startAnimation(
                AnimationUtils.loadAnimation(
                    activity,
                    R.anim.rotate_clockwise
                )
            )
            view.findNavController().navigate(R.id.navigation_payment_to_success)
            isEmiClicked = true
        }
    }
}