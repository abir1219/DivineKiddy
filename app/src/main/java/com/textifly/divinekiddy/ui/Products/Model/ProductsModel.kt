package com.textifly.divinekiddy.ui.Products.Model

import com.google.gson.annotations.SerializedName
import com.textifly.divinekiddy.ui.SubCategory.Model.SubCatList

data class ProductsModel(
    @SerializedName("status")
    var status : Int?,

    @SerializedName("list")
    var list : List<ProductList>
)

data class ProductList(
    @SerializedName("id")
    var id : String?,
    @SerializedName("category_id")
    var category_id : String?,
    @SerializedName("subcategory_id")
    var subcategory_id : String?,
    @SerializedName("name")
    var name : String?,
    @SerializedName("image")
    var image : String?,
    @SerializedName("description")
    var description : String?
)
