package com.textifly.divinekiddy.ui.Profile.Model

import com.google.gson.annotations.SerializedName

data class ProfileModel(
    @SerializedName("id") var id     : String? = null,
    @SerializedName("name") var name   : String? = null,
    @SerializedName("email") var email  : String? = null,
    @SerializedName("mobile") var mobile : String? = null,
    @SerializedName("image") var image  : String? = null
)
