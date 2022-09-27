package com.textifly.divinekiddy.ui.Cart.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.textifly.divinekiddy.ApiManager.RetrofitHelper
import com.textifly.divinekiddy.CustomDialog.CustomProgressDialog
import com.textifly.divinekiddy.R
import com.textifly.divinekiddy.Utils.WebService
import com.textifly.divinekiddy.ui.Cart.CartFragment
import com.textifly.divinekiddy.ui.Cart.Model.CartList
import com.textifly.divinekiddy.ui.ProductDetails.Model.CartModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CartAdapter(var modelList:List<CartList>) : RecyclerView.Adapter<CartAdapter.ViewHolder>(){
    lateinit var context: Context
    private var retrofitHelper = RetrofitHelper.getRetrofitInstance()
    private var retrofitApiInterface = retrofitHelper.create(WebService::class.java)

    var onDataRecived: CartFragment.onDataRecived? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.cart_layout,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        Glide.with(context)
            .load("https://divinekiddy.com/uploads/product/${modelList[position].image}")
            .into(holder.ivImage)
        //holder.ivImage?.setImageResource(modelList[position].image)
        //holder.tvDeliveryTime?.text = modelList[position].deliveryDate
        holder.tvProdName?.text = modelList[position].name
        holder.tvSize?.text = "Size: "+modelList[position].age
        holder.tvQuantity?.text = "Quantity: ${modelList[position].quantity}"
        holder.tvSellingPrice?.text = "₹"+modelList[position].special_price
        holder.tvCostPrice?.text = "₹"+modelList[position].actual_price
        holder.tvDiscountPercentage?.text = modelList[position].parcentage+"% off"

        Log.d("CART_ID", modelList[position].id!!)

        holder.ivMenu?.setOnClickListener{
            Log.d("","clicked")
            val animationZoomIn = AnimationUtils.loadAnimation(context, R.anim.slide_right_to_left)
            holder.llFunctions?.visibility = View.VISIBLE
            holder.llFunctions?.startAnimation(animationZoomIn)
            //holder.llData?.visibility = View.GONE
        }

        holder.ivBack?.setOnClickListener{
            val animationZoomIn = AnimationUtils.loadAnimation(context, R.anim.slide_left_to_right)
            holder.llFunctions?.startAnimation(animationZoomIn)
            //holder.llData?.visibility = View.VISIBLE
            holder.llFunctions?.visibility = View.GONE
        }

        holder.llMoveToWishlist?.setOnClickListener{
            Log.d("Cart_id",modelList[position].id.toString())
            CustomProgressDialog.showDialog(context, true)
            val sharedPreference = context.getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
            if (sharedPreference!!.contains("uid")) {
                val uid = sharedPreference.getString("uid", "")
                retrofitApiInterface.moveToWishlist(modelList[position].id, uid, "")
                    .enqueue(object : Callback<CartModel?> {
                        override fun onResponse(call: Call<CartModel?>, response: Response<CartModel?>
                        ) {
                            if (response.body()!!.status.equals("success")) {
                                CustomProgressDialog.showDialog(context, false)
                                removeFromCart(position, modelList[position].id!!,holder.llFunctions)
                                onDataRecived?.onCallBack(position.toString())
                            } else {
                                //Toast.makeText(context,"")
                                CustomProgressDialog.showDialog(context, false)
                            }
                        }

                        override fun onFailure(call: Call<CartModel?>, t: Throwable) {
                            CustomProgressDialog.showDialog(context, false)
                        }
                    })
            } else {
                val device_id: String = Settings.Secure.getString(
                    context.contentResolver,
                    Settings.Secure.ANDROID_ID
                )
                retrofitApiInterface.moveToWishlist(modelList[position].id, "", device_id)
                    .enqueue(object : Callback<CartModel?> {
                        override fun onResponse(
                            call: Call<CartModel?>,
                            response: Response<CartModel?>
                        ) {
                            if (response.body()!!.status.equals("success")) {
                                CustomProgressDialog.showDialog(context, false)
                                removeFromCart(position, modelList[position].id!!,holder.llFunctions)
                                onDataRecived?.onCallBack(position.toString())
                            } else {
                                //Toast.makeText(context,"")
                                CustomProgressDialog.showDialog(context, false)
                            }
                        }

                        override fun onFailure(call: Call<CartModel?>, t: Throwable) {
                            CustomProgressDialog.showDialog(context, false)
                        }
                    })
            }
        }

        holder.llRemoveCart?.setOnClickListener {
            Log.d("CART_ID", modelList[position].id!!)
            CustomProgressDialog.showDialog(context, true)
            removeFromCart(position,modelList[position].id!!,holder.llFunctions)
        }
    }

    private fun removeFromCart(position: Int, id: String, llFunctions: LinearLayout?) {
        retrofitApiInterface.removeFromCart(id)
            .enqueue(object : Callback<CartModel?> {
                override fun onResponse(
                    call: Call<CartModel?>,
                    response: Response<CartModel?>
                ) {
                    if (response.body()!!.status.equals("success")) {
                        CustomProgressDialog.showDialog(context, false)
                        onDataRecived?.onCallBack(position.toString())
                        llFunctions?.visibility = View.GONE
                        val animationZoomIn =
                            AnimationUtils.loadAnimation(context, R.anim.slide_left_to_right)
                        llFunctions?.startAnimation(animationZoomIn)
                        //holder.llData?.visibility = View.VISIBLE
                        llFunctions?.visibility = View.GONE
                    } else {
                        llFunctions?.visibility = View.VISIBLE
                        CustomProgressDialog.showDialog(context, false)
                    }
                }

                override fun onFailure(call: Call<CartModel?>, t: Throwable) {
                    Toast.makeText(context, "Getting some troubles", Toast.LENGTH_SHORT).show()
                    llFunctions?.visibility = View.VISIBLE
                    CustomProgressDialog.showDialog(context, false)
                }
            })
    }

    override fun getItemCount(): Int {
        return modelList.size
    }


    class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        var ivImage: ImageView = itemview.findViewById(R.id.ivImage)
        var ivMenu: ImageView? = itemview.findViewById(R.id.ivMenu)
        var ivBack: ImageView? = itemview.findViewById(R.id.ivBack)
        //var tvDeliveryTime: TextView? = itemview.findViewById(R.id.tvDeliveryTime)
        var tvProdName: TextView? = itemview.findViewById(R.id.tvProdName)
        var tvSize: TextView? = itemview.findViewById(R.id.tvSize)
        var tvQuantity: TextView? = itemview.findViewById(R.id.tvQuantity)
        var tvSellingPrice: TextView? = itemview.findViewById(R.id.tvSellingPrice)
        var tvCostPrice: TextView? = itemview.findViewById(R.id.tvCostPrice)
        var tvDiscountPercentage: TextView? = itemview.findViewById(R.id.tvDiscountPercentage)
        var llRemoveCart: LinearLayout? = itemview.findViewById(R.id.llRemoveCart)
        var llMoveToWishlist: LinearLayout? = itemview.findViewById(R.id.llMoveToWishlist)


        var llData: LinearLayout? = itemview.findViewById(R.id.llData)
        var llFunctions: LinearLayout? = itemview.findViewById(R.id.llFunctions)
    }

    fun setListner(onDataRecived4: CartFragment.onDataRecived) {
        onDataRecived = onDataRecived4
    }

}