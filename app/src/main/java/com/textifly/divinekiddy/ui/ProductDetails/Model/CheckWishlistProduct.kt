package com.textifly.divinekiddy.ui.ProductDetails.Model

import com.google.gson.annotations.SerializedName

data class CheckWishlistProduct(
    @SerializedName("status")
    var status : String?,
    @SerializedName("message")
    var message : String?
)
