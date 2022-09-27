package com.textifly.divinekiddy.ui.Wishlist.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.textifly.divinekiddy.R
import com.textifly.divinekiddy.ui.Cart.CartFragment
import com.textifly.divinekiddy.ui.Wishlist.Model.WishlistList
import com.textifly.divinekiddy.ui.Wishlist.Model.WishlistModel
import com.textifly.divinekiddy.ui.Wishlist.WishlistFragment

class WishlistAdapter(var modelList: List<WishlistList>) :
    RecyclerView.Adapter<WishlistAdapter.ViewHolder>() {
    lateinit var context: Context

    var onDataRecived: WishlistFragment.onDataRecived? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.wishlist_layout, parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //holder.image.setImageResource(modelList[position].image)
        Glide.with(context)
            .load("https://divinekiddy.com/uploads/product/${modelList[position].image}")
            .into(holder.image)
        holder.tvSellingPrice.text = "₹ ${modelList[position].price}"
        holder.tvCostPrice.text = "₹ ${modelList[position].actual_price}"
        holder.tvTitle.text = "${modelList[position].name}"
        holder.tvCostPrice.text = "₹ ${modelList[position].actual_price}"
        holder.tvDiscountPercentage.text = "${modelList[position].parcentage} % OFF"
    }

    override fun getItemCount(): Int {
        return modelList.size
    }

    class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        var image: ImageView = itemview.findViewById(R.id.ivImage)
        var tvSellingPrice: TextView = itemview.findViewById(R.id.tvSellingPrice)
        var tvTitle: TextView = itemview.findViewById(R.id.tvTitle)
        var tvCostPrice: TextView = itemview.findViewById(R.id.tvCostPrice)
        var tvDiscountPercentage: TextView = itemview.findViewById(R.id.tvDiscountPercentage)
    }

    fun setListner(onDataRecived: WishlistFragment.onDataRecived) {
        this.onDataRecived = onDataRecived
    }
}