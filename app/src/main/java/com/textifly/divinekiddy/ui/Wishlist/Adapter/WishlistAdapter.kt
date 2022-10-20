package com.textifly.divinekiddy.ui.Wishlist.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.textifly.divinekiddy.ApiManager.RetrofitHelper
import com.textifly.divinekiddy.CommonSuccessModel.SuccessModel
import com.textifly.divinekiddy.CustomDialog.CustomProgressDialog
import com.textifly.divinekiddy.R
import com.textifly.divinekiddy.Utils.WebService
import com.textifly.divinekiddy.ui.Cart.CartFragment
import com.textifly.divinekiddy.ui.ProductDetails.Model.CartModel
import com.textifly.divinekiddy.ui.Wishlist.Model.WishlistList
import com.textifly.divinekiddy.ui.Wishlist.Model.WishlistModel
import com.textifly.divinekiddy.ui.Wishlist.WishlistFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WishlistAdapter(var modelList: List<WishlistList>) :
    RecyclerView.Adapter<WishlistAdapter.ViewHolder>() {
    lateinit var context: Context

    var onDataRecived: WishlistFragment.onDataRecived? = null

    val retrofit = RetrofitHelper.getRetrofitInstance()
    val retrofitApiInterface = retrofit.create(WebService::class.java)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.wishlist_layout, parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        //holder.image.setImageResource(modelList[position].image)
        Glide.with(context)
            .load("https://divinekiddy.com/uploads/product/${modelList[position].image}")
            .into(holder.image)
        holder.tvSellingPrice.text = "₹ ${modelList[position].price}"
        holder.tvCostPrice.text = "₹ ${modelList[position].actual_price}"
        holder.tvTitle.text = "${modelList[position].name}"
        holder.tvCostPrice.text = "₹ ${modelList[position].actual_price}"
        holder.tvDiscountPercentage.text = "${modelList[position].parcentage} % OFF"


        holder.tvMoveToCart.setOnClickListener {
            CustomProgressDialog.showDialog(context, true)
            val sharedPreference = context.getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
            if (sharedPreference!!.contains("uid")) {
                val uid = sharedPreference.getString("uid", "")
                retrofitApiInterface.moveToCart(modelList[position].id, uid, "")
                    .enqueue(object : Callback<SuccessModel?> {
                        override fun onResponse(
                            call: Call<SuccessModel?>,
                            response: Response<SuccessModel?>
                        ) {
                            if (response.body()!!.status.equals("success")) {
                                CustomProgressDialog.showDialog(context, false)
                                removeFromWishlist(position, modelList[position].id)
                                onDataRecived?.onCallBack(position.toString())
                            } else {
                                //Toast.makeText(context,"")
                                CustomProgressDialog.showDialog(context, false)
                            }
                        }

                        override fun onFailure(call: Call<SuccessModel?>, t: Throwable) {
                            CustomProgressDialog.showDialog(context, false)
                        }
                    })
            } else {
                val device_id: String = Settings.Secure.getString(
                    context.contentResolver,
                    Settings.Secure.ANDROID_ID
                )
                retrofitApiInterface.moveToCart(modelList[position].id, "", device_id)
                    .enqueue(object : Callback<SuccessModel?> {
                        override fun onResponse(
                            call: Call<SuccessModel?>,
                            response: Response<SuccessModel?>
                        ) {
                            if (response.body()!!.status.equals("success")) {
                                CustomProgressDialog.showDialog(context, false)
                                onDataRecived?.onCallBack(position.toString())
                            } else {
                                //Toast.makeText(context,"")
                                CustomProgressDialog.showDialog(context, false)
                            }
                        }

                        override fun onFailure(call: Call<SuccessModel?>, t: Throwable) {
                            CustomProgressDialog.showDialog(context, false)
                        }
                    })
            }
        }

        holder.cvRemove.setOnClickListener {
            CustomProgressDialog.showDialog(context, true)
            removeFromWishlist(position, modelList[position].id)
        }
    }

    private fun removeFromWishlist(position:Int?,id: String?) {
        retrofitApiInterface.removeFromWishlist(id).enqueue(object : Callback<SuccessModel?> {
            override fun onResponse(call: Call<SuccessModel?>, response: Response<SuccessModel?>) {
                CustomProgressDialog.showDialog(context,false)
                if(response.body()!!.status.equals("success")){
                    onDataRecived?.onCallBack(position.toString())
                }else{

                }
            }

            override fun onFailure(call: Call<SuccessModel?>, t: Throwable) {
                CustomProgressDialog.showDialog(context,false)
            }
        })
    }


    override fun getItemCount(): Int {
        return modelList.size
    }

    class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        var image: ImageView = itemview.findViewById(R.id.ivImage)
        var tvSellingPrice: TextView = itemview.findViewById(R.id.tvSellingPrice)
        var tvTitle: TextView = itemview.findViewById(R.id.tvTitle)
        var tvCostPrice: TextView = itemview.findViewById(R.id.tvCostPrice)
        var tvDiscountPercentage: TextView = itemview.findViewById(R.id.tvDiscountPercentage)
        var tvMoveToCart: TextView = itemview.findViewById(R.id.tvMoveToCart)
        var cvRemove: CardView = itemview.findViewById(R.id.cvRemove)
    }

    fun setListner(onDataRecived: WishlistFragment.onDataRecived) {
        this.onDataRecived = onDataRecived
    }
}