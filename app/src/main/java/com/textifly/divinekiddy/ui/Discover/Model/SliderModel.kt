package com.textifly.divinekiddy.ui.Discover.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SliderModel(
    @SerializedName("status")
    var status : String? = null,
    @SerializedName("image")
    var list: List<SliderImage>? = null
)


/*data class ImageList(
    @SerializedName("list")
    val list: List<SliderImage>? = null
)*/

data class SliderImage(
    @SerializedName("image")
    val image: String?
)




