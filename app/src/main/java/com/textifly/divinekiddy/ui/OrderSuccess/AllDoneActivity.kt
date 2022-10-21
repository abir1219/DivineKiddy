package com.textifly.divinekiddy.ui.OrderSuccess

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.textifly.divinekiddy.ApiManager.RetrofitHelper
import com.textifly.divinekiddy.CustomDialog.CustomProgressDialog
import com.textifly.divinekiddy.MainActivity
import com.textifly.divinekiddy.R
import com.textifly.divinekiddy.Utils.WebService
import com.textifly.divinekiddy.databinding.ActivityAllDoneBinding
import com.textifly.divinekiddy.databinding.ActivityRazorPayBinding
import com.textifly.divinekiddy.ui.OrderSuccess.Adapter.OrderSuccessAdapter
import com.textifly.divinekiddy.ui.OrderSuccess.Model.OrderSuccessModel
import com.textifly.divinekiddy.ui.SavedAddress.Model.AddressList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AllDoneActivity : AppCompatActivity(),View.OnClickListener {
    private lateinit var binding: ActivityAllDoneBinding

    private var retrofitHelper = RetrofitHelper.getRetrofitInstance()
    private var retrofitApiInterface = retrofitHelper.create(WebService::class.java)

    override fun onResume() {
        super.onResume()
        try {
            val bottom_nav: BottomNavigationView? = findViewById(R.id.bottom_nav)
            bottom_nav?.visibility = View.GONE
        } catch (e: Exception) {
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_all_done)
        binding = ActivityAllDoneBinding.inflate(layoutInflater)
        try {
            val bottom_nav: BottomNavigationView? = findViewById(R.id.bottom_nav)
            bottom_nav?.visibility = View.GONE
        } catch (e: Exception) {
        }
        setContentView(binding.root)
        initView()
        btnClick()
        setLayout()
        loadOrderList()
        val sharedPreference = getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
        loadingAddress(sharedPreference)
    }

    private fun initView() {

        binding.tvTotalItemPrice.text="₹"+ intent.getStringExtra("total")
        binding.tvTotal.text="You Pay ₹"+ intent.getStringExtra("total")
        binding.tvOrderId.text="Order No: "+intent.getStringExtra("order_id")
        binding.tvDiscount.text="₹"+ intent.getStringExtra("discountPrice")

    }

    private fun loadingAddress(sharedPreference: SharedPreferences) {
        //Toast.makeText(this@AllDoneActivity,"hii",Toast.LENGTH_SHORT).show()
        //Toast.makeText(this@AllDoneActivity,"${sharedPreference.getString("addressId","")}",Toast.LENGTH_SHORT).show()
        retrofitApiInterface.getAddressById(sharedPreference.getString("addressId","")).
        enqueue(object : Callback<AddressList?> {
            override fun onResponse(call: Call<AddressList?>, response: Response<AddressList?>) {
                binding.tvName.text = response.body()!!.name
                binding.tvPhone.text = response.body()!!.mobile
                binding.tvAddress.text = response.body()!!.address +", "+response.body()!!.city+", "+response.body()!!.state+" - "+response.body()!!.pin
            }

            override fun onFailure(call: Call<AddressList?>, t: Throwable) {
                Toast.makeText(this@AllDoneActivity,"Getting some troubles", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setLayout() {
        binding.rvCart.layoutManager = LinearLayoutManager(this@AllDoneActivity, RecyclerView.VERTICAL, false)
    }

    private fun loadOrderList() {
        CustomProgressDialog.showDialog(this@AllDoneActivity, true)
        val sharedPreference =
            getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
        val uid = sharedPreference.getString("uid", "")
        val order_id = sharedPreference.getString("order_id", "")
        //Toast.makeText(this@AllDoneActivity, "uid=>$uid", Toast.LENGTH_SHORT).show()
        retrofitApiInterface.successOrderDetails(order_id).enqueue(object :
            Callback<OrderSuccessModel?> {
            override fun onResponse(
                call: Call<OrderSuccessModel?>,
                response: Response<OrderSuccessModel?>
            ) {
                CustomProgressDialog.showDialog(this@AllDoneActivity, false)
                Log.d("Cart_list", response.body()!!.toString())
                if (response.body()!!.status.equals("Success")) {
                    //Toast.makeText(this@AllDoneActivity,"success",Toast.LENGTH_SHORT).show()
                    val orderSuccessAdapter = OrderSuccessAdapter(response.body()!!.list)
                    binding.rvCart.adapter = orderSuccessAdapter
                }
            }

            override fun onFailure(
                call: Call<OrderSuccessModel?>,
                t: Throwable
            ) {
                CustomProgressDialog.showDialog(this@AllDoneActivity, false)
                Toast.makeText(this@AllDoneActivity, "Fail", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun btnClick() {
        binding.tvContinueShopping.setOnClickListener(this)
        binding.llMenu.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.tvContinueShopping -> {
                startActivity(Intent(this@AllDoneActivity, MainActivity::class.java))
            }
                //view.findNavController().navigate(R.id.navigation_success_to_discover)
            R.id.llMenu ->onBackPressed()
        }
    }
}