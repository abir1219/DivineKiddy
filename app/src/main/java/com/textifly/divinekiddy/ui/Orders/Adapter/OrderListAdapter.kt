package com.textifly.divinekiddy.ui.Orders.Adapter

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
import com.textifly.divinekiddy.ui.Orders.Model.OrderList

class OrderListAdapter(var modelList: List<OrderList>) :
    RecyclerView.Adapter<OrderListAdapter.ViewHolder>() {
    lateinit var context: Context

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivProdImage: ImageView = itemView.findViewById(R.id.ivProdImage)
        var tvProdName: TextView = itemView.findViewById(R.id.tvProdName)
        var tvAge: TextView = itemView.findViewById(R.id.tvAge)
        var tvPrice: TextView = itemView.findViewById(R.id.tvPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.order_list_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context)
            .load("https://divinekiddy.com/uploads/product/${modelList[position].image}")
            .into(holder.ivProdImage)

        holder.tvProdName.text = modelList[position].name
        holder.tvAge.text = modelList[position].age
        holder.tvPrice.text = "₹" + modelList[position].price

        val bundle = Bundle()
        bundle.putString("order_id",modelList[position].id)

        holder.itemView.setOnClickListener{
                view ->
            view.findNavController()
                .navigate(R.id.nav_order_list_to_order_details,bundle)
        }


        //navigation_profile_details
    }

    override fun getItemCount(): Int {
        return modelList.size
    }
}