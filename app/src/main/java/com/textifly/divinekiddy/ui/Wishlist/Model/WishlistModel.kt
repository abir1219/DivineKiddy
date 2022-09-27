package com.textifly.divinekiddy.ui.Wishlist.Model

import com.google.gson.annotations.SerializedName

data class WishlistModel(
    @SerializedName("status")
    var status:String?,
    @SerializedName("total_price")
    var total_price:String?,
    @SerializedName("total_discount")
    var total_discount:String?,
    @SerializedName("list")
    var list : List<WishlistList>
    )

data class WishlistList(
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
    @SerializedName("percentage")
    var parcentage:String?,
)
