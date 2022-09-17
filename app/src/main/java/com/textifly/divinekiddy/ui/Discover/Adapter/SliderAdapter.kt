package com.textifly.divinekiddy.ui.Discover.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.smarteist.autoimageslider.SliderViewAdapter
import com.textifly.divinekiddy.R

class SliderAdapter(basepath: String?, imageUrl: ArrayList<String>) :
    SliderViewAdapter<SliderAdapter.ViewHolder>() {
    lateinit var context: Context
    var modelList: ArrayList<String> = imageUrl
    var imagePath = basepath


    class ViewHolder(itemView: View) : SliderViewAdapter.ViewHolder(itemView) {
        val ivSlider: ImageView = itemView.findViewById(R.id.ivSlider)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("ivSlider_$position",modelList.get(position))
        Glide.with(holder.itemView)
            .load(imagePath + modelList.get(position)).placeholder(R.drawable.loader).into(holder.ivSlider)
    }


    override fun getCount(): Int {
        return modelList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder {
        context = parent!!.context
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.image_slider_layout, parent, false)
        )
    }
}