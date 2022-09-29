package com.textifly.divinekiddy.ui.SavedAddress.Adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.textifly.divinekiddy.ApiManager.RetrofitHelper
import com.textifly.divinekiddy.CustomDialog.CustomProgressDialog
import com.textifly.divinekiddy.R
import com.textifly.divinekiddy.Utils.WebService
import com.textifly.divinekiddy.ui.Cart.CartFragment
import com.textifly.divinekiddy.ui.ProductDetails.Model.CartModel
import com.textifly.divinekiddy.ui.SavedAddress.AddressListFragment
import com.textifly.divinekiddy.ui.SavedAddress.Model.AddressList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SavedAddressAdapter(var modelList: List<AddressList>) :
    RecyclerView.Adapter<SavedAddressAdapter.ViewHolder>() {
    lateinit var context: Context

    val retrofit = RetrofitHelper.getRetrofitInstance()
    val retrofitApiInterface = retrofit.create(WebService::class.java)

    var onDataRecived: AddressListFragment.onDataRecived? = null

    class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        var tvName: TextView = itemview.findViewById(R.id.tvName)
        var tvAddressType: TextView = itemview.findViewById(R.id.tvAddressType)
        var tvPhone: TextView = itemview.findViewById(R.id.tvPhone)
        var tvAddress: TextView = itemview.findViewById(R.id.tvAddress)
        var tvEditAddress: TextView = itemview.findViewById(R.id.tvEditAddress)
        var tvRemoveAddress: TextView = itemview.findViewById(R.id.tvRemoveAddress)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SavedAddressAdapter.ViewHolder {
        context = parent.context
        return SavedAddressAdapter.ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.saved_address_list_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SavedAddressAdapter.ViewHolder, position: Int) {
        holder.tvName.text = modelList[position].name
        holder.tvAddressType.text = modelList[position].defaultAddress
        holder.tvPhone.text = modelList[position].mobile
        holder.tvAddress.text =
            "${modelList[position].address}, ${modelList[position].landmark}, ${modelList[position].city}, ${modelList[position].state} - ${modelList[position].pin}"

        val bundle = Bundle()
        bundle.putString("addressId",modelList[position].id)

        holder.tvEditAddress.setOnClickListener {
                view ->view.findNavController()
            .navigate(R.id.nav_address_list_to_add_address,bundle)
            //nav_address_list_to_add_address
        }

        holder.tvRemoveAddress.setOnClickListener{
            CustomProgressDialog.showDialog(context,true)
            retrofitApiInterface.removeAddress(modelList[position].id).enqueue(object : Callback<CartModel?> {
                override fun onResponse(call: Call<CartModel?>, response: Response<CartModel?>) {
                    CustomProgressDialog.showDialog(context,false)
                    if(response.body()!!.status.equals("success",ignoreCase = true)){
                        Toast.makeText(context,"Address removed successfully",Toast.LENGTH_SHORT).show()
                        onDataRecived?.onCallBack(position.toString())
                    }
                }

                override fun onFailure(call: Call<CartModel?>, t: Throwable) {
                    CustomProgressDialog.showDialog(context,false)
                    Toast.makeText(context,"Getting some troubles",Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    override fun getItemCount(): Int {
        return modelList.size
    }

    fun setListner(onDataRecived: AddressListFragment.onDataRecived) {
        this.onDataRecived = onDataRecived
    }
}