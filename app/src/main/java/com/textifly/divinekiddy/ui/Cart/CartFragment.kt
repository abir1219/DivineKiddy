package com.textifly.divinekiddy.ui.Cart

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.textifly.divinekiddy.ApiManager.RetrofitHelper
import com.textifly.divinekiddy.CustomDialog.CustomProgressDialog
import com.textifly.divinekiddy.MainActivity
import com.textifly.divinekiddy.R
import com.textifly.divinekiddy.Utils.WebService
import com.textifly.divinekiddy.databinding.FragmentCartBinding
import com.textifly.divinekiddy.ui.Cart.Adapter.CartAdapter
import com.textifly.divinekiddy.ui.Cart.Adapter.CartAddressListAdapter
import com.textifly.divinekiddy.ui.Cart.Model.CartCountModel
import com.textifly.divinekiddy.ui.Cart.Model.CartListModel
import com.textifly.divinekiddy.ui.SavedAddress.Model.AddressList
import com.textifly.divinekiddy.ui.SavedAddress.Model.SavedAddressModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartFragment : Fragment(), View.OnClickListener {
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
        cartCount()
        wishlistCount()
        loadCartList()
        val sharedPreference = requireActivity().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
        if(sharedPreference.contains("addressId")){
            binding.llAddressData.visibility = VISIBLE
            binding.llNoAddressData.visibility = GONE
            loadingAddress(sharedPreference)
        }else{
            binding.llAddressData.visibility = GONE
            binding.llNoAddressData.visibility = VISIBLE
        }
        return binding.root
    }

    private fun loadingAddress(sharedPreference: SharedPreferences) {
        retrofitApiInterface.getAddressById(sharedPreference.getString("addressId","")).
                    enqueue(object : Callback<AddressList?> {
            override fun onResponse(call: Call<AddressList?>, response: Response<AddressList?>) {
                binding.tvAddressName.text = response.body()!!.name+","
                binding.tvPin.text = response.body()!!.pin
                binding.tvPin.text = response.body()!!.pin
                binding.tvAddress.text = response.body()!!.address +", "+response.body()!!.city+", "+response.body()!!.state
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
                        binding.tvWishlistBadge.visibility = GONE
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

            retrofitApiInterface.wishlistCount("",device_id).enqueue(object : Callback<CartCountModel?> {
                override fun onResponse(
                    call: Call<CartCountModel?>,
                    response: Response<CartCountModel?>
                ) {
                    if(response.body()!!.status.equals("success")){
                        binding.tvWishlistBadge.visibility = View.VISIBLE
                        binding.tvWishlistBadge.text = response.body()!!.count.toString()
                    }else if(response.body()!!.status.equals("error")){
                        binding.tvWishlistBadge.visibility = GONE
                    }
                }

                override fun onFailure(call: Call<CartCountModel?>, t: Throwable) {

                }
            })
        }
    }

    private fun cartCount() {
        val sharedPreference =  requireActivity().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
//        val editor = sharedPreference.edit()
//        editor.remove("priceId")
//        editor.commit()
        val uid : String?
        if(sharedPreference.contains("uid")) {
            uid = sharedPreference.getString("uid", "")
            retrofitApiInterface.cartCount(uid,"").enqueue(object : Callback<CartCountModel?> {
                override fun onResponse(
                    call: Call<CartCountModel?>,
                    response: Response<CartCountModel?>
                ) {
                    Log.d("CART_COUNT_RES",response.body()!!.toString())
                    if(response.body()!!.status.equals("success")){
                        binding.tvcartBadge.visibility = View.VISIBLE
                        binding.tvcartBadge.text = response.body()!!.count.toString()
                    }else if(response.equals("error")){
                        binding.tvcartBadge.visibility = GONE
                    }
                }

                override fun onFailure(call: Call<CartCountModel?>, t: Throwable) {
                    Log.d("CART_COUNT_Error","Error")
                }
            })
        }else{
            val device_id: String = Settings.Secure.getString(requireActivity().contentResolver,
                Settings.Secure.ANDROID_ID)

            retrofitApiInterface.cartCount("",device_id).enqueue(object : Callback<CartCountModel?> {
                override fun onResponse(
                    call: Call<CartCountModel?>,
                    response: Response<CartCountModel?>
                ) {
                    if(response.body()!!.status.equals("success")){
                        binding.tvcartBadge.visibility = View.VISIBLE
                        binding.tvcartBadge.text = response.body()!!.count.toString()
                    }else if(response.body()!!.status.equals("error")){
                        binding.tvcartBadge.visibility = GONE
                    }
                }

                override fun onFailure(call: Call<CartCountModel?>, t: Throwable) {

                }
            })
        }
    }

    private fun btnClick() {
        binding.llMenu.setOnClickListener(this)
        binding.tvCheckout.setOnClickListener(this)
        binding.llCoupon.setOnClickListener(this)
        binding.tvContinueShopping.setOnClickListener(this)
        binding.llWishlist.setOnClickListener(this)
        binding.tvChangeAddress.setOnClickListener(this)
    }

    private fun loadCartList() {
        activity?.let { CustomProgressDialog.showDialog(it, true) }
        modelList = ArrayList()
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
                        binding.tvDiscount.text = "-₹"+response.body()!!.total_discount
                        val cartAdapter = CartAdapter(response.body()!!.list)
                        binding.rvCart.adapter = cartAdapter
                        cartAdapter.setListner(object : onDataRecived {
                            override fun onCallBack(pos: String?) {
                                loadCartList()
                                cartCount()
                                wishlistCount()
                            }
                        })


                    } else {
                        //Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
                        binding.rlRecordsFound.visibility = GONE
                        binding.rlNoRecordsFound.visibility = VISIBLE
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
                        binding.tvDiscount.text = "-₹"+response.body()!!.total_discount
                        //Toast.makeText(requireContext(), "success", Toast.LENGTH_SHORT).show()
                        //binding.rvCart.adapter = CartAdapter(response.body()!!.list)

                        val cartAdapter = CartAdapter(response.body()!!.list)
                        binding.rvCart.adapter = cartAdapter
                        cartAdapter.setListner(object : onDataRecived {
                            override fun onCallBack(pos: String?) {
                                loadCartList()
                                cartCount()
                                wishlistCount()
                            }
                        })

                    } else {
                        //Toast.makeText(requireContext(),"error",Toast.LENGTH_SHORT).show()
                        binding.rlRecordsFound.visibility = GONE
                        binding.rlNoRecordsFound.visibility = VISIBLE
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

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.llMenu -> activity?.onBackPressed()
            R.id.llCoupon -> view.findNavController().navigate(R.id.nav_cart_to_offer)
            R.id.tvCheckout -> view.findNavController().navigate(R.id.nav_cart_to_shipping_address)
            R.id.tvContinueShopping -> startActivity(Intent(requireContext(),MainActivity::class.java))
            R.id.llWishlist -> view.findNavController()
                .navigate(R.id.navigation_cart_to_wishlist)

            R.id.tvChangeAddress -> {
                var bottomSheetDialog: BottomSheetDialog? = null
                bottomSheetDialog = BottomSheetDialog(requireContext())
                bottomSheetDialog.setContentView(R.layout.select_addresslist_layout)
                bottomSheetDialog.setCanceledOnTouchOutside(true)

                val rvAddressList: RecyclerView? = bottomSheetDialog.findViewById(R.id.rvAddressList)
                val llcancel: LinearLayout? = bottomSheetDialog.findViewById(R.id.llcancel)

                val rlNoRecordsFound: RelativeLayout? = bottomSheetDialog.findViewById(R.id.rlNoRecordsFound)

                llcancel!!.setOnClickListener(View.OnClickListener { bottomSheetDialog.dismiss() })

                rvAddressList!!.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

                loadAddressList(rvAddressList,rlNoRecordsFound)

                bottomSheetDialog.show()
            }
        }
    }//nav_cart_to_offer

    private fun loadAddressList(rvAddressList: RecyclerView, rlNoRecordsFound: RelativeLayout?) {
        CustomProgressDialog.showDialog(requireContext(), true)
        val sharedPreference = requireContext().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
        val uid = sharedPreference.getString("uid", "")
        retrofitApiInterface.getAddressList(uid).enqueue(object : Callback<SavedAddressModel?> {
            override fun onResponse(
                call: Call<SavedAddressModel?>,
                response: Response<SavedAddressModel?>
            ) {
                Log.d("Addresslist_RES",response.body()!!.toString())
                CustomProgressDialog.showDialog(requireContext(), false)
                if(response.body()!!.status.equals("success",ignoreCase = true)){
                    rvAddressList.visibility = View.VISIBLE
                    rlNoRecordsFound?.visibility = GONE
                    val cartAddressAdapter = CartAddressListAdapter(response.body()!!.addressList)
                    rvAddressList.adapter = cartAddressAdapter

                    /*savedAddressAdapter.setListner(object : AddressListFragment.onDataRecived {
                        override fun onCallBack(pos: String?) {
                            loadAddressList(rvAddressList, rlNoRecordsFound)
                        }
                    })*/
                }else{
                    rvAddressList.visibility = GONE
                    binding.rlNoRecordsFound.visibility = VISIBLE
                }
            }

            override fun onFailure(call: Call<SavedAddressModel?>, t: Throwable) {
                CustomProgressDialog.showDialog(requireContext(), false)
            }
        })
    }

    interface onDataRecived {
        fun onCallBack(pos: String?)
    }
}