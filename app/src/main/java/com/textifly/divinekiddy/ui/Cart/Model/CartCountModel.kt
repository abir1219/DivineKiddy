package com.textifly.divinekiddy.ui.Cart.Model

import com.google.gson.annotations.SerializedName

data class CartCountModel(
    @SerializedName("status")
    var status : String?,
    @SerializedName("count")
    var count : String?
)
