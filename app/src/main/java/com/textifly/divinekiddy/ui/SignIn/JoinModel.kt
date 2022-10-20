package com.textifly.divinekiddy.ui.SignIn

import com.google.gson.annotations.SerializedName

data class JoinModel(
    @SerializedName("status")
    var status : String?,
    @SerializedName("message")
    var message:String?,
    @SerializedName("id")
    var id:String?,
    @SerializedName("name")
    var name:String?,
    @SerializedName("email")
    var email:String?,
    @SerializedName("mobile")
    var mobile:String?,
    @SerializedName("image")
    var image:String?,
)
