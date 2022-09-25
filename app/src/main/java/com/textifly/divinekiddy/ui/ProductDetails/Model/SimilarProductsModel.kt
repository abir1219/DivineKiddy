package com.textifly.divinekiddy.ui.ProductDetails.Model

import com.google.gson.annotations.SerializedName

data class SimilarProductsModel(
    @SerializedName("status")
    var status : String,
    @SerializedName("list")
    var list : List<SimilarProductList>,
)

data class SimilarProductList(
    @SerializedName("product_id")
    var product_id : String,
    @SerializedName("name")
    var name : String,
    @SerializedName("image")
    var image : String,
    @SerializedName("actual_price")
    var actual_price : String,
    @SerializedName("special_price")
    var special_price : String,
    @SerializedName("percentage")
    var percentage : String,
)

