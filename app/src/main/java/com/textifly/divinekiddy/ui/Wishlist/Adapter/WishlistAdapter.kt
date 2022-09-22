package com.textifly.divinekiddy.ui.Wishlist.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.textifly.divinekiddy.R
import com.textifly.divinekiddy.ui.Wishlist.Model.WishlistModel

class WishlistAdapter(var modelList: ArrayList<WishlistModel>) :
    RecyclerView.Adapter<WishlistAdapter.ViewHolder>() {
    lateinit var context: Context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.wishlist_layout, parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.image.setImageResource(modelList[position].image)
        holder.tvPrice.text = "₹ ${modelList[position].price}"
    }

    override fun getItemCount(): Int {
        return modelList.size
    }

    class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        var image: ImageView = itemview.findViewById(R.id.ivImage)
        var tvPrice: TextView = itemview.findViewById(R.id.tvPrice)
    }
}