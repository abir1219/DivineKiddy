package com.textifly.divinekiddy.ui.Categories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.textifly.divinekiddy.R
import com.textifly.divinekiddy.databinding.FragmentAllCategoriesBinding
import com.textifly.divinekiddy.databinding.FragmentDiscoverBinding
import com.textifly.divinekiddy.ui.Categories.Adapter.CategoryAdapter
import com.textifly.divinekiddy.ui.Categories.Model.CategoryModel

class AllCategoriesFragment : Fragment() {
    private var _binding: FragmentAllCategoriesBinding? = null
    private val binding get() = _binding!!
    lateinit var modelList: ArrayList<CategoryModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAllCategoriesBinding.inflate(inflater, container, false)
        setLayout()
        loadCategory()
        return binding.root
    }

    private fun setLayout() {
        binding.rvCategories.layoutManager =
            LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
    }

    private fun loadCategory() {
        modelList = ArrayList()
        val imageTitleList = listOf(
            "Baby Girl Fashion",
            "Baby Boy Fashion",
            "Newborn Fashion",
            "Western Collection",
            "Birthday Box",
            "Toy"
        )

        val ageList = listOf("(0-2yrs)", "(0-2yrs)", "", "", "", "")

        val imageList = intArrayOf(
            R.drawable.c_girl, R.drawable.c_boys, R.drawable.c_newborn,
            R.drawable.c_western, R.drawable.c_birthday_box, R.drawable.c_toys,
        )

        for (i in 0 until imageList.size) {
            modelList.add(CategoryModel(imageTitleList[i], ageList[i], imageList[i]))
        }

        binding.rvCategories.adapter = CategoryAdapter(modelList)
    }

}