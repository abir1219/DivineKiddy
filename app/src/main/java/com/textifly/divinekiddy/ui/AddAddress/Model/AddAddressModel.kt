package com.textifly.divinekiddy.ui.AddAddress.Model

import com.google.gson.annotations.SerializedName

data class AddAddressModel(
    @SerializedName("status")
    var status : String?,

    @SerializedName("message")
    var message : String?,
)
