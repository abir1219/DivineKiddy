package com.textifly.divinekiddy.ui.SubCategory.Adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.textifly.divinekiddy.R
import com.textifly.divinekiddy.ui.SubCategory.Model.SubCatList

class SubCategoryAdapter(var modelList: List<SubCatList>) :
    RecyclerView.Adapter<SubCategoryAdapter.ViewHolder>() {

    lateinit var context: Context

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cvSubCat: CardView = itemView.findViewById(R.id.cvSubCat)
        val ivImage: ImageView = itemView.findViewById(R.id.ivImage)
        val tvSubCatName: TextView = itemView.findViewById(R.id.tvSubCatName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.subcategory_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context)
            .load("https://divinekiddy.com/uploads/subcategory/${modelList[position].image}")
            .into(holder.ivImage)

        holder.tvSubCatName.text = modelList[position].name

        holder.itemView.setOnClickListener{view ->
            val bundle = Bundle()
            bundle.putString("categoryId", modelList[position].category_id)
            bundle.putString("subcategory_id", modelList[position].id)
            bundle.putString("categoryName", modelList[position].name)
            view.findNavController()
                .navigate(R.id.nav_discover_to_subcategory_to_products,bundle)
        }

    }

    override fun getItemCount(): Int {
        return modelList.size
    }
}