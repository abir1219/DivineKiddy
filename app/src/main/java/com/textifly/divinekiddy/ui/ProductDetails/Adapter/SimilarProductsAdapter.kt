package com.textifly.divinekiddy.ui.ProductDetails.Adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.textifly.divinekiddy.R
import com.textifly.divinekiddy.ui.ProductDetails.Model.SimilarProductList
import com.textifly.divinekiddy.ui.ProductDetails.Model.SimilarProductsModel

class SimilarProductsAdapter(val modelList: List<SimilarProductList>) :
    RecyclerView.Adapter<SimilarProductsAdapter.ViewHolder>() {

    lateinit var context: Context

    class ViewHolder(val itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivImage : ImageView = itemView.findViewById(R.id.ivImage)
        val tvName : TextView = itemView.findViewById(R.id.tvName)
        val tvPrice : TextView = itemView.findViewById(R.id.tvPrice)
        val tvActualPrice : TextView = itemView.findViewById(R.id.tvActualPrice)
        val tvDiscountPercentage : TextView = itemView.findViewById(R.id.tvDiscountPercentage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.similar_product_layout,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //holder.ivImage.setImageResource(modelList[position].image)
        Glide.with(context)
            .load("https://divinekiddy.com/uploads/product/${modelList[position].image}")
            .into(holder.ivImage)
        holder.tvName.text =  modelList[position].name
        holder.tvPrice.text =  "₹"+modelList[position].special_price
        holder.tvActualPrice.text =  "₹"+modelList[position].actual_price
        holder.tvDiscountPercentage.text =  modelList[position].percentage + "% OFF"

        val bundle = Bundle()
        bundle.putString("prodId",modelList[position].product_id)

        /*holder.itemView.setOnClickListener{
                view ->
            view.findNavController()
                .navigate(context,bundle)
        }*/
    }

    override fun getItemCount(): Int {
        return modelList.size
    }
}