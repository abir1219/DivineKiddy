package com.textifly.divinekiddy.ui.Orders

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.textifly.divinekiddy.ApiManager.RetrofitHelper
import com.textifly.divinekiddy.CustomDialog.CustomProgressDialog
import com.textifly.divinekiddy.MainActivity
import com.textifly.divinekiddy.R
import com.textifly.divinekiddy.Utils.WebService
import com.textifly.divinekiddy.databinding.FragmentOrdersListBinding
import com.textifly.divinekiddy.ui.Orders.Adapter.OrderListAdapter
import com.textifly.divinekiddy.ui.Orders.Model.OrderListModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class OrdersListFragment : Fragment(), View.OnClickListener  {
    private var _binding: FragmentOrdersListBinding? = null
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
    ): View {
        _binding = FragmentOrdersListBinding.inflate(inflater, container, false)
        try {
            val bottomNav: BottomNavigationView? = activity?.findViewById(R.id.bottom_nav)
            bottomNav?.visibility = View.GONE
        } catch (e: Exception) {

        }
        val sharedPreference = requireActivity().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
        if(sharedPreference.contains("uid")){
            binding.rlNoRecordsFound.visibility = View.GONE
            binding.rvOrderList.visibility = View.VISIBLE
            loadOrderList(sharedPreference)
            setLayout()
        }else{
            binding.rlNoRecordsFound.visibility = View.VISIBLE
            binding.rvOrderList.visibility = View.GONE
        }
        btnClick()
        return binding.root
    }

    private fun btnClick() {
        binding.tvContinueShopping.setOnClickListener(this)
        binding.llMenu.setOnClickListener(this)
    }

    private fun loadOrderList(sharedPreference: SharedPreferences) {
        CustomProgressDialog.showDialog(requireContext(), true)
        val uid = sharedPreference.getString("uid", "")
        //Toast.makeText(requireContext(), "uid=>$uid", Toast.LENGTH_SHORT).show()
        retrofitApiInterface.orderList(uid).enqueue(object : Callback<OrderListModel?> {
            override fun onResponse(
                call: Call<OrderListModel?>,
                response: Response<OrderListModel?>
            ) {
                CustomProgressDialog.showDialog(requireContext(), false)
                Log.d("Cart_list", response.body()!!.toString())
                if (response.body()!!.status.equals("Success")) {
                    //Toast.makeText(requireContext(),"success",Toast.LENGTH_SHORT).show()
                    val orderListAdapter = OrderListAdapter(response.body()!!.orderList)
                    binding.rvOrderList.adapter = orderListAdapter
                }
            }

            override fun onFailure(
                call: Call<OrderListModel?>,
                t: Throwable
            ) {
                CustomProgressDialog.showDialog(requireContext(), false)
                Toast.makeText(requireContext(), "Fail", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setLayout() {
        binding.rvOrderList.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tvContinueShopping -> startActivity(
                Intent(requireContext(),
                    MainActivity::class.java)
            )
            R.id.llMenu -> activity?.onBackPressed()
        }
    }
}