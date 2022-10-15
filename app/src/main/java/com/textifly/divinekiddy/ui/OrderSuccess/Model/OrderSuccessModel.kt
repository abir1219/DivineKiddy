package com.textifly.divinekiddy.ui.OrderSuccess.Model

import com.google.gson.annotations.SerializedName

data class OrderSuccessModel(
    @SerializedName("status") var status : String?= null,
    @SerializedName("list") var list   : ArrayList<SuccessOrderList> = arrayListOf()
)

data class SuccessOrderList(
    @SerializedName("product_id") var productId: String? = null,
    @SerializedName("name") var name : String? = null,
    @SerializedName("image") var image : String? = null,
    @SerializedName("price_id") var priceId : String? = null,
    @SerializedName("age") var age : String? = null,
    @SerializedName("actual_price" ) var actualPrice : Int?    = null,
    @SerializedName("special_price") var specialPrice: Int?    = null,
    @SerializedName("price") var price : String? = null,
    @SerializedName("quantity") var quantity: String? = null,
    @SerializedName("parcentage") var parcentage : Double? = null
)
