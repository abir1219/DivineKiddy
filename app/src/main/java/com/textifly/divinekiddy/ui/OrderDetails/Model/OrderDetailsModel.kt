package com.textifly.divinekiddy.ui.OrderDetails.Model

import com.google.gson.annotations.SerializedName

data class OrderDetailsModel(
    @SerializedName("id")
    var id : String? = null,
    @SerializedName("user_id")
    var userId: String? = null,
    @SerializedName("order_id")
    var orderId : String? = null,
    @SerializedName("address_id")
    var addressId : String? = null,
    @SerializedName("name")
    var name : String? = null,
    @SerializedName("user_address_name")
    var userAddressName : String? = null,
    @SerializedName("email")
    var email: String? = null,
    @SerializedName("pin")
    var pin : String? = null,
    @SerializedName("address")
    var address : String? = null,
    @SerializedName("landmark")
    var landmark : String? = null,
    @SerializedName("state")
    var state : String? = null,
    @SerializedName("city")
    var city : String? = null,
    @SerializedName("mobile")
    var mobile : String? = null,
    @SerializedName("product_id")
    var productId: String? = null,
    @SerializedName("product_name" )
    var productName : String? = null,
    @SerializedName("image" )
    var image : String? = null,
    @SerializedName("price_id")
    var priceId : String? = null,
    @SerializedName("age")
    var age : String? = null,
    @SerializedName("price")
    var price  : String? = null,
    @SerializedName("quantity")
    var quantity : String? = null,
    @SerializedName("cancel_status")
    var cancelStatus  : String? = null,
    @SerializedName("discount")
    var discount  : String? = null
)
//discount
