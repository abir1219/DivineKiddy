package com.textifly.divinekiddy.ui.ProductDetails.Model

import com.google.gson.annotations.SerializedName

data class AgePriceModel(
    @SerializedName("id")
    var id : String?,
    @SerializedName("age")
    var age:String,
    @SerializedName("special_price")
    var price:String)
