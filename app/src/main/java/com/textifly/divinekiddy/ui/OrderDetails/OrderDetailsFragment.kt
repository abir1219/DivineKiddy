package com.textifly.divinekiddy.ui.OrderDetails

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.textifly.divinekiddy.ApiManager.RetrofitHelper
import com.textifly.divinekiddy.CustomDialog.CustomProgressDialog
import com.textifly.divinekiddy.R
import com.textifly.divinekiddy.Utils.WebService
import com.textifly.divinekiddy.databinding.FragmentOrderDetailsBinding
import com.textifly.divinekiddy.databinding.FragmentOrdersListBinding
import com.textifly.divinekiddy.ui.OrderDetails.Model.OrderDetailsModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderDetailsFragment : Fragment(),View.OnClickListener {
    private var _binding: FragmentOrderDetailsBinding? = null
    private val binding get() = _binding!!

    private var retrofitHelper = RetrofitHelper.getRetrofitInstance()
    private var retrofitApiInterface = retrofitHelper.create(WebService::class.java)

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
        _binding = FragmentOrderDetailsBinding.inflate(inflater, container, false)
        try {
            val bottomNav: BottomNavigationView? = activity?.findViewById(R.id.bottom_nav)
            bottomNav?.visibility = View.GONE
        } catch (e: Exception) {

        }
        btnClick()
        loadOrderDetails()
        return binding.root
    }

    private fun btnClick() {
        binding.llMenu.setOnClickListener(this)
    }

    private fun loadOrderDetails() {
        CustomProgressDialog.showDialog(requireContext(),true)
        retrofitApiInterface.orderDetails(arguments?.getString("order_id")).enqueue(object : Callback<OrderDetailsModel?> {
            override fun onResponse(
                call: Call<OrderDetailsModel?>,
                response: Response<OrderDetailsModel?>
            ) {
                CustomProgressDialog.showDialog(requireContext(),false)
                Glide.with(requireActivity())
                    .load("https://divinekiddy.com/uploads/product/${response.body()!!.image}")
                    .into(binding.imageView)
                binding.tvProdName.text = response.body()!!.name
                binding.tvAge.text = response.body()!!.age

                binding.tvAddressName.text = response.body()!!.userAddressName
                binding.tvMobile.text = response.body()!!.mobile
                binding.tvAddress.text = response.body()!!.address +", "+response.body()!!.city+", "+response.body()!!.state+" -"+response.body()!!.pin
                binding.tvTotal.text = response.body()!!.price
                binding.tvSaveTotal.text = "You saved ₹ ${response.body()!!.discount} on this order"
                //sharedPreference.getString("totalDiscount","")
            }
            override fun onFailure(call: Call<OrderDetailsModel?>, t: Throwable) {
                CustomProgressDialog.showDialog(requireContext(),false)
            }
        })
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.llMenu -> activity?.onBackPressed()
        }
    }


}