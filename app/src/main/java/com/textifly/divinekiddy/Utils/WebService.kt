package com.textifly.divinekiddy.Utils

import com.textifly.divinekiddy.CommonSuccessModel.SuccessModel
import com.textifly.divinekiddy.ui.AddAddress.Model.AddAddressModel
import com.textifly.divinekiddy.ui.Cart.Model.CartCountModel
import com.textifly.divinekiddy.ui.Cart.Model.CartListModel
import com.textifly.divinekiddy.ui.Discover.Model.HeaderImageModel
import com.textifly.divinekiddy.ui.Discover.Model.SliderModel
import com.textifly.divinekiddy.ui.OrderDetails.Model.OrderDetailsModel
import com.textifly.divinekiddy.ui.OrderSuccess.Model.OrderSuccessModel
import com.textifly.divinekiddy.ui.Orders.Model.OrderListModel
import com.textifly.divinekiddy.ui.ProductDetails.Model.*
import com.textifly.divinekiddy.ui.Products.Model.ProductsModel
import com.textifly.divinekiddy.ui.Profile.Model.ProfileModel
import com.textifly.divinekiddy.ui.SavedAddress.Model.AddressList
import com.textifly.divinekiddy.ui.SavedAddress.Model.SavedAddressModel
import com.textifly.divinekiddy.ui.SignIn.JoinModel
import com.textifly.divinekiddy.ui.SubCategory.Model.SubCategoryModel
import com.textifly.divinekiddy.ui.Wishlist.Model.WishlistModel
import okhttp3.MultipartBody
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
    @POST("addwishlist")
    fun addToWishlist(
        @Field("product_id") product_id : String,
        @Field("user_id") user_id : String,
        @Field("device_id") device_id : String,
        @Field("price_id") price_id : String,
        @Field("price") price : String,
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
    @POST("wishlistcount")
    fun wishlistCount(
        @Field("user_id") user_id : String?,
        @Field("device_id") device_id : String?,
    ): Call<CartCountModel?>

    @FormUrlEncoded
    @POST("getcartlist")
    fun cartList(
        @Field("user_id") user_id : String?,
        @Field("device_id") device_id : String?,
    ): Call<CartListModel?>

    @FormUrlEncoded
    @POST("getwishlist")
    fun getWishlistList(
        @Field("user_id") user_id : String?,
        @Field("device_id") device_id : String?,
    ): Call<WishlistModel?>

    @FormUrlEncoded
    @POST("cartTransfer")
    fun cartTransfer(
        @Field("user_id") user_id : String?,
        @Field("device_id") device_id : String?,
    ): Call<SuccessModel?>

    @FormUrlEncoded
    @POST("cartUpdate")
    fun cartUpdate(
        @Field("cart_id") cart_id : String?,
        @Field("quantity") quantity : String?,
    ): Call<CartModel?>

    @FormUrlEncoded
    @POST("orders")
    fun placeOrder(
        @Field("user_id") user_id : String?,
        @Field("address_id") address_id : String?,
    ): Call<CartModel?>

    @FormUrlEncoded
    @POST("addaddress")
    fun addAddress(
        @Field("user_id") user_id : String?,
        @Field("name") name : String?,
        @Field("email") email : String?,
        @Field("address") address : String?,
        @Field("landmark") landmark : String?,
        @Field("state") state : String?,
        @Field("city") city : String?,
        @Field("pin") pin : String?,
        @Field("mobile") mobile : String?,
        @Field("default_address") default_address : String?,
    ): Call<AddAddressModel?>

    @FormUrlEncoded
    @POST("addressedit")
    fun getAddressById(
        @Field("id") id : String?
    ): Call<AddressList?>

    @FormUrlEncoded
    @POST("addressupdate")
    fun updateAddress(
        @Field("id") id: String?,
        @Field("user_id") user_id : String?,
        @Field("name") name : String?,
        @Field("email") email : String?,
        @Field("address") address : String?,
        @Field("landmark") landmark : String?,
        @Field("state") state : String?,
        @Field("city") city : String?,
        @Field("pin") pin : String?,
        @Field("mobile") mobile : String?,
    ):Call<SuccessModel?>

    @FormUrlEncoded
    @POST("getaddresslist")
    fun getAddressList(
        @Field("user_id") user_id : String?
    ): Call<SavedAddressModel?>

    @FormUrlEncoded
    @POST("removeaddress")
    fun removeAddress(
        @Field("id") id: String?,
    ):Call<SuccessModel?>

    @FormUrlEncoded
    @POST("related_productlist")
    fun getRelatedProducts(
        @Field("product_id") product_id: String?,
        @Field("subcategory_id") subcategory_id: String?
    ):Call<SimilarProductsModel?>

    @FormUrlEncoded
    @POST("removecart")
    fun removeFromCart(
        @Field("cartid") cartid: String?,
    ):Call<SuccessModel?>


    @FormUrlEncoded
    @POST("removewishlist")
    fun removeFromWishlist(
        @Field("wishid") wishid: String?,
    ):Call<SuccessModel?>

    @FormUrlEncoded
    @POST("moveToCart")
    fun moveToCart(
        @Field("wishid") wishid: String?,
        @Field("user_id") user_id: String?,
        @Field("device_id") device_id: String?,
    ):Call<SuccessModel?>

    @FormUrlEncoded
    @POST("moveToWishlist")
    fun moveToWishlist(
        @Field("cartid") cartid: String?,
        @Field("user_id") user_id: String?,
        @Field("device_id") device_id: String?,
    ):Call<SuccessModel?>

    @FormUrlEncoded
    @POST("wishlistcheck_product")
    fun checkProductInWishlist(
        @Field("user_id") user_id : String?,
        @Field("device_id") device_id : String?,
        @Field("product_id") product_id : String?,
    ):Call<CheckWishlistProduct?>

    @FormUrlEncoded
    @POST("orderlist")
    fun orderList(
        @Field("user_id") user_id : String?,
    ): Call<OrderListModel?>

    @FormUrlEncoded
    @POST("orderdetails")
    fun orderDetails(
        @Field("orders_id") user_id : String?,
    ): Call<OrderDetailsModel?>

    @FormUrlEncoded
    @POST("getRecentOrders")
    fun successOrderDetails(
        @Field("order_id") order_id : String?,
    ): Call<OrderSuccessModel?>

    @FormUrlEncoded
    @POST("profileedit")
    fun profileEdit(
        @Field("id") id : String?,
    ): Call<ProfileModel?>

    /*@FormUrlEncoded
    @POST("profileupdate")
    fun profileUpdate(
        @Field("user_id") user_id : String?,
        @Field("mobile") mobile : String?,
        @Field("name") name : String?,
        @Field("email") email : String?,
        @Field("image") image : String?,
    ): Call<SuccessModel?>*/

    @Multipart
    @POST("profileupdate")
    fun profileUpdate(
        @Part("user_id") user_id: RequestBody?,
        @Part("mobile") mobile: RequestBody?,
        @Part("name") name: RequestBody?,
        @Part("email") email: RequestBody?,
        @Part image: MultipartBody.Part?
    ):Call<SuccessModel?>

    @FormUrlEncoded
    @POST("cancelorder")
    fun cancelProduct(
        @Field("orderid") orderid : String?,
        @Field("reason") reason : String?,
        @Field("message") message : String?,
    ): Call<SuccessModel?>
}