package com.textifly.divinekiddy.ui.SubCategory

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.textifly.divinekiddy.R
import com.textifly.divinekiddy.Utils.WebService
import com.textifly.divinekiddy.databinding.FragmentSubCategoryBinding
import com.textifly.divinekiddy.ApiManager.RetrofitHelper
import com.textifly.divinekiddy.ui.SubCategory.Adapter.SubCategoryAdapter
import com.textifly.divinekiddy.ui.SubCategory.Model.SubCategoryModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SubCategoryFragment : Fragment(),View.OnClickListener {
    private var _binding: FragmentSubCategoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSubCategoryBinding.inflate(inflater, container, false)
        btnClick()
        setLayout()
        loadSubCategory()
        return binding.root
    }

    private fun btnClick() {
        binding.llMenu.setOnClickListener(this
        )
    }

    private fun loadSubCategory() {
        val categoryId = arguments!!.getString("categoryId")
        Log.d("CATEGORY_ID",categoryId!!)
        val retrofit = RetrofitHelper.getRetrofitInstance()
        val apiInterface = retrofit.create(WebService::class.java)

        apiInterface.getAllSubCategory(categoryId).enqueue(object : Callback<SubCategoryModel?> {
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
        }
    }

}