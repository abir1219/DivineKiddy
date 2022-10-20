package com.textifly.divinekiddy.ui.Payment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.textifly.divinekiddy.ApiManager.RetrofitHelper
import com.textifly.divinekiddy.CustomDialog.CustomProgressDialog
import com.textifly.divinekiddy.R
import com.textifly.divinekiddy.Utils.WebService
import com.textifly.divinekiddy.databinding.FragmentPaymentBinding
import com.textifly.divinekiddy.ui.ProductDetails.Model.CartModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentFragment : Fragment() ,View.OnClickListener{
    private var _binding: FragmentPaymentBinding? = null
    private val binding get() = _binding!!
    var isUpiClicked = false
    var isCardClicked = false
    var isPaytmClicked = false
    var isNetBankigClicked = false
    var isCodClicked = false
    var isEmiClicked = false

    val bundle = Bundle()

    private var retrofitHelper = RetrofitHelper.getRetrofitInstance()
    private var retrofitApiInterface = retrofitHelper.create(WebService::class.java)

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
        initView()
        return binding.root
    }

    private fun initView() {
        binding.tvTotalItemPrice.text="₹"+arguments?.getString("totalPrice")
        binding.tvTotalPrice.text="₹"+arguments?.getString("totalPrice")
        binding.tvTotal.text="You Pay ₹"+arguments?.getString("totalPrice")
        binding.tvDiscount.text="₹"+arguments?.getString("discountPrice")

        bundle.putString("totalPrice",arguments?.getString("totalPrice"))
        bundle.putString("discountPrice",arguments?.getString("discountPrice"))
    }

    private fun btnClick() {
        /*binding.llUpi.setOnClickListener(this)
        binding.llCard.setOnClickListener(this)*/
        binding.llPayment.setOnClickListener(this)
        binding.llCod.setOnClickListener(this)
        binding.llNetBanking.setOnClickListener(this)
        binding.llEmi.setOnClickListener(this)
        binding.llMenu.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            /*R.id.llUpi -> openUpiLayout(view)
            R.id.llCard -> openCardLayout(view)*/
            //R.id.llPayment -> openPaytmLayout(view)
            R.id.llCod -> openCodLayout(view)
            R.id.llNetBanking -> openNetBankingLayout(view)
            R.id.llEmi -> openEmiLayout(view)
            R.id.llMenu -> activity?.onBackPressed()
        }
    }
//navigation_payment_to_success
private fun checkOut() {
    val prefs = requireActivity().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
      CustomProgressDialog.showDialog(requireContext(),true)
          retrofitApiInterface.placeOrder(prefs.getString("uid",""),prefs.getString("addressId","")).enqueue(object : Callback<CartModel?> {
              override fun onResponse(call: Call<CartModel?>, response: Response<CartModel?>) {
                  CustomProgressDialog.showDialog(requireContext(),false)
                  if(response.body()!!.status.equals("Success", ignoreCase = true)){
                      var edit = prefs.edit()
                      edit.putString("order_id",response.body()!!.order_id)
                      edit.commit()
                      Toast.makeText(requireContext(),"Order Placed Successfully",Toast.LENGTH_SHORT).show()
                      //view?.findNavController()?.navigate(R.id.nav_cart_to_shipping_address)
                  }else{
                      Toast.makeText(requireContext(),"Order Placed un-successful",Toast.LENGTH_SHORT).show()
                  }
              }

              override fun onFailure(call: Call<CartModel?>, t: Throwable) {
                  CustomProgressDialog.showDialog(requireContext(),false)
                  Toast.makeText(requireContext(),"Getting some troubles",Toast.LENGTH_SHORT).show()
              }
          })
}

    /*private fun openUpiLayout(view: View) {
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
            checkOut()
            view.findNavController().navigate(R.id.navigation_payment_to_success,bundle)
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
            checkOut()
            view.findNavController().navigate(R.id.navigation_payment_to_success,bundle)
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
            checkOut()
            view.findNavController().navigate(R.id.navigation_payment_to_success,bundle)
            isPaytmClicked = true
        }
    }*/

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
            checkOut()
            view.findNavController().navigate(R.id.navigation_payment_to_success,bundle)
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
            checkOut()
            view.findNavController().navigate(R.id.navigation_payment_to_success,bundle)
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
            checkOut()
            view.findNavController().navigate(R.id.navigation_payment_to_success,bundle)
            isEmiClicked = true
        }
    }
}