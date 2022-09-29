package com.textifly.divinekiddy.ui.Discover

import android.content.Context
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.textifly.divinekiddy.R
import com.textifly.divinekiddy.Utils.WebService
import com.textifly.divinekiddy.databinding.FragmentDiscoverBinding
import com.textifly.divinekiddy.ApiManager.RetrofitHelper
import com.textifly.divinekiddy.CustomDialog.CustomProgressDialog
import com.textifly.divinekiddy.ui.Cart.Model.CartCountModel
import com.textifly.divinekiddy.ui.Discover.Adapter.HeaderImageAdapter
import com.textifly.divinekiddy.ui.Discover.Adapter.SliderAdapter
import com.textifly.divinekiddy.ui.Discover.Model.HeaderImageModel
//import com.textifly.divinekiddy.ui.Discover.Model.ImageList
import com.textifly.divinekiddy.ui.Discover.Model.SliderModel
import com.textifly.divinekiddy.ui.ProductDetails.Model.CartModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DiscoverFragment : Fragment(),View.OnClickListener {
    private var _binding: FragmentDiscoverBinding? = null
    private val binding get() = _binding!!
    lateinit var modelList: ArrayList<HeaderImageModel>
    lateinit var sliderImageList: ArrayList<String>
    lateinit var imageUrl: ArrayList<String>
    lateinit var sliderAdapter: SliderAdapter

    private val retrofitHelper = RetrofitHelper.getRetrofitInstance()
    private val retrofitApiInterface = retrofitHelper.create(WebService::class.java)

    override fun onResume() {
        super.onResume()
        try {
            val bottomNav: BottomNavigationView? = activity?.findViewById(R.id.bottom_nav)
            bottomNav?.visibility = View.VISIBLE
        } catch (e: Exception) {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDiscoverBinding.inflate(inflater, container, false)
        try {
            val bottomNav: BottomNavigationView? = activity?.findViewById(R.id.bottom_nav)
            bottomNav?.visibility = View.VISIBLE
        } catch (e: Exception) {

        }
        /*val binding = DataBindingUtil.inflate<FragmentDiscoverBinding>(inflater,
            R.layout.fragment_discover,container,false)*/
        setLayout()
        btnClick()
        cartCount()
        wishlistCount()
        loadHeader()
        loadSlider()
        cartTransfer()

        //Toast.makeText(activity,"Hi",Toast.LENGTH_LONG).show()
        return binding.root
    }

    private fun cartTransfer() {
        CustomProgressDialog.showDialog(requireContext(),true)
        val sharedPreference =  requireActivity().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
        val uid : String?
        if(sharedPreference.contains("uid")) {
            uid = sharedPreference.getString("uid", "")
            val device_id: String = Settings.Secure.getString(requireActivity().contentResolver,
                Settings.Secure.ANDROID_ID)

            retrofitApiInterface.cartTransfer(uid,device_id).enqueue(object : Callback<CartModel?> {
                override fun onResponse(call: Call<CartModel?>, response: Response<CartModel?>) {
                    CustomProgressDialog.showDialog(requireContext(),false)
                    if(response.body()!!.status.equals("success")){
                        cartCount()
                        wishlistCount()
                    }
                }

                override fun onFailure(call: Call<CartModel?>, t: Throwable) {
                    CustomProgressDialog.showDialog(requireContext(),false)
                }
            })
        }else{
            CustomProgressDialog.showDialog(requireContext(),false)
            Toast.makeText(requireContext(),"Please Login",Toast.LENGTH_SHORT).show()
        }
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

    private fun btnClick() {
        binding.llCart.setOnClickListener(this)
        binding.llWishlist.setOnClickListener(this)
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
                        Log.d("CART_COUNT_Error_discover","Error")
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

    private fun loadSlider() {
        sliderImageList = ArrayList()
        imageUrl = ArrayList()


        //val response = apiInterface.getBanner()
        val response: Call<SliderModel?> = retrofitApiInterface.getBanner()

        //Log.d("RES_CALL",response.toString())

        //Ctrl+Shift+Space
        retrofitApiInterface.getBanner().enqueue(object : Callback<SliderModel?> {
            override fun onResponse(
                call: Call<SliderModel?>,
                response: Response<SliderModel?>
            ) {
                val responseBody = response.body()!!
                Log.d("MYDATA_RES", responseBody.list?.size.toString())
                //sliderImageList = responseBody

                if(responseBody.status.equals("success")){
                    for (i in 0 until responseBody.list!!.size) {
                        //sliderImageList.add(ImageList(responseBody))
                        //imageUrl = responseBody.
                        imageUrl.add(responseBody.list!![i].image.toString())

                    }
                    sliderAdapter = SliderAdapter("https://divinekiddy.com/uploads/banner/",imageUrl)
                    binding.slider.setSliderAdapter(sliderAdapter)
                    binding.slider.scrollTimeInSec = 3
                    binding.slider.isAutoCycle = true

                    // on below line we are calling start
                    // auto cycle to start our cycle.
                    binding.slider.startAutoCycle()
                    binding.slider.setIndicatorVisibility(false)
                    //SliderAdapter(sliderImageList)
                    //binding.slider.setImageList(sliderImageList, ScaleTypes.FIT)

                    /*for (myData in responseBody){
                        Log.d("MYDATA_RES", myData.toString())

                        myStringBuilder.append("DATA_RAW => ")
                        myStringBuilder.append(myData.sliderImage)
                        //myStringBuilder.append("\n")
                    }*/
                }
            }

            override fun onFailure(call: Call<SliderModel?>, t: Throwable) {
                Log.d("ERROR", t.message.toString())
            }
        })
        //Log.d("RESPONSE", response.toString())


        /*val sliderImages =
            intArrayOf(R.drawable.always_on_trend)*/


    }

    private fun setLayout() {
        //binding.rvHeaderImage.layoutManager = GridLayoutManager(activity,2)
        binding.rvHeaderImage.layoutManager =
            LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
    }

    private fun loadHeader() {
        modelList = ArrayList()

        retrofitApiInterface.getAllCategory().enqueue(object : Callback<HeaderImageModel?> {
            override fun onResponse(
                call: Call<HeaderImageModel?>,
                response: Response<HeaderImageModel?>
            ) {
                when (response.body()?.status) {
                    1 -> {
                        Log.d("HEADER_RES",response.body()!!.List.toString())
                        binding.rvHeaderImage.adapter = HeaderImageAdapter(response.body()!!.List)
                    }
                }
            }

            override fun onFailure(call: Call<HeaderImageModel?>, t: Throwable) {

            }
        });

        val headerImageList =
            intArrayOf(R.drawable.all, R.drawable.boy, R.drawable.girls, R.drawable.newborn)

        val headerImageName = listOf("All", "Girl", "Boy", "Sale")

        Log.d("HeaderImageLength", "" + headerImageList.size)
        for (i in 0 until headerImageList.size) {
            //modelList = listOf(HeaderImageModel(headerImageList[i-1]))
            //modelList.add()
            //modelList.add(HeaderImageModel_old(headerImageList[i], headerImageName[i]))
        }
        //binding.rvHeaderImage.adapter = HeaderImageAdapter(modelList)
        //print("HeaderImageLength ${headerImageList.size}")
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.llCart -> view.findNavController().navigate(R.id.navigation_discover_details_to_cart)
            R.id.llWishlist -> view.findNavController().navigate(R.id.navigation_discover_details_to_wishlist)
        }
    }
}


