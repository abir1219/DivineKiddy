package com.textifly.divinekiddy.ui.ProductDetails

import android.content.Context
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.textifly.divinekiddy.CustomDialog.CustomProgressDialog
import com.textifly.divinekiddy.R
import com.textifly.divinekiddy.Utils.WebService
import com.textifly.divinekiddy.databinding.FragmentProductDetailsBinding
import com.textifly.divinekiddy.ApiManager.RetrofitHelper
import com.textifly.divinekiddy.ui.Cart.Model.CartCountModel
import com.textifly.divinekiddy.ui.Discover.Adapter.SliderAdapter
import com.textifly.divinekiddy.ui.ProductDetails.Adapter.AgePriceAdapter
import com.textifly.divinekiddy.ui.ProductDetails.Adapter.SimilarProductsAdapter
import com.textifly.divinekiddy.ui.ProductDetails.Model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductDetailsFragment : Fragment(), View.OnClickListener {
    var _binding: FragmentProductDetailsBinding? = null
    val binding get() = _binding!!
    lateinit var modelList: ArrayList<AgePriceModel>
    lateinit var similarModelList: ArrayList<SimilarProductsModel>

    private val retrofitHelper = RetrofitHelper.getRetrofitInstance()
    private val retrofitApiInterface = retrofitHelper.create(WebService::class.java)

    var prodId : String? = null

    var isProdDetailsClicked: Boolean = false

    lateinit var imageUrl: ArrayList<String>

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
        // Inflate the layout for this fragment
        _binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        try {
            val bottomNav: BottomNavigationView? = activity?.findViewById(R.id.bottom_nav)
            bottomNav?.visibility = View.GONE
        } catch (e: Exception) {

        }
        initView()
        btnClick()
        cartCount()
        wishlistCount()
        loadProduct()
        setLayout()
        return binding.root
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
                    Log.d("Wishlist_COUNT_RES",response.body()!!.toString())
                    if(response.body()!!.status.equals("success")){
                        binding.tvWishlistBadge.visibility = View.VISIBLE
                        binding.tvWishlistBadge.text = response.body()!!.count.toString()

                        binding.ivAddToWishlist.setColorFilter(resources.getColor(R.color.red))
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
                        binding.ivAddToWishlist.setColorFilter(resources.getColor(R.color.red))
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

    private fun initView() {
        val sharedPreference =  requireActivity().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
            val editor = sharedPreference.edit()
            editor.remove("priceId")
            editor.commit()
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

            Log.d("Divice_id_cart_count",device_id)

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

    private fun loadProduct() {
        activity?.let { CustomProgressDialog.showDialog(it,true) }
        prodId = arguments?.getString("prodId")

        if (prodId != null) {
            retrofitApiInterface.productDetails(prodId!!)
                .enqueue(object : Callback<ProductDetailsModel?> {
                    override fun onResponse(
                        call: Call<ProductDetailsModel?>,
                        response: Response<ProductDetailsModel?>
                    ) {

                        activity?.let { CustomProgressDialog.showDialog(it,false) }
                        when (response.body()!!.status) {
                            1 -> {
                                binding.tvSellingPrice.text = "₹" + response.body()!!.special_price
                                binding.tvActualPrice.text = "₹" + response.body()!!.actual_price
                                binding.tvProdName.text = response.body()!!.name
                                binding.tvProdDetails.text = response.body()!!.description

                                loadSimilarProduct(response.body()!!.id,response.body()!!.subcategory_id)
                                
                                loadSlider(response.body()!!.imageList)
                                loadAgePrice(response.body()!!.ageList)
                            }
                        }
                    }

                    override fun onFailure(call: Call<ProductDetailsModel?>, t: Throwable) {
                        activity?.let { CustomProgressDialog.showDialog(it,false) }
                    }
                })
        }

    }

    private fun btnClick() {
        binding.tvAddToCart.setOnClickListener(this)
        binding.ivAddToWishlist.setOnClickListener(this)
        binding.tvBuyNow.setOnClickListener(this)
        binding.llMenu.setOnClickListener(this)
        binding.llCart.setOnClickListener(this)
        binding.rlProdDetails.setOnClickListener(this)
    }

    private fun loadSimilarProduct(prodId: String?, subcategoryId: String?) {
        retrofitApiInterface.getRelatedProducts(prodId,subcategoryId).enqueue(object : Callback<SimilarProductsModel?> {
            override fun onResponse(
                call: Call<SimilarProductsModel?>,
                response: Response<SimilarProductsModel?>
            ) {
                if(response.body()!!.status.equals("success")){
                    binding.rvSimilarProduct.adapter = SimilarProductsAdapter(response.body()!!.list)
                }
            }

            override fun onFailure(call: Call<SimilarProductsModel?>, t: Throwable) {
            }
        })
        /*similarModelList = ArrayList()
        val similartImageList = arrayListOf(
            R.drawable.p0,
            R.drawable.p1,
            R.drawable.p2,
            R.drawable.p3,
            R.drawable.p5,
            R.drawable.p6
        )

        val priceList = listOf("1,351", "679", "831", "1,331", "1,225", "961")

        for (i in 0 until similartImageList.size) {
            similarModelList.add(SimilarProductsModel(similartImageList[i], priceList[i]))
        }
        binding.rvSimilarProduct.adapter = SimilarProductsAdapter(similarModelList)*/

    }

    private fun setLayout() {
        binding.rvSize.layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        binding.rvSimilarProduct.layoutManager =
            LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
    }

    private fun loadAgePrice(ageList: List<AgePriceModel>?) {
        binding.rvSize.adapter = AgePriceAdapter(ageList!!)
    }

    private fun loadSlider(imageList: List<ProductImage>?) {
        imageUrl = ArrayList()
        for (i in 0 until imageList!!.size) {
            //sliderImageList.add(ImageList(responseBody))
            //imageUrl = responseBody.
            imageUrl.add(imageList[i].image.toString())

        }

        val sliderAdapter = SliderAdapter("https://divinekiddy.com/uploads/product/", imageUrl)
        binding.slider.setSliderAdapter(sliderAdapter)
        binding.slider.scrollTimeInSec = 3
        binding.slider.isAutoCycle = true
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.tvAddToCart ->{
                val sharedPreference = context?.getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
                if(sharedPreference!!.contains("priceId")){
                    addToCart(view)
                }else{
                    Toast.makeText(requireActivity(),"Select Price First",Toast.LENGTH_SHORT).show()
                }
            }
            R.id.ivAddToWishlist -> {
                val sharedPreference = context?.getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
                if(sharedPreference!!.contains("priceId")){
                    addToWishlist(view)
                }else{
                    Toast.makeText(requireActivity(),"Select Price First",Toast.LENGTH_SHORT).show()
                }
            }
            R.id.rlProdDetails -> openProdDetails()

            R.id.llCart -> view.findNavController().navigate(R.id.navigation_product_details_to_cart)

            R.id.llMenu -> activity?.onBackPressed()

            R.id.tvBuyNow -> {
                val sharedPreference = context?.getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
                if(sharedPreference!!.contains("priceId")){
                    addAndGoToCart(view)
                }else{
                    Toast.makeText(requireActivity(),"Select Price First",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun addToWishlist(view: View) {
        //activity?.let { CustomProgressDialog.showDialog(it,true) }
        CustomProgressDialog.showDialog(requireContext(),true)
        //Toast.makeText(activity,"Prod Id => $prodId",Toast.LENGTH_LONG).show()
        val sharedPreference =  requireActivity().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
//        val editor = sharedPreference.edit()
//        editor.remove("priceId")
//        editor.commit()
        val uid : String?
        if(sharedPreference.contains("uid")){
            uid = sharedPreference.getString("uid","")
            Toast.makeText(activity,"uid => $uid",Toast.LENGTH_LONG).show()

            retrofitApiInterface.addToWishlist(prodId!!,uid!!,"",sharedPreference.getString("priceId","")!!,
                sharedPreference.getString("price","")!!).enqueue(object : Callback<CartModel?> {

                override fun onResponse(
                    call: Call<CartModel?>,
                    response: Response<CartModel?>
                ) {
                    Log.d("ADD_TO_WISHLIST",response.body()!!.toString())
                    CustomProgressDialog.showDialog(requireContext(),false)
                    if(response.body()!!.status!!.equals("success")){
                        wishlistCount()
                        Toast.makeText(activity,response.body()!!.message,Toast.LENGTH_LONG).show()
                    }else if(response.body()!!.status!!.equals("error")){
                        Toast.makeText(activity,response.body()!!.message,Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<CartModel?>, t: Throwable) {
                    CustomProgressDialog.showDialog(requireContext(),false)
                    Toast.makeText(activity,"Getting some troubles",Toast.LENGTH_LONG).show()
                }
            })
        }else{
            val device_id: String = Settings.Secure.getString(requireActivity().contentResolver,
                Settings.Secure.ANDROID_ID)

            Log.d("Divice_id_res",device_id)

            retrofitApiInterface.addToWishlist(prodId!!,"",device_id,sharedPreference.getString("priceId","")!!,
                sharedPreference.getString("price","")!!).enqueue(object : Callback<CartModel?> {
                override fun onResponse(
                    call: Call<CartModel?>,
                    response: Response<CartModel?>
                ) {
                    CustomProgressDialog.showDialog(requireContext(),false)
                    if(response.body()!!.status!!.equals("success")){
                        wishlistCount()
                        Toast.makeText(activity,response.body()!!.message,Toast.LENGTH_LONG).show()
                    }else if(response.body()!!.status!!.equals("error")){
                        Toast.makeText(activity,response.body()!!.message,Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<CartModel?>, t: Throwable) {
                    CustomProgressDialog.showDialog(requireContext(),false)
                    Toast.makeText(activity,"Getting some troubles",Toast.LENGTH_LONG).show()
                }
            })
        }
    }

    private fun addAndGoToCart(view: View) {
        //activity?.let { CustomProgressDialog.showDialog(it,true) }
        CustomProgressDialog.showDialog(requireContext(),true)
        //Toast.makeText(activity,"Prod Id => $prodId",Toast.LENGTH_LONG).show()
        val sharedPreference =  requireActivity().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
//        val editor = sharedPreference.edit()
//        editor.remove("priceId")
//        editor.commit()
        val uid : String?
        if(sharedPreference.contains("uid")){
            uid = sharedPreference.getString("uid","")
            Toast.makeText(activity,"uid => $uid",Toast.LENGTH_LONG).show()

            retrofitApiInterface.addToCart(prodId!!,uid!!,"",sharedPreference.getString("priceId","")!!,
                sharedPreference.getString("price","")!!,"1").enqueue(object : Callback<CartModel?> {

                override fun onResponse(
                    call: Call<CartModel?>,
                    response: Response<CartModel?>
                ) {
                    CustomProgressDialog.showDialog(requireContext(),false)
                    if(response.body()!!.status!!.equals("Success")){
                        cartCount()
                        Toast.makeText(activity,response.body()!!.message,Toast.LENGTH_LONG).show()
                        view.findNavController()
                            .navigate(R.id.navigation_product_details_to_cart)
                    }else if(response.body()!!.status!!.equals("error")){
                        Toast.makeText(activity,response.body()!!.message,Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<CartModel?>, t: Throwable) {
                    CustomProgressDialog.showDialog(requireContext(),false)
                    Toast.makeText(activity,"Getting some troubles",Toast.LENGTH_LONG).show()
                }
            })
        }else{
            val device_id: String = Settings.Secure.getString(requireActivity().contentResolver,
                Settings.Secure.ANDROID_ID)

            retrofitApiInterface.addToCart(prodId!!,"",device_id,sharedPreference.getString("priceId","")!!,
                sharedPreference.getString("price","")!!,"1").enqueue(object : Callback<CartModel?> {
                override fun onResponse(
                    call: Call<CartModel?>,
                    response: Response<CartModel?>
                ) {
                    CustomProgressDialog.showDialog(requireContext(),false)
                    if(response.body()!!.status!!.equals("Success")){
                        cartCount()
                        Toast.makeText(activity,response.body()!!.message,Toast.LENGTH_LONG).show()
                    }else if(response.body()!!.status!!.equals("error")){
                        Toast.makeText(activity,response.body()!!.message,Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<CartModel?>, t: Throwable) {
                    CustomProgressDialog.showDialog(requireContext(),false)
                    Toast.makeText(activity,"Getting some troubles",Toast.LENGTH_LONG).show()
                }
            })
        }

        /* CustomProgressDialog.showDialog(requireContext(),false)

         view.findNavController()
             .navigate(R.id.navigation_product_details_to_cart)*/
    }

    private fun addToCart(view: View) {
        //activity?.let { CustomProgressDialog.showDialog(it,true) }
        CustomProgressDialog.showDialog(requireContext(),true)
        //Toast.makeText(activity,"Prod Id => $prodId",Toast.LENGTH_LONG).show()
        val sharedPreference =  requireActivity().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
//        val editor = sharedPreference.edit()
//        editor.remove("priceId")
//        editor.commit()
        val uid : String?
        if(sharedPreference.contains("uid")){
            uid = sharedPreference.getString("uid","")
            Toast.makeText(activity,"uid => $uid",Toast.LENGTH_LONG).show()

            retrofitApiInterface.addToCart(prodId!!,uid!!,"",sharedPreference.getString("priceId","")!!,
                sharedPreference.getString("price","")!!,"1").enqueue(object : Callback<CartModel?> {

                override fun onResponse(
                    call: Call<CartModel?>,
                    response: Response<CartModel?>
                ) {
                    CustomProgressDialog.showDialog(requireContext(),false)
                    if(response.body()!!.status!!.equals("Success")){
                        cartCount()
                        Toast.makeText(activity,response.body()!!.message,Toast.LENGTH_LONG).show()
                    }else if(response.body()!!.status!!.equals("error")){
                        Toast.makeText(activity,response.body()!!.message,Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<CartModel?>, t: Throwable) {
                    CustomProgressDialog.showDialog(requireContext(),false)
                    Toast.makeText(activity,"Getting some troubles",Toast.LENGTH_LONG).show()
                }
            })
        }else{
            val device_id: String = Settings.Secure.getString(requireActivity().contentResolver,
                Settings.Secure.ANDROID_ID)

            Log.d("Divice_id_res",device_id)

            retrofitApiInterface.addToCart(prodId!!,"",device_id,sharedPreference.getString("priceId","")!!,
                sharedPreference.getString("price","")!!,"1").enqueue(object : Callback<CartModel?> {
                override fun onResponse(
                    call: Call<CartModel?>,
                    response: Response<CartModel?>
                ) {
                    CustomProgressDialog.showDialog(requireContext(),false)
                    if(response.body()!!.status!!.equals("Success")){
                        cartCount()
                        Toast.makeText(activity,response.body()!!.message,Toast.LENGTH_LONG).show()
                    }else if(response.body()!!.status!!.equals("error")){
                        Toast.makeText(activity,response.body()!!.message,Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<CartModel?>, t: Throwable) {
                    CustomProgressDialog.showDialog(requireContext(),false)
                    Toast.makeText(activity,"Getting some troubles",Toast.LENGTH_LONG).show()
                }
            })
        }

       /* CustomProgressDialog.showDialog(requireContext(),false)

        view.findNavController()
            .navigate(R.id.navigation_product_details_to_cart)*/
    }

    private fun openProdDetails() {
        if(isProdDetailsClicked){
            binding.ivProdDetails.startAnimation(
                AnimationUtils.loadAnimation(
                    activity,
                    R.anim.rotate_anticlockwise_right_angle
                )
            )
            isProdDetailsClicked = false
            binding.tvProdDetails.visibility = View.VISIBLE
        }else{
            binding.ivProdDetails.startAnimation(
                AnimationUtils.loadAnimation(
                    activity,
                    R.anim.rotate_clockwise_right_angle
                )
            )
            isProdDetailsClicked = true
            binding.tvProdDetails.visibility = View.GONE
        }
    }
}