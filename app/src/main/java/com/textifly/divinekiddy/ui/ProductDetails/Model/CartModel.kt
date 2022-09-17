package com.textifly.divinekiddy.ui.ProductDetails.Model

import com.google.gson.annotations.SerializedName

data class CartModel(
    @SerializedName("status")
    var status : String? = null,

    @SerializedName("massage")
    var message : String? = null
)
