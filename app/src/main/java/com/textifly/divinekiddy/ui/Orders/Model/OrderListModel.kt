package com.textifly.divinekiddy.ui.Orders.Model

import com.google.gson.annotations.SerializedName

data class OrderListModel(
    @SerializedName("status")
    var status : String?,
    @SerializedName("list")
    var orderList : List<OrderList>
)

data class OrderList(
    @SerializedName("id")
    var id : String?,
    @SerializedName("user_id")
    var user_id : String?,
    @SerializedName("product_id")
    var product_id : String?,
    @SerializedName("name")
    var name : String?,
    @SerializedName("image")
    var image : String?,
    @SerializedName("price_id")
    var price_id : String?,
    @SerializedName("age")
    var age : String?,
    @SerializedName("price")
    var price : String?,
    @SerializedName("quantity")
    var quantity : String?,
    @SerializedName("cancel_status")
    var cancel_status : String?,
)
