package com.textifly.divinekiddy.ui.Cart.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.textifly.divinekiddy.R
import com.textifly.divinekiddy.ui.SavedAddress.Model.AddressList

class CartAddressListAdapter(var modelList:List<AddressList>):RecyclerView.Adapter<CartAddressListAdapter.ViewHolder>() {
    lateinit var context: Context
    var selected_position = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.cart_address_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tvAddress?.text =
            "${modelList[position].address}, ${modelList[position].landmark}, ${modelList[position].city}, ${modelList[position].state} - ${modelList[position].pin}"

        holder.llAddress?.setBackgroundResource(
            if (selected_position == position)
                R.drawable.address_select_bg
                //context.resources.getColor(R.color.yellow)
            else R.drawable.address_unselect_bg//context.resources.getColor(R.color.black)
        )
        /*setCardBackgroundColor(
            if (selected_position == position) context!!.resources.getColor(
                R.color.yellow
            ) else context!!.resources.getColor(R.color.colorGray)
        )*/
        holder.tvAddress?.setTextColor(
            if (selected_position == position)
                context.resources.getColor(R.color.black)
              //  context.resources.getColor(R.color.black)
            else context.resources.getColor(R.color.black)//context.resources.getColor(R.color.grey)
        )

        holder.llAddress?.setOnClickListener {
            notifyItemChanged(selected_position)
            selected_position = holder.layoutPosition
            notifyItemChanged(selected_position)
            //QtyAdapter.quantity = holder.tvQty.text.toString()

            //holder.cvBackground.setCardBackgroundColor(context.getResources().getColor(R.color.red5));
            //holder.tvQty.setTextColor(context.getResources().getColor(R.color.red5));
        }
    }

    override fun getItemCount(): Int {
        return modelList.size
    }

    class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        var tvAddress: TextView? = itemView.findViewById(R.id.tvAddress)
        var llAddress: LinearLayout? = itemView.findViewById(R.id.llAddress)
    }
}