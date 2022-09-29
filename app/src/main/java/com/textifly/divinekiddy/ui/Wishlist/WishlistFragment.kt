package com.textifly.divinekiddy.ui.Wishlist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.textifly.divinekiddy.ApiManager.RetrofitHelper
import com.textifly.divinekiddy.CustomDialog.CustomProgressDialog
import com.textifly.divinekiddy.MainActivity
import com.textifly.divinekiddy.R
import com.textifly.divinekiddy.Utils.WebService
import com.textifly.divinekiddy.databinding.FragmentWishlistBinding
import com.textifly.divinekiddy.ui.Cart.Model.CartCountModel
import com.textifly.divinekiddy.ui.Wishlist.Adapter.WishlistAdapter
import com.textifly.divinekiddy.ui.Wishlist.Model.WishlistModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WishlistFragment : Fragment(),View.OnClickListener {
    private var _binding : FragmentWishlistBinding? = null
    private val binding get() = _binding!!
    lateinit var modelList : ArrayList<WishlistModel>
    
    val retrofit = RetrofitHelper.getRetrofitInstance()
    val retrofitApiInterface = retrofit.create(WebService::class.java)

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
        _binding = FragmentWishlistBinding.inflate(inflater,container,false)
        super.onResume()
        try {
            val bottomNav : BottomNavigationView?  = activity?.findViewById(R.id.bottom_nav)
            bottomNav?.visibility = View.GONE
        }catch (e:Exception){}
        btnClick()
        setLayout()
        wishlistCount()
        cartCount()
        loadWishlist()
        return binding.root
    }

    private fun setLayout() {
        binding.rvWishlist.layoutManager = GridLayoutManager(activity,2)
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

            retrofitApiInterface.wishlistCount("",device_id).enqueue(object : Callback<CartCountModel?> {
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
                        binding.tvcartBadge.visibility = View.GONE
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
                        binding.tvcartBadge.visibility = View.GONE
                    }
                }

                override fun onFailure(call: Call<CartCountModel?>, t: Throwable) {

                }
            })
        }
    }

    private fun loadWishlist() {
        activity?.let { CustomProgressDialog.showDialog(it, true) }
        modelList = ArrayList()
        val sharedPreference =
            requireActivity().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
        if (sharedPreference.contains("uid")) {
            val uid = sharedPreference.getString("uid", "")
            //Toast.makeText(requireContext(), "uid=>$uid", Toast.LENGTH_SHORT).show()
            retrofitApiInterface.getWishlistList(uid, "").enqueue(object : Callback<WishlistModel?> {
                override fun onResponse(
                    call: Call<WishlistModel?>,
                    response: Response<WishlistModel?>
                ) {
                    CustomProgressDialog.showDialog(requireContext(), false)
                    Log.d("Cart_list", response.body()!!.toString())
                    if (response.body()!!.status.equals("Success")) {
                        //Toast.makeText(requireContext(),"success",Toast.LENGTH_SHORT).show()
                        val wishlistAdapter = WishlistAdapter(response.body()!!.list)
                        binding.rvWishlist.adapter = wishlistAdapter
                        wishlistAdapter.setListner(object : WishlistFragment.onDataRecived {
                            override fun onCallBack(pos: String?) {
                                loadWishlist()
                                cartCount()
                            }
                        })


                    } else {
                        //Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
                        binding.rvWishlist.visibility = View.GONE
                        binding.rlNoRecordsFound.visibility = View.VISIBLE
                    }
                }

                override fun onFailure(
                    call: Call<WishlistModel?>,
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
            retrofitApiInterface.getWishlistList("", device_id).enqueue(object : Callback<WishlistModel?> {
                override fun onResponse(
                    call: Call<WishlistModel?>,
                    response: Response<WishlistModel?>
                ) {
                    Log.e("CARTLIST_RES_Device_id", response.body().toString())
                    CustomProgressDialog.showDialog(requireContext(), false)
                    if (response.body()!!.status.equals("Success")) {

                        val wishlistAdapter = WishlistAdapter(response.body()!!.list)
                        binding.rvWishlist.adapter = wishlistAdapter
                        wishlistAdapter.setListner(object : WishlistFragment.onDataRecived {
                            override fun onCallBack(pos: String?) {
                                loadWishlist()
                                cartCount()
                                wishlistCount()
                            }
                        })

                    } else {
                        //Toast.makeText(requireContext(),"error",Toast.LENGTH_SHORT).show()
                        binding.rvWishlist.visibility = View.GONE
                        binding.rlNoRecordsFound.visibility = View.VISIBLE
                    }
                }

                override fun onFailure(
                    call: Call<WishlistModel?>,
                    t: Throwable
                ) {
                    CustomProgressDialog.showDialog(requireContext(), false)
                    Toast.makeText(requireContext(), "Getting some troubles", Toast.LENGTH_SHORT)
                        .show()
                }
            })
        }
    }

    private fun btnClick() {
        binding.llMenu.setOnClickListener(this)
        binding.tvContinueShopping.setOnClickListener(this)
        binding.llCart.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.llMenu -> activity?.onBackPressed()
            R.id.tvContinueShopping -> startActivity(
                Intent(requireContext(),
                    MainActivity::class.java)
            )
            R.id.llCart -> view.findNavController().navigate(R.id.navigation_wishlist_to_cart)
        }
    }

    interface onDataRecived {
        fun onCallBack(pos: String?)
    }

}