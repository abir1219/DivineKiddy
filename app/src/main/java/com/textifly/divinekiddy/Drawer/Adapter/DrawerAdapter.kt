package com.textifly.divinekiddy.Drawer.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.textifly.divinekiddy.Drawer.Model.DrawerModel
import com.textifly.divinekiddy.MainActivity
import com.textifly.divinekiddy.R

class DrawerAdapter(var modelList:List<DrawerModel>):RecyclerView.Adapter<DrawerAdapter.ViewHolder>() {
    lateinit var context: Context
    private var mListener: MainActivity.OnDrawerMenuListener? = null

    class ViewHolder(var itemView: View):RecyclerView.ViewHolder(itemView){
        var tvMenu: TextView? = itemView.findViewById(R.id.tvMenu)
        var ivMenu: ImageView? = itemView.findViewById(R.id.ivMenu)
        var llMenu: LinearLayout? = itemView.findViewById(R.id.llMenu)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.drawer_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvMenu?.text = modelList[position].name
        holder.ivMenu?.setImageResource(modelList[position].image)

        holder.llMenu?.setOnClickListener {
            try {
                mListener!!.onDrawerMenuClick(position)
            } catch (e: NullPointerException) {
            }
        }
    }

    override fun getItemCount(): Int {
    return modelList.size
    }

    fun setListenerDrawerMenu(listener: MainActivity.OnDrawerMenuListener) {
        mListener = listener
    }

}