package com.textifly.divinekiddy.ui.OrderSuccess

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.textifly.divinekiddy.ApiManager.RetrofitHelper
import com.textifly.divinekiddy.CustomDialog.CustomProgressDialog
import com.textifly.divinekiddy.R
import com.textifly.divinekiddy.Utils.WebService
import com.textifly.divinekiddy.databinding.FragmentAllDoneBinding
import com.textifly.divinekiddy.ui.Cart.Adapter.CartAdapter
import com.textifly.divinekiddy.ui.Cart.CartFragment
import com.textifly.divinekiddy.ui.Cart.Model.CartListModel
import com.textifly.divinekiddy.ui.OrderSuccess.Adapter.OrderSuccessAdapter
import com.textifly.divinekiddy.ui.OrderSuccess.Model.OrderSuccessModel
import com.textifly.divinekiddy.ui.Orders.Model.OrderListModel
import com.textifly.divinekiddy.ui.SavedAddress.Model.AddressList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AllDoneFragment : Fragment(),View.OnClickListener {
    private var _binding: FragmentAllDoneBinding? = null
    private val binding get() = _binding!!

    private var retrofitHelper = RetrofitHelper.getRetrofitInstance()
    private var retrofitApiInterface = retrofitHelper.create(WebService::class.java)

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
        initView()
        btnClick()
        setLayout()
        loadOrderList()
        val sharedPreference = requireActivity().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
        loadingAddress(sharedPreference)
        return binding.root
    }

    private fun initView() {
        
        binding.tvTotalItemPrice.text="₹"+arguments?.getString("totalPrice")
        binding.tvTotal.text="You Pay ₹"+arguments?.getString("totalPrice")
        binding.tvDiscount.text="₹"+arguments?.getString("discountPrice")
        
    }

    private fun loadingAddress(sharedPreference: SharedPreferences) {
        //Toast.makeText(requireContext(),"hii",Toast.LENGTH_SHORT).show()
        //Toast.makeText(requireContext(),"${sharedPreference.getString("addressId","")}",Toast.LENGTH_SHORT).show()
        retrofitApiInterface.getAddressById(sharedPreference.getString("addressId","")).
        enqueue(object : Callback<AddressList?> {
            override fun onResponse(call: Call<AddressList?>, response: Response<AddressList?>) {
                binding.tvName.text = response.body()!!.name
                binding.tvPhone.text = response.body()!!.mobile
                binding.tvAddress.text = response.body()!!.address +", "+response.body()!!.city+", "+response.body()!!.state+" - "+response.body()!!.pin
            }

            override fun onFailure(call: Call<AddressList?>, t: Throwable) {
                Toast.makeText(requireActivity(),"Getting some troubles",Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setLayout() {
        binding.rvCart.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
    }

    private fun loadOrderList() {
        CustomProgressDialog.showDialog(requireContext(), true)
        val sharedPreference =
            requireActivity().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
        val uid = sharedPreference.getString("uid", "")
        val order_id = sharedPreference.getString("order_id", "")
        //Toast.makeText(requireContext(), "uid=>$uid", Toast.LENGTH_SHORT).show()
        retrofitApiInterface.successOrderDetails(order_id).enqueue(object : Callback<OrderSuccessModel?> {
            override fun onResponse(
                call: Call<OrderSuccessModel?>,
                response: Response<OrderSuccessModel?>
            ) {
                CustomProgressDialog.showDialog(requireContext(), false)
                Log.d("Cart_list", response.body()!!.toString())
                if (response.body()!!.status.equals("Success")) {
                    //Toast.makeText(requireContext(),"success",Toast.LENGTH_SHORT).show()
                    val orderSuccessAdapter = OrderSuccessAdapter(response.body()!!.list)
                    binding.rvCart.adapter = orderSuccessAdapter
                }
            }

            override fun onFailure(
                call: Call<OrderSuccessModel?>,
                t: Throwable
            ) {
                CustomProgressDialog.showDialog(requireContext(), false)
                Toast.makeText(requireContext(), "Fail", Toast.LENGTH_SHORT).show()
            }
        })
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