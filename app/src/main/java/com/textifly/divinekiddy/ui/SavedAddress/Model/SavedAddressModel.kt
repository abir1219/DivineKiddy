package com.textifly.divinekiddy.ui.SavedAddress.Model

import com.google.gson.annotations.SerializedName

data class SavedAddressModel(
    @SerializedName("status" )
    var status : String? = null,
    @SerializedName("list")
    var addressList : ArrayList<AddressList> = arrayListOf()
)

data class AddressList (

    @SerializedName("id") var id  : String? = null,
    @SerializedName("user_id") var userId : String? = null,
    @SerializedName("name") var name   : String? = null,
    @SerializedName("email") var email : String? = null,
    @SerializedName("address") var address : String? = null,
    @SerializedName("landmark") var landmark : String? = null,
    @SerializedName("city") var city: String? = null,
    @SerializedName("pin") var pin : String? = null,
    @SerializedName("mobile") var mobile: String? = null,
    @SerializedName("state") var state: String? = null,
    @SerializedName("default_address") var defaultAddress : String? = null

)
