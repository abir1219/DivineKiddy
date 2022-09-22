package com.textifly.divinekiddy.Utils

import com.textifly.divinekiddy.ui.Cart.Model.CartCountModel
import com.textifly.divinekiddy.ui.Cart.Model.CartListModel
import com.textifly.divinekiddy.ui.Discover.Model.HeaderImageModel
import com.textifly.divinekiddy.ui.Discover.Model.SliderModel
import com.textifly.divinekiddy.ui.ProductDetails.Model.CartModel
import com.textifly.divinekiddy.ui.ProductDetails.Model.ProductDetailsModel
import com.textifly.divinekiddy.ui.Products.Model.ProductsModel
import com.textifly.divinekiddy.ui.SignIn.JoinModel
import com.textifly.divinekiddy.ui.SubCategory.Model.SubCategoryModel
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface WebService {

    @GET("getbanner")
    fun getBanner(): Call<SliderModel?>

    @GET("getallcategory")
    fun getAllCategory(): Call<HeaderImageModel?>

    @FormUrlEncoded
    @POST("getallsubcategory")
    fun getAllSubCategory(@Field("category_id") category_id : String): Call<SubCategoryModel?>

    @FormUrlEncoded
    @POST("getallproduct")
    fun getAllProducts(
        @Field("category_id") category_id: String?,
        @Field("subcategory_id") subcategory_id: String?)
    : Call<ProductsModel?>

    @FormUrlEncoded
    @POST("productdetails")
    fun productDetails(@Field("product_id") product_id : String): Call<ProductDetailsModel?>

    @FormUrlEncoded
    @POST("addcart")
    fun addToCart(
        @Field("product_id") product_id : String,
        @Field("user_id") user_id : String,
        @Field("device_id") device_id : String,
        @Field("price_id") price_id : String,
        @Field("price") price : String,
        @Field("quantity") quantity : String,
    ): Call<CartModel?>

    @FormUrlEncoded
    @POST("registration")
    fun join(
        @Field("name") name : String,
        @Field("email") email : String,
        @Field("mobile") mobile : String,
        @Field("device_id") device_id : String,
    ): Call<JoinModel?>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("mobile") mobile : String,
    ): Call<JoinModel?>

    @FormUrlEncoded
    @POST("cartcount")
    fun cartCount(
        @Field("user_id") user_id : String?,
        @Field("device_id") device_id : String?,
    ): Call<CartCountModel?>

    @FormUrlEncoded
    @POST("getcartlist")
    fun cartList(
        @Field("user_id") user_id : String?,
        @Field("device_id") device_id : String?,
    ): Call<CartListModel?>

}