package com.textifly.divinekiddy.ui.Discover.Model

import com.google.gson.annotations.SerializedName

data class HeaderImageModel(
    @SerializedName("status")
    val status : Int,
    @SerializedName("list")
    val List : List<CategoryList>
)


data class CategoryList(
    @SerializedName("id")
    val id : String,
    @SerializedName("name")
    val name : String,
    @SerializedName("image")
    val image : String,
    @SerializedName("description")
    val description : String,
)
