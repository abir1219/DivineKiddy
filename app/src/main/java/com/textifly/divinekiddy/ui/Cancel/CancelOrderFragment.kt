package com.textifly.divinekiddy.ui.Cancel

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.textifly.divinekiddy.ApiManager.RetrofitHelper
import com.textifly.divinekiddy.CommonSuccessModel.SuccessModel
import com.textifly.divinekiddy.CustomDialog.CustomProgressDialog
import com.textifly.divinekiddy.R
import com.textifly.divinekiddy.Utils.WebService
import com.textifly.divinekiddy.databinding.FragmentCancelOrderBinding
import com.textifly.divinekiddy.ui.ProductDetails.Model.ProductDetailsModel
import kotlinx.coroutines.NonDisposableHandle.parent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CancelOrderFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentCancelOrderBinding? = null
    private val binding get() = _binding!!

    private var retrofitHelper = RetrofitHelper.getRetrofitInstance()
    private var retrofitApiInterface = retrofitHelper.create(WebService::class.java)

    var reason: String? = null
    val bundle = Bundle()

    override fun onResume() {
        super.onResume()
        try {
            val bottomNav: BottomNavigationView? = activity?.findViewById(R.id.bottom_nav)
            bottomNav?.visibility = View.GONE
        } catch (e: Exception) {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCancelOrderBinding.inflate(inflater, container, false)
        try {
            val bottomNav: BottomNavigationView? = activity?.findViewById(R.id.bottom_nav)
            bottomNav?.visibility = View.GONE
        } catch (e: Exception) {
        }
        btnClick()
        loadSpinner()
        loadProduct()
        return binding.root
    }

    private fun loadSpinner() {
        val reasons = resources.getStringArray(R.array.Reasons)
        //Toast.makeText(requireContext(),reasons[0],Toast.LENGTH_SHORT).show()
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item, reasons
        )
        binding.spReason.adapter = adapter
        binding.spReason.isSelected = false
        binding.spReason.setSelection(0, true)

        binding.spReason.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                if (!reasons[position].equals("Select Reason", ignoreCase = false)) {
                    reason = reasons[position]
                    binding.spReason.visibility = View.GONE
                    binding.tvReason.text = reasons[position]
                } else {
                    reason = ""
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }

    private fun loadProduct() {
        CustomProgressDialog.showDialog(requireContext(), true)
        binding.tvProdName.text = arguments?.getString("prod_name")
        binding.tvProdAge.text = arguments?.getString("prod_age")
        binding.tvQuantity.text = arguments?.getString("prod_qty")
        setBundle()
        Glide.with(requireContext())
            .load("https://divinekiddy.com/uploads/product/" + arguments?.getString("prod_img"))
            .placeholder(R.drawable.loader).into(binding.ivImage)
        CustomProgressDialog.showDialog(requireContext(), false)

    }

    private fun setBundle() {
        bundle.putString("prod_name",arguments?.getString("prod_name"))
        bundle.putString("prod_age",arguments?.getString("prod_age"))
        bundle.putString("prod_qty",arguments?.getString("prod_qty"))
    }

    private fun btnClick() {
        binding.llMenu.setOnClickListener(this)
        binding.tvCancel.setOnClickListener(this)
        binding.tvReason.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.llMenu -> activity?.onBackPressed()
            R.id.tvCancel -> {
                if(reason!!.isEmpty()|| binding.tvComments.text.isEmpty()){
                    Toast.makeText(requireContext(),"Please select a reason",Toast.LENGTH_SHORT).show()
                }else{
                    cancelProduct()
                }
            }
            R.id.tvReason -> {
                binding.spReason.performClick()
                binding.spReason.visibility = View.VISIBLE
            }
        }
    }

    private fun cancelProduct() {
        Log.d("orderId", arguments?.getString("orderId")!!)

        CustomProgressDialog.showDialog(requireContext(), true)
        retrofitApiInterface.cancelProduct(arguments?.getString("orderId"),reason,binding.tvComments.text.toString()).enqueue(object : Callback<SuccessModel?> {
            override fun onResponse(call: Call<SuccessModel?>, response: Response<SuccessModel?>) {
                CustomProgressDialog.showDialog(requireContext(), false)
                if(response.body()!!.status.equals("success",ignoreCase = false)){
                    Toast.makeText(requireContext(),response.body()!!.message,Toast.LENGTH_SHORT).show()
                    val sharedPreference = requireActivity().getSharedPreferences("PREFERENCE",
                        Context.MODE_PRIVATE)

                    view?.findNavController()!!.navigate(R.id.navigation_cancel_order_to_cancel_success,bundle)
                }
            }

            override fun onFailure(call: Call<SuccessModel?>, t: Throwable) {
                CustomProgressDialog.showDialog(requireContext(), false)
                Toast.makeText(requireContext(),"Getting some troubles",Toast.LENGTH_SHORT).show()
            }
        })
    }

}