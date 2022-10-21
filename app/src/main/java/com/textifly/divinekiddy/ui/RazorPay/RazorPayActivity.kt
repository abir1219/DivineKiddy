package com.textifly.divinekiddy.ui.RazorPay

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import com.textifly.divinekiddy.ApiManager.RetrofitHelper
import com.textifly.divinekiddy.CustomDialog.CustomProgressDialog
import com.textifly.divinekiddy.R
import com.textifly.divinekiddy.Utils.WebService
import com.textifly.divinekiddy.databinding.ActivityRazorPayBinding
import com.textifly.divinekiddy.ui.OrderSuccess.AllDoneActivity
import com.textifly.divinekiddy.ui.ProductDetails.Model.CartModel
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RazorPayActivity : AppCompatActivity(),PaymentResultListener {
    private lateinit var binding: ActivityRazorPayBinding

    private var retrofitHelper = RetrofitHelper.getRetrofitInstance()
    private var retrofitApiInterface = retrofitHelper.create(WebService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_razor_pay)
        binding = ActivityRazorPayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Checkout.preload(applicationContext)
        startPayment()
    }

    private fun startPayment() {
        val amount = Math.round(intent.getStringExtra("total")!!.toFloat() * 100)
        val sharedPreference = getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)


        val checkout = Checkout()

        checkout.setKeyID("rzp_test_o3O4Hk7c1gysZz")
        //checkout.setImage(R.drawable.splash);
        //checkout.setImage(R.drawable.splash);
        checkout.setImage(R.drawable.divinekiddylogo)

        val jsonObject = JSONObject()
        try {
            jsonObject.put("name", "DivineKiddey")
            jsonObject.put("description", "Payment")
            jsonObject.put("theme.color", "#ffdd00")
            jsonObject.put("currency", "INR")
            jsonObject.put("amount", amount)
            val preFill = JSONObject()
            preFill.put("email", sharedPreference.getString("email",""))
            preFill.put("contact", intent.getStringExtra("mobile"))
            jsonObject.put("prefill", preFill)

            /*jsonObject.put("prefill.contact", YoDB.getPref().read(Constants.PHONE,""));
            jsonObject.put("prefill.email",YoDB.getPref().read(Constants.EMAIL,""));Log.e(
                "json",
                jsonObject.toString()
            )*/
            checkout.open(this@RazorPayActivity, jsonObject)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }


    private fun placeOrder() {
        val prefs = getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
        CustomProgressDialog.showDialog(this, true)
        retrofitApiInterface.placeOrder(prefs.getString("uid",""),prefs.getString("addressId","")).enqueue(object :
            Callback<CartModel?> {
            override fun onResponse(call: Call<CartModel?>, response: Response<CartModel?>) {
                CustomProgressDialog.showDialog(this@RazorPayActivity,false)
                if(response.body()!!.status.equals("Success", ignoreCase = true)){
                    /*val edit = prefs.edit()
                    edit.putString("order_id",response.body()!!.order_id)
                    edit.commit()*/
                    Toast.makeText(this@RazorPayActivity,"Order Placed Successfully", Toast.LENGTH_SHORT).show()

                    val intent = Intent (this@RazorPayActivity, AllDoneActivity::class.java)
                    intent.putExtra("total",getIntent().getStringExtra("total"))
                    intent.putExtra("discountPrice",getIntent().getStringExtra("discountPrice"))
                    intent.putExtra("order_id",response.body()!!.order_id)
                    startActivity(intent)

                    //view?.findNavController()?.navigate(R.id.nav_cart_to_shipping_address)
                }else{
                    Toast.makeText(this@RazorPayActivity,"Order Placed un-successful", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<CartModel?>, t: Throwable) {
                CustomProgressDialog.showDialog(this@RazorPayActivity,false)
                Toast.makeText(this@RazorPayActivity,"Getting some troubles", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onPaymentSuccess(p0: String?) {
        try {
            placeOrder()
            //Toast.makeText(this, "Payment Successful: " + razorpayPaymentID, Toast.LENGTH_SHORT).show();
        } catch (e: Exception) {
            //Log.e(TAG, "Exception in onPaymentSuccess", e)
        }
    }

    override fun onPaymentError(p0: Int, p1: String?) {

    }
}