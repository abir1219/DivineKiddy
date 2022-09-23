package com.textifly.divinekiddy.ui.SubCategory

import android.content.Context
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.textifly.divinekiddy.R
import com.textifly.divinekiddy.Utils.WebService
import com.textifly.divinekiddy.databinding.FragmentSubCategoryBinding
import com.textifly.divinekiddy.ApiManager.RetrofitHelper
import com.textifly.divinekiddy.ui.Cart.Model.CartCountModel
import com.textifly.divinekiddy.ui.SubCategory.Adapter.SubCategoryAdapter
import com.textifly.divinekiddy.ui.SubCategory.Model.SubCategoryModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SubCategoryFragment : Fragment(),View.OnClickListener {
    private var _binding: FragmentSubCategoryBinding? = null
    private val binding get() = _binding!!

    val retrofit = RetrofitHelper.getRetrofitInstance()
    val retrofitApiInterface = retrofit.create(WebService::class.java)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSubCategoryBinding.inflate(inflater, container, false)
        btnClick()
        cartCount()
        setLayout()
        loadSubCategory()
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

    private fun btnClick() {
        binding.llMenu.setOnClickListener(this)
        binding.llCart.setOnClickListener(this)
    }

    private fun loadSubCategory() {
        val categoryId = arguments!!.getString("categoryId")
        Log.d("CATEGORY_ID",categoryId!!)


        retrofitApiInterface.getAllSubCategory(categoryId).enqueue(object : Callback<SubCategoryModel?> {
            override fun onResponse(
                call: Call<SubCategoryModel?>,
                response: Response<SubCategoryModel?>
            ) {
                when (response.body()!!.status) {
                    1 -> {
                        //Log.d("RESPONSE_STATUS",response.body()!!.list.toString())
                        binding.rvSubCategory.adapter = SubCategoryAdapter(response.body()!!.list)
                    }
                }
            }

            override fun onFailure(call: Call<SubCategoryModel?>, t: Throwable) {
                Log.d("ERROR_SUBCAT",t.message.toString())
            }
        })
    }

    private fun setLayout() {
        binding.rvSubCategory.layoutManager =
            LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
    }

    override fun onClick(view: View?) {
        when (view?.id){
            R.id.llMenu -> activity?.onBackPressed()
            R.id.llCart -> view.findNavController().navigate(R.id.navigation_subcategory_to_cart)
        }
    }

}