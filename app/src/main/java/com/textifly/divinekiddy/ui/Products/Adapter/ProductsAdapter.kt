package com.textifly.divinekiddy.ui.Products.Adapter

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
import com.textifly.divinekiddy.ui.Products.Model.ProductList
import com.textifly.divinekiddy.ui.Products.Model.ProductsModel

class ProductsAdapter(var modelList: List<ProductList>) :
    RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsAdapter.ViewHolder {
        context = parent.context
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.products_layout,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.ivImage.setImageResource(modelList[position].imageUrl)
        /*holder.tvProdName?.text = modelList[position].prodName
        holder.tvSellingPrice?.text = "₹"+modelList[position].sellingPrice
        holder.tvCostPrice?.text = "₹"+modelList[position].costPrice
        holder.tvDiscountPercentage?.text = "( "+modelList[position].discount+"% off )"*/

        Glide.with(context)
            .load("https://divinekiddy.com/uploads/product/"+modelList[position].image)
            .into(holder.ivImage)
        holder.tvProdName.text = modelList[position].name

        val bundle = Bundle()
        bundle.putString("prodId",modelList[position].id)


        holder.itemView.setOnClickListener{
                view ->
            view.findNavController()
                .navigate(R.id.nav_product_to_product_details,bundle)
        }
    }

    override fun getItemCount(): Int {
        return modelList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivImage : ImageView = itemView.findViewById(R.id.ivImage)
        var tvProdName : TextView = itemView.findViewById(R.id.tvProdName)
        var tvSellingPrice: TextView? = itemView.findViewById(R.id.tvSellingPrice)
        var tvCostPrice: TextView? = itemView.findViewById(R.id.tvCostPrice)
        var tvDiscountPercentage: TextView? = itemView.findViewById(R.id.tvDiscountPercentage)
    }
}