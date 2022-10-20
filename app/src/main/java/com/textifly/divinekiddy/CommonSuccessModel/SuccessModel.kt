package com.textifly.divinekiddy.CommonSuccessModel

import com.google.gson.annotations.SerializedName

data class SuccessModel(
    @SerializedName("status")
    var status : String?,
    @SerializedName("message")
    var message : String?,
)
