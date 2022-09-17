package com.textifly.divinekiddy.ui.Discover.Adapter

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.textifly.divinekiddy.R
import com.textifly.divinekiddy.ui.Discover.Model.CategoryList
import com.textifly.divinekiddy.ui.Discover.Model.HeaderImageModel
import com.textifly.divinekiddy.ui.Discover.Model.HeaderImageModel_old

class HeaderImageAdapter(var modelList: List<CategoryList>) :
    RecyclerView.Adapter<HeaderImageAdapter.MyViewHolder>() {

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        context = parent.context
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.header_image_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //holder.imageView.setImageResource(modelList.get(position).image)
        Glide.with(context)
            .load("https://divinekiddy.com/uploads/category/${modelList.get(position).image}")
            .into(holder.imageView)

        Log.d("CAT_NAME","name=> "+modelList.get(position).name)
        Log.d("CAT_ID","name=> "+ modelList[position].id)
        holder.tvHeaderName.text = modelList.get(position).name

        holder.llHeader.setOnClickListener { view ->
            val bundle = Bundle()
            bundle.putString("categoryId", modelList[position].id)
            view.findNavController()
                .navigate(R.id.nav_discover_to_subcategory,bundle)
        }

    }

    override fun getItemCount(): Int {
        return modelList.size
    }


    class MyViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val llHeader: LinearLayout = itemview.findViewById(R.id.llHeader)
        val imageView: ImageView = itemview.findViewById(R.id.ivHeaderImage)
        val tvHeaderName: TextView = itemview.findViewById(R.id.tvHeaderName)
    }
}