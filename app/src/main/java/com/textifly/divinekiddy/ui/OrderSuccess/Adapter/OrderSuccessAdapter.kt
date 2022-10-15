package com.textifly.divinekiddy.ui.OrderSuccess.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.textifly.divinekiddy.R
import com.textifly.divinekiddy.ui.OrderSuccess.Model.OrderSuccessModel
import com.textifly.divinekiddy.ui.OrderSuccess.Model.SuccessOrderList
import com.textifly.divinekiddy.ui.Orders.Model.OrderList

class OrderSuccessAdapter (var modelList:List<SuccessOrderList>) : RecyclerView.Adapter<OrderSuccessAdapter.ViewHolder>(){
    lateinit var context: Context

    class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        var ivImage: ImageView = itemview.findViewById(R.id.ivImage)
        var ivMenu: ImageView? = itemview.findViewById(R.id.ivMenu)
        var ivBack: ImageView? = itemview.findViewById(R.id.ivBack)
        //var tvDeliveryTime: TextView? = itemview.findViewById(R.id.tvDeliveryTime)
        var tvProdName: TextView? = itemview.findViewById(R.id.tvProdName)
        var tvSize: TextView? = itemview.findViewById(R.id.tvSize)
        var tvQuantity: TextView? = itemview.findViewById(R.id.tvQuantity)
        var tvSellingPrice: TextView? = itemview.findViewById(R.id.tvSellingPrice)
        var tvCostPrice: TextView? = itemview.findViewById(R.id.tvCostPrice)
        var tvDiscountPercentage: TextView? = itemview.findViewById(R.id.tvDiscountPercentage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return OrderSuccessAdapter.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.order_successful_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context)
            .load("https://divinekiddy.com/uploads/product/${modelList[position].image}")
            .into(holder.ivImage)
        //holder.ivImage?.setImageResource(modelList[position].image)
        //holder.tvDeliveryTime?.text = modelList[position].deliveryDate
        holder.tvProdName?.text = modelList[position].name
        holder.tvSize?.text = "Size: "+modelList[position].age
        holder.tvQuantity?.text = "Quantity: ${modelList[position].quantity}"
        holder.tvSellingPrice?.text = "₹"+modelList[position].price
//        holder.tvCostPrice?.text = "₹"+modelList[position].actual_price
//        holder.tvDiscountPercentage?.text = modelList[position].parcentage+"% off"
    }

    override fun getItemCount(): Int {
        return modelList.size
    }
}