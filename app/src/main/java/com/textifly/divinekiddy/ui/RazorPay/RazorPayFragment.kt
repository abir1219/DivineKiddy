package com.textifly.divinekiddy.ui.RazorPay

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import com.textifly.divinekiddy.ApiManager.RetrofitHelper
import com.textifly.divinekiddy.CustomDialog.CustomProgressDialog
import com.textifly.divinekiddy.R
import com.textifly.divinekiddy.Utils.WebService
import com.textifly.divinekiddy.databinding.FragmentPaymentBinding
import com.textifly.divinekiddy.databinding.FragmentRazorPayBinding
import com.textifly.divinekiddy.ui.ProductDetails.Model.CartModel
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RazorPayFragment : Fragment(), PaymentResultListener {
    private var _binding: FragmentRazorPayBinding? = null
    private val binding get() = _binding!!

    private var retrofitHelper = RetrofitHelper.getRetrofitInstance()
    private var retrofitApiInterface = retrofitHelper.create(WebService::class.java)

    override fun onResume() {
        super.onResume()
        try {
            var bottomNav : BottomNavigationView?  = activity?.findViewById(R.id.bottom_nav)
            bottomNav?.visibility = View.GONE
        }catch (e:Exception){}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRazorPayBinding.inflate(inflater,container,false)
        try {
            var bottomNav : BottomNavigationView?  = activity?.findViewById(R.id.bottom_nav)
            bottomNav?.visibility = View.GONE
        }catch (e:Exception){}
        Checkout.preload(context)
        startPayment()
        return binding.root
    }

    private fun startPayment() {
        val sharedPreference = requireContext().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
        val amount = Math.round(arguments?.getString("amount")!!.toFloat() * 100)

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
            preFill.put("contact", sharedPreference.getString("mobile",""))
            jsonObject.put("prefill", preFill)

            //navigation_razor_pay_to_success
            checkout.open(requireActivity(), jsonObject)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    override fun onPaymentSuccess(razorpayPaymentID: String?) {
        Toast.makeText(requireContext(), "Payment Successful: $razorpayPaymentID", Toast.LENGTH_SHORT).show();
        checkOut()
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Log.d("ERROR-msg",p1.toString())
    }


    private fun checkOut() {
        val prefs = requireActivity().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
        CustomProgressDialog.showDialog(requireContext(),true)
        retrofitApiInterface.placeOrder(prefs.getString("uid",""),prefs.getString("addressId","")).enqueue(object :
            Callback<CartModel?> {
            override fun onResponse(call: Call<CartModel?>, response: Response<CartModel?>) {
                CustomProgressDialog.showDialog(requireContext(),false)
                if(response.body()!!.status.equals("Success", ignoreCase = true)){
                    val edit = prefs.edit()
                    edit.putString("order_id",response.body()!!.order_id)
                    edit.commit()
                    Toast.makeText(requireContext(),"Order Placed Successfully", Toast.LENGTH_SHORT).show()
                    view?.findNavController()?.navigate(R.id.navigation_razor_pay_to_success)
                }else{
                    Toast.makeText(requireContext(),"Order Placed un-successful", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<CartModel?>, t: Throwable) {
                CustomProgressDialog.showDialog(requireContext(),false)
                Toast.makeText(requireContext(),"Getting some troubles", Toast.LENGTH_SHORT).show()
            }
        })
    }
}