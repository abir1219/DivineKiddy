package com.textifly.divinekiddy.ui.Cart.Model

import com.google.gson.annotations.SerializedName

data class CartListModel(
    @SerializedName("status")
    var status:String?,
    @SerializedName("list")
    var list : List<CartList>
)

data class CartList(
    @SerializedName("id")
    var id:String?,
    @SerializedName("user_id")
    var user_id:String?,
    @SerializedName("product_id")
    var product_id:String?,
    @SerializedName("name")
    var name:String?,
    @SerializedName("image")
    var image:String?,
    @SerializedName("price_id")
    var price_id:String?,
    @SerializedName("age")
    var age:String?,
    @SerializedName("actual_price")
    var actual_price:String?,
    @SerializedName("special_price")
    var special_price:String?,
    @SerializedName("price")
    var price:String?,
    @SerializedName("quantity")
    var quantity:String?,
    @SerializedName("parcentage")
    var parcentage:String?,
)


/*
var imageUrl: Int,
var prodName: String,
//var deliveryDate: String,
var size: String,
//var quantity: String,
var costPrice: String,
var sellingPrice: String,
var discount: String,*/
