package com.textifly.divinekiddy.ui.Cart

import android.content.Context
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.textifly.divinekiddy.ApiManager.RetrofitHelper
import com.textifly.divinekiddy.CustomDialog.CustomProgressDialog
import com.textifly.divinekiddy.R
import com.textifly.divinekiddy.Utils.WebService
import com.textifly.divinekiddy.databinding.FragmentCartBinding
import com.textifly.divinekiddy.ui.Cart.Adapter.CartAdapter
import com.textifly.divinekiddy.ui.Cart.Model.CartListModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartFragment : Fragment(),View.OnClickListener{
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private var retrofitHelper = RetrofitHelper.getRetrofitInstance()
    private var retrofitApiInterface = retrofitHelper.create(WebService::class.java)

    lateinit var modelList: ArrayList<CartListModel>

    override fun onResume() {
        super.onResume()
        try {
            val bottomNav: BottomNavigationView? = activity?.findViewById(R.id.bottom_nav)
            bottomNav?.visibility = GONE
        } catch (e: Exception) {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        try {
            val bottomNav: BottomNavigationView? = activity?.findViewById(R.id.bottom_nav)
            bottomNav?.visibility = GONE
        } catch (e: Exception) {

        }
        btnClick()
        setLayout()
        loadCartList()
        return binding.root
    }

    private fun btnClick() {
        binding.llMenu.setOnClickListener(this)
        binding.tvCheckout.setOnClickListener(this)
        binding.llCoupon.setOnClickListener(this)
    }

    private fun loadCartList() {
        activity?.let { CustomProgressDialog.showDialog(it,true) }
        modelList = ArrayList()
        val sharedPreference =  requireActivity().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
        if(sharedPreference.contains("uid")){
            val uid = sharedPreference.getString("uid","")
            Toast.makeText(requireContext(),"uid=>$uid",Toast.LENGTH_SHORT).show()
            retrofitApiInterface.cartList(uid,"").enqueue(object : Callback<CartListModel?> {
                override fun onResponse(
                    call: Call<CartListModel?>,
                    response: Response<CartListModel?>
                ) {
                    CustomProgressDialog.showDialog(requireContext(),false)
                    if(response.body()!!.status.equals("Success")){
                        //Toast.makeText(requireContext(),"success",Toast.LENGTH_SHORT).show()
                        val cartAdapter = CartAdapter(response.body()!!.list)
                        binding.rvCart.adapter = cartAdapter
                        cartAdapter.setListner(object : onDataRecived {
                            override fun onCallBack(pos: String?) {
                                loadCartList()
                            }
                        })


                    }else{
                        Toast.makeText(requireContext(),"error",Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(
                    call: Call<CartListModel?>,
                    t: Throwable
                ) {
                    CustomProgressDialog.showDialog(requireContext(),false)
                    Toast.makeText(requireContext(),"Fail",Toast.LENGTH_SHORT).show()
                }
            })
        }else{
            val device_id: String = Settings.Secure.getString(requireActivity().contentResolver,
                Settings.Secure.ANDROID_ID)
            retrofitApiInterface.cartList("",device_id).enqueue(object : Callback<CartListModel?> {
                override fun onResponse(
                    call: Call<CartListModel?>,
                    response: Response<CartListModel?>
                ) {
                    CustomProgressDialog.showDialog(requireContext(),false)
                    if(response.body()!!.status.equals("Success")){
                        Toast.makeText(requireContext(),"success",Toast.LENGTH_SHORT).show()
                        binding.rvCart.adapter = CartAdapter(response.body()!!.list)
                    }else{
                        Toast.makeText(requireContext(),"error",Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(
                    call: Call<CartListModel?>,
                    t: Throwable
                ) {
                    CustomProgressDialog.showDialog(requireContext(),false)
                    Toast.makeText(requireContext(),"Getting some troubles",Toast.LENGTH_SHORT).show()
                }
            })   
        }

        /*val imageList = intArrayOf(
            R.drawable.cart_one, R.drawable.cart_two, R.drawable.cart_three
        )

        val prodNameList = listOf(
            "Boys White Animal Print Tee, Jacket and Pant Set",
            "Girls Blue Floral Print Casual Dress",
            "Boys Red Polka Dot Print Shirt And Pant Set With B.."
        )

        var sizeList = listOf("2-3", "9-12", "2-3")

        var costPriceList = listOf("2089", "929", "1479")
        var sellingPriceList = listOf("819", "599", "629")
        var discountList = listOf("61", "36", "57")


        for (i in 0 until prodNameList.size) {
            modelList.add(
                CartModel(
                    imageList[i],
                    prodNameList[i],
                    sizeList[i],
                    costPriceList[i],
                    sellingPriceList[i],
                    discountList[i]
                )
            )
        }

        Log.d("MODELLIST_RES",modelList.toString())
        binding.rvCart.adapter = CartAdapter(modelList)*/
    }

    private fun setLayout() {
        binding.rvCart.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
    }

    override fun onClick(view: View?) {
     when(view?.id){
         R.id.llMenu -> activity?.onBackPressed()
         R.id.llCoupon -> view.findNavController().navigate(R.id.nav_cart_to_offer)
         R.id.tvCheckout -> view.findNavController().navigate(R.id.nav_cart_to_shipping_address)
     }
    }//nav_cart_to_offer

    interface onDataRecived {
        fun onCallBack(pos: String?)
    }
}