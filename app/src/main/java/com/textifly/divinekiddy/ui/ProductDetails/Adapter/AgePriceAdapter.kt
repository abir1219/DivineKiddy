package com.textifly.divinekiddy.ui.ProductDetails.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.textifly.divinekiddy.R
import com.textifly.divinekiddy.ui.ProductDetails.Model.AgePriceModel

class AgePriceAdapter(var modelList : List<AgePriceModel>):RecyclerView.Adapter<AgePriceAdapter.ViewHolder>(){
    lateinit var context: Context


    class ViewHolder(val itemview: View):RecyclerView.ViewHolder(itemview){
        var  tvAge : TextView = itemview.findViewById(R.id.tvAge)
        var  tvPrice : TextView = itemview.findViewById(R.id.tvPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.age_price_layout,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvAge.text= modelList[position].age
        holder.tvPrice.text= "₹"+modelList[position].price

        holder.itemview.setOnClickListener {
            val sharedPreference = context.getSharedPreferences(
                "PREFERENCE",
                Context.MODE_PRIVATE
            )
            val editor = sharedPreference.edit()
            editor.putString("priceId", modelList[position].id)
            editor.commit()
        }
    }

    override fun getItemCount(): Int {
        return modelList.size
    }
}