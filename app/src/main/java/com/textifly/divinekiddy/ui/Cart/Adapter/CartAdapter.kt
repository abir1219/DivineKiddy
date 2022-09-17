package com.textifly.divinekiddy.ui.Cart.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.textifly.divinekiddy.R
import com.textifly.divinekiddy.ui.Cart.Model.CartModel

class CartAdapter(var modelList:List<CartModel>) : RecyclerView.Adapter<CartAdapter.ViewHolder>(){
    lateinit var context: Context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.cart_layout,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.ivImage?.setImageResource(modelList[position].imageUrl)
        //holder.tvDeliveryTime?.text = modelList[position].deliveryDate
        holder.tvProdName?.text = modelList[position].prodName
        holder.tvSize?.text = "Size: "+modelList[position].size+" years"
        //holder.tvQuantity?.text = modelList[position].quantity
        holder.tvSellingPrice?.text = "₹"+modelList[position].sellingPrice
        holder.tvCostPrice?.text = "₹"+modelList[position].costPrice
        holder.tvDiscountPercentage?.text = modelList[position].discount+"% off"

        holder.ivMenu?.setOnClickListener(View.OnClickListener {
            Log.d("","clicked")
            val animationZoomIn = AnimationUtils.loadAnimation(context, R.anim.slide_right_to_left)
            holder.llFunctions?.visibility = View.VISIBLE
            holder.llFunctions?.startAnimation(animationZoomIn)
            //holder.llData?.visibility = View.GONE
        })

        holder.ivBack?.setOnClickListener(View.OnClickListener {
            val animationZoomIn = AnimationUtils.loadAnimation(context, R.anim.slide_left_to_right)
            holder.llFunctions?.startAnimation(animationZoomIn)
            //holder.llData?.visibility = View.VISIBLE
            holder.llFunctions?.visibility = View.GONE
        })
    }

    override fun getItemCount(): Int {
        return modelList.size
    }


    class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        var ivImage: ImageView? = itemview.findViewById(R.id.ivImage)
        var ivMenu: ImageView? = itemview.findViewById(R.id.ivMenu)
        var ivBack: ImageView? = itemview.findViewById(R.id.ivBack)
        //var tvDeliveryTime: TextView? = itemview.findViewById(R.id.tvDeliveryTime)
        var tvProdName: TextView? = itemview.findViewById(R.id.tvProdName)
        var tvSize: TextView? = itemview.findViewById(R.id.tvSize)
        //var tvQuantity: TextView? = itemview.findViewById(R.id.tvQuantity)
        var tvSellingPrice: TextView? = itemview.findViewById(R.id.tvSellingPrice)
        var tvCostPrice: TextView? = itemview.findViewById(R.id.tvCostPrice)
        var tvDiscountPercentage: TextView? = itemview.findViewById(R.id.tvDiscountPercentage)
        var llData: LinearLayout? = itemview.findViewById(R.id.llData)
        var llFunctions: LinearLayout? = itemview.findViewById(R.id.llFunctions)
    }
}