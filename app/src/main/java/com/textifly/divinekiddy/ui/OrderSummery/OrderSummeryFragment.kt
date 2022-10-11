package com.textifly.divinekiddy.ui.OrderSummery

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
import com.textifly.divinekiddy.databinding.FragmentOrderSummeryBinding
import com.textifly.divinekiddy.ui.Cart.Adapter.CartAdapter
import com.textifly.divinekiddy.ui.Cart.Adapter.OrderSummeryAdapter
import com.textifly.divinekiddy.ui.Cart.CartFragment
import com.textifly.divinekiddy.ui.Cart.Model.CartCountModel
import com.textifly.divinekiddy.ui.Cart.Model.CartListModel
import com.textifly.divinekiddy.ui.SavedAddress.Model.AddressList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderSummeryFragment : Fragment(),View.OnClickListener {
    private var _binding : FragmentOrderSummeryBinding? = null
    private val binding get() = _binding!!

    private var retrofitHelper = RetrofitHelper.getRetrofitInstance()
    private var retrofitApiInterface = retrofitHelper.create(WebService::class.java)
    val bundle = Bundle()

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
        // Inflate the layout for this fragment
        _binding = FragmentOrderSummeryBinding.inflate(inflater,container,false)
        try {
            val bottomNav: BottomNavigationView? = activity?.findViewById(R.id.bottom_nav)
            bottomNav?.visibility = View.GONE
        } catch (e: Exception) {

        }
        btnClick()
        setLayout()
        wishlistCount()
        loadCartList()
        val sharedPreference = requireActivity().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
        loadingAddress(sharedPreference)
        return binding.root
    }

    private fun loadingAddress(sharedPreference: SharedPreferences) {
        //Toast.makeText(requireContext(),"${sharedPreference.getString("addressId","")}",Toast.LENGTH_SHORT).show()
        retrofitApiInterface.getAddressById(sharedPreference.getString("addressId","")).
        enqueue(object : Callback<AddressList?> {
            override fun onResponse(call: Call<AddressList?>, response: Response<AddressList?>) {
                Log.d("Address_res",response.body()!!.toString())
                binding.tvName.text = response.body()!!.name
                binding.tvPhone.text ="+91"+ response.body()!!.mobile
                binding.tvAddress.text = response.body()!!.address +", "+response.body()!!.city+", "+response.body()!!.state+" -"+response.body()!!.pin
            }

            override fun onFailure(call: Call<AddressList?>, t: Throwable) {
                Toast.makeText(requireActivity(),"Getting some troubles",Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun wishlistCount() {
        val sharedPreference =  requireActivity().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
//        val editor = sharedPreference.edit()
//        editor.remove("priceId")
//        editor.commit()
        val uid : String?
        if(sharedPreference.contains("uid")) {
            uid = sharedPreference.getString("uid", "")
            retrofitApiInterface.wishlistCount(uid,"").enqueue(object : Callback<CartCountModel?> {
                override fun onResponse(
                    call: Call<CartCountModel?>,
                    response: Response<CartCountModel?>
                ) {
                    Log.d("CART_COUNT_RES",response.body()!!.toString())
                    if(response.body()!!.status.equals("success")){
                        binding.tvWishlistBadge.visibility = View.VISIBLE
                        binding.tvWishlistBadge.text = response.body()!!.count.toString()


                    }else if(response.equals("error")){
                        binding.tvWishlistBadge.visibility = View.GONE
                    }
                }

                override fun onFailure(call: Call<CartCountModel?>, t: Throwable) {
                    Log.d("CART_COUNT_Error","Error")
                }
            })
        }else{
            val device_id: String = Settings.Secure.getString(requireActivity().contentResolver,
                Settings.Secure.ANDROID_ID)

            Log.d("Divice_id_cart_count",device_id)

            retrofitApiInterface.wishlistCount("",device_id).enqueue(object :
                Callback<CartCountModel?> {
                override fun onResponse(
                    call: Call<CartCountModel?>,
                    response: Response<CartCountModel?>
                ) {
                    if(response.body()!!.status.equals("success")){
                        binding.tvWishlistBadge.visibility = View.VISIBLE
                        binding.tvWishlistBadge.text = response.body()!!.count.toString()
                    }else if(response.body()!!.status.equals("error")){
                        binding.tvWishlistBadge.visibility = View.GONE
                    }
                }

                override fun onFailure(call: Call<CartCountModel?>, t: Throwable) {

                }
            })
        }
    }

    private fun loadCartList() {
        activity?.let { CustomProgressDialog.showDialog(it, true) }
        val sharedPreference =
            requireActivity().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
        if (sharedPreference.contains("uid")) {
            val uid = sharedPreference.getString("uid", "")
            //Toast.makeText(requireContext(), "uid=>$uid", Toast.LENGTH_SHORT).show()
            retrofitApiInterface.cartList(uid, "").enqueue(object : Callback<CartListModel?> {
                override fun onResponse(
                    call: Call<CartListModel?>,
                    response: Response<CartListModel?>
                ) {
                    CustomProgressDialog.showDialog(requireContext(), false)
                    Log.d("Cart_list", response.body()!!.toString())
                    if (response.body()!!.status.equals("Success")) {
                        //Toast.makeText(requireContext(),"success",Toast.LENGTH_SHORT).show()
                        binding.tvTotalItemPrice.text = "₹"+response.body()!!.total_price
                        binding.tvTotalPrice.text = "₹"+response.body()!!.total_price
                        binding.tvDiscount.text = "-₹"+response.body()!!.total_discount

                        bundle.putString("totalPrice",response.body()!!.total_price)
                        bundle.putString("discountPrice",response.body()!!.total_discount)

                        val orderSummeryAdapter = OrderSummeryAdapter(response.body()!!.list)
                        binding.rvCart.adapter = orderSummeryAdapter

                        orderSummeryAdapter.setListner(object : onDataRecived {
                            override fun onCallBack(pos: String?) {
                                loadCartList()
                                wishlistCount()
                            }
                        })

                    }
                }

                override fun onFailure(
                    call: Call<CartListModel?>,
                    t: Throwable
                ) {
                    CustomProgressDialog.showDialog(requireContext(), false)
                    Toast.makeText(requireContext(), "Fail", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            val device_id: String = Settings.Secure.getString(
                requireActivity().contentResolver,
                Settings.Secure.ANDROID_ID
            )
            Log.d("Device_Id", device_id)
            retrofitApiInterface.cartList("", device_id).enqueue(object : Callback<CartListModel?> {
                override fun onResponse(
                    call: Call<CartListModel?>,
                    response: Response<CartListModel?>
                ) {
                    Log.e("CARTLIST_RES_Device_id", response.body().toString())
                    CustomProgressDialog.showDialog(requireContext(), false)
                    if (response.body()!!.status.equals("Success")) {
                        binding.tvTotalItemPrice.text = "₹"+response.body()!!.total_price
                        binding.tvTotalPrice.text = "₹"+response.body()!!.total_price
                        binding.tvDiscount.text = "-₹"+response.body()!!.total_discount
                        //Toast.makeText(requireContext(), "success", Toast.LENGTH_SHORT).show()
                        //binding.rvCart.adapter = CartAdapter(response.body()!!.list)
                        bundle.putString("totalPrice",response.body()!!.total_price)
                        bundle.putString("discountPrice",response.body()!!.total_discount)

                        val orderSummeryAdapter = OrderSummeryAdapter(response.body()!!.list)
                        binding.rvCart.adapter = orderSummeryAdapter

                        orderSummeryAdapter.setListner(object : onDataRecived {
                            override fun onCallBack(pos: String?) {
                                loadCartList()
                                wishlistCount()
                            }
                        })

                    }
                }

                override fun onFailure(
                    call: Call<CartListModel?>,
                    t: Throwable
                ) {
                    CustomProgressDialog.showDialog(requireContext(), false)
                    Toast.makeText(requireContext(), "Getting some troubles", Toast.LENGTH_SHORT)
                        .show()
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

    private fun btnClick() {
        binding.llMenu.setOnClickListener(this)
        binding.tvContinue.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.llMenu -> activity?.onBackPressed()
            R.id.tvContinue -> {
                checkOut()
            }

        }
    }

    private fun checkOut() {
        val prefs = requireActivity().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
        if(prefs.contains("addressId")){

            view?.findNavController()?.navigate(R.id.navigation_order_summery_to_payment,bundle)
            /*  CustomProgressDialog.showDialog(requireContext(),true)
              retrofitApiInterface.placeOrder(prefs.getString("uid",""),prefs.getString("addressId","")).enqueue(object : Callback<CartModel?> {
                  override fun onResponse(call: Call<CartModel?>, response: Response<CartModel?>) {
                      CustomProgressDialog.showDialog(requireContext(),false)
                      if(response.body()!!.status.equals("Success", ignoreCase = true)){
                          Toast.makeText(requireContext(),"Order Placed Successfully",Toast.LENGTH_SHORT).show()
                          view?.findNavController()?.navigate(R.id.nav_cart_to_shipping_address)
                      }else{
                          Toast.makeText(requireContext(),"Order Placed un-successful",Toast.LENGTH_SHORT).show()
                      }
                  }

                  override fun onFailure(call: Call<CartModel?>, t: Throwable) {
                      CustomProgressDialog.showDialog(requireContext(),false)
                      Toast.makeText(requireContext(),"Getting some troubles",Toast.LENGTH_SHORT).show()
                  }
              })*/
        }else{
            Toast.makeText(requireContext(), "Please select Address", Toast.LENGTH_SHORT).show()
        }
    }

    interface onDataRecived {
        fun onCallBack(pos: String?)
    }
}