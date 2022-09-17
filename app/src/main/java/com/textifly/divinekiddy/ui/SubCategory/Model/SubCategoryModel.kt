package com.textifly.divinekiddy.ui.SubCategory.Model

import com.google.gson.annotations.SerializedName

data class SubCategoryModel(
    @SerializedName("status")
    var status : Int?,

    @SerializedName("list")
    var list : List<SubCatList>
)

data class SubCatList(
    @SerializedName("id")
    var id : String?,
    @SerializedName("category_id")
    var category_id : String?,
    @SerializedName("name")
    var name : String?,
    @SerializedName("image")
    var image : String?,
    @SerializedName("description")
    var description : String?
)
