package com.textifly.divinekiddy.ui.Products

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.textifly.divinekiddy.R
import com.textifly.divinekiddy.Utils.WebService
import com.textifly.divinekiddy.databinding.FragmentProductsLayoutBinding
import com.textifly.divinekiddy.ApiManager.RetrofitHelper
import com.textifly.divinekiddy.ui.Products.Adapter.ProductsAdapter
import com.textifly.divinekiddy.ui.Products.Model.ProductsModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductsFragment : Fragment(),View.OnClickListener {
    var _binding: FragmentProductsLayoutBinding? = null
    val binding get() = _binding!!
    lateinit var modelList: ArrayList<ProductsModel>

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
        setLayout()
        loadCategory()
        return binding.root
    }

    private fun loadCategory() {
        val category_id = arguments!!.getString("categoryId")
        val subcategory_id = arguments!!.getString("subcategory_id")

        Log.d("ARGUMENTS_RES",category_id+" and "+subcategory_id)

        val retrofit = RetrofitHelper.getRetrofitInstance()
        val  apiInterface = retrofit.create(WebService::class.java)

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
    }

    override fun onClick(view: View?) {
        when (view?.id){
            R.id.llMenu -> activity?.onBackPressed()
        }
    }

}