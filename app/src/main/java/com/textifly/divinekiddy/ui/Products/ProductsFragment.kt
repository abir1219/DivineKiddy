package com.textifly.divinekiddy.ui.Products

import android.content.Context
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.textifly.divinekiddy.R
import com.textifly.divinekiddy.Utils.WebService
import com.textifly.divinekiddy.databinding.FragmentProductsLayoutBinding
import com.textifly.divinekiddy.ApiManager.RetrofitHelper
import com.textifly.divinekiddy.ui.Cart.Model.CartCountModel
import com.textifly.divinekiddy.ui.Products.Adapter.ProductsAdapter
import com.textifly.divinekiddy.ui.Products.Model.ProductsModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductsFragment : Fragment(),View.OnClickListener {
    var _binding: FragmentProductsLayoutBinding? = null
    val binding get() = _binding!!
    lateinit var modelList: ArrayList<ProductsModel>

    val retrofit = RetrofitHelper.getRetrofitInstance()
    val  apiInterface = retrofit.create(WebService::class.java)

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
        _binding = FragmentProductsLayoutBinding.inflate(inflater,container,false)
        try {
            val bottomNav: BottomNavigationView? = activity?.findViewById(R.id.bottom_nav)
            bottomNav?.visibility = View.GONE
        } catch (e: Exception) {

        }
        binding.tvTitle.text = arguments!!.getString("categoryName")
        btnClick()
        cartCount()
        setLayout()
        loadCategory()
        return binding.root
    }

    private fun cartCount() {
        val sharedPreference =  requireActivity().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
//        val editor = sharedPreference.edit()
//        editor.remove("priceId")
//        editor.commit()
        val uid : String?
        if(sharedPreference.contains("uid")) {
            uid = sharedPreference.getString("uid", "")
            apiInterface.cartCount(uid,"").enqueue(object : Callback<CartCountModel?> {
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

            apiInterface.cartCount("",device_id).enqueue(object : Callback<CartCountModel?> {
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

    private fun loadCategory() {
        val category_id = arguments!!.getString("categoryId")
        val subcategory_id = arguments!!.getString("subcategory_id")

        Log.d("ARGUMENTS_RES",category_id+" and "+subcategory_id)

        apiInterface.getAllProducts(category_id,subcategory_id).enqueue(object : Callback<ProductsModel?> {
            override fun onResponse(
                call: Call<ProductsModel?>,
                response: Response<ProductsModel?>
            ) {
                when(response.body()!!.status){
                    1->{
                        binding.rvCategory.adapter = ProductsAdapter(response.body()!!.list)
                    }
                }
            }

            override fun onFailure(call: Call<ProductsModel?>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })

        /*modelList = ArrayList()
        val imageList = intArrayOf(R.drawable.p0,R.drawable.p1,R.drawable.p7,
            R.drawable.p6,R.drawable.p0,R.drawable.p1,R.drawable.p2,R.drawable.p6,R.drawable.p5,R.drawable.p1
            ,R.drawable.p2,R.drawable.p3,R.drawable.p0,R.drawable.p1,R.drawable.p2,R.drawable.p3,R.drawable.p5
            ,R.drawable.p5,R.drawable.p2,R.drawable.p3,R.drawable.p0,R.drawable.p1,R.drawable.p2,R.drawable.p3)

        for (i in 0 until imageList.size){
            modelList.add(ProductsModel(imageList[i]))
        }

        binding.rvCategory.adapter = CategoryAdapter(modelList)*/
    }

    private fun setLayout() {
        binding.rvCategory.layoutManager = GridLayoutManager(activity,2)
    }

    private fun btnClick() {
        binding.llMenu.setOnClickListener(this)
        binding.llCart.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id){
            R.id.llMenu -> activity?.onBackPressed()
            R.id.llCart -> view.findNavController().navigate(R.id.navigation_products_to_cart)

        }
    }

}