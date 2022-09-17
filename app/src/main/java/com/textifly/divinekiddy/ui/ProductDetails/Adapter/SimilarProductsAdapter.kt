package com.textifly.divinekiddy.ui.ProductDetails.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.textifly.divinekiddy.R
import com.textifly.divinekiddy.ui.ProductDetails.Model.SimilarProductsModel

class SimilarProductsAdapter(val modelList: List<SimilarProductsModel>) :
    RecyclerView.Adapter<SimilarProductsAdapter.ViewHolder>() {

    lateinit var context: Context

    class ViewHolder(val itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivImage : ImageView = itemView.findViewById(R.id.ivImage)
        val tvPrice : TextView = itemView.findViewById(R.id.tvPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.similar_product_layout,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.ivImage.setImageResource(modelList[position].imageUrl)
        holder.tvPrice.text =  "₹"+modelList[position].price
    }

    override fun getItemCount(): Int {
        return modelList.size
    }
}