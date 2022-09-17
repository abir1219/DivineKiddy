package com.textifly.divinekiddy.ui.ProductDetails.Model

import com.google.gson.annotations.SerializedName

data class ProductDetailsModel(
    @SerializedName("status")
    var status: Int?,
    @SerializedName("id")
    var id: String?,
    @SerializedName("category_id")
    var category_id: String?,
    @SerializedName("subcategory_id")
    var subcategory_id: String?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("image")
    var image: String?,
    @SerializedName("description")
    var description: String?,
    @SerializedName("actual_price")
    var actual_price: String?,
    @SerializedName("special_price")
    var special_price: String?,

    @SerializedName("age")
    var ageList: List<AgePriceModel>?,

    @SerializedName("product_image")
    var imageList: List<ProductImage>?
)

data class ProductImage(
    @SerializedName("id")
    var id: String?,
    @SerializedName("image")
    var image: String?,
)
