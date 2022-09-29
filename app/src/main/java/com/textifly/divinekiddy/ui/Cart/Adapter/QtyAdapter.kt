package com.textifly.divinekiddy.ui.Cart.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.textifly.divinekiddy.R
import com.textifly.divinekiddy.ui.Cart.Model.QtyModel

class QtyAdapter (var modelList:List<QtyModel>) : RecyclerView.Adapter<QtyAdapter.ViewHolder>(){

    var context: Context? = null
    companion object {
        var quantity = ""
    }
    var selected_position = -1

    class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        var cvBackground: CardView = itemview.findViewById(R.id.cvBackground)
        var tvQty: TextView = itemview.findViewById(R.id.qty)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.quantity_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvQty.setText(modelList[position].qty)
        holder.cvBackground.setCardBackgroundColor(
            if (selected_position == position) context!!.resources.getColor(
                R.color.yellow
            ) else context!!.resources.getColor(R.color.colorGray)
        )
        holder.tvQty.setTextColor(
            if (selected_position == position) context!!.resources.getColor(R.color.yellow) else context!!.resources.getColor(
                R.color.black
            )
        )

        holder.cvBackground.setOnClickListener {
            notifyItemChanged(selected_position)
            selected_position = holder.layoutPosition
            notifyItemChanged(selected_position)
            quantity = holder.tvQty.text.toString()

            //holder.cvBackground.setCardBackgroundColor(context.getResources().getColor(R.color.red5));
            //holder.tvQty.setTextColor(context.getResources().getColor(R.color.red5));
        }

    }

    override fun getItemCount(): Int {
        return modelList.size
    }
}