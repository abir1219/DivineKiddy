package com.textifly.divinekiddy.ui.AddAddress

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.textifly.divinekiddy.ApiManager.RetrofitHelper
import com.textifly.divinekiddy.CommonSuccessModel.SuccessModel
import com.textifly.divinekiddy.CustomDialog.CustomProgressDialog
import com.textifly.divinekiddy.R
import com.textifly.divinekiddy.Utils.WebService
import com.textifly.divinekiddy.databinding.FragmentAddAddressBinding
import com.textifly.divinekiddy.ui.AddAddress.Model.AddAddressModel
import com.textifly.divinekiddy.ui.ProductDetails.Model.CartModel
import com.textifly.divinekiddy.ui.SavedAddress.Model.AddressList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddAddressFragment : Fragment(),View.OnClickListener {
    private var _binding : FragmentAddAddressBinding? = null
    private val binding get() = _binding!!

    var addressType = "";

    val retrofit = RetrofitHelper.getRetrofitInstance()
    val retrofitApiInterface = retrofit.create(WebService::class.java)

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
        _binding = FragmentAddAddressBinding.inflate(inflater,container,false)
        try {
            var bottomNav : BottomNavigationView?  = activity?.findViewById(R.id.bottom_nav)
            bottomNav?.visibility = View.GONE
        }catch (e:Exception){}
        btnClick()
        if(arguments?.containsKey("addressId") == true){
            loadAddressData()
        }
        return binding.root
    }

    private fun loadAddressData() {
        CustomProgressDialog.showDialog(requireContext(),true)
        retrofitApiInterface.getAddressById(arguments?.getString("addressId")).enqueue(object : Callback<AddressList?> {
            override fun onResponse(call: Call<AddressList?>, response: Response<AddressList?>) {
                CustomProgressDialog.showDialog(requireContext(),false)
                binding.tieName.setText(response.body()!!.name)
                binding.tieEmail.setText(response.body()!!.email)
                binding.tieAddress.setText(response.body()!!.address)
                binding.tieLandmark.setText(response.body()!!.landmark)
                binding.tieCity.setText(response.body()!!.city)
                binding.tieState.setText(response.body()!!.state)
                binding.tiePincode.setText(response.body()!!.pin)
                binding.tieMobile.setText(response.body()!!.mobile)
            }

            override fun onFailure(call: Call<AddressList?>, t: Throwable) {
                CustomProgressDialog.showDialog(requireContext(),false)
            }
        })
    }

    private fun btnClick() {
        binding.llMenu.setOnClickListener(this)
        binding.tvSavedAddress.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.llMenu -> activity?.onBackPressed()//view.findNavController().navigate(R.id.nav_to_add_address_address_list)
            R.id.tvSavedAddress -> {
              checkValidity()
            }

        }
    }

    private fun checkValidity() {
        if (binding.tieName.text!!.toString().isEmpty()){
            binding.tieName.requestApplyInsets();
            Toast.makeText(requireContext(),"Enter full name",Toast.LENGTH_SHORT).show()
        }else if (binding.tieEmail.text.toString()!!.isEmpty()){
            binding.tieEmail.requestApplyInsets();
            Toast.makeText(requireContext(),"Enter email address",Toast.LENGTH_SHORT).show()
        }else if (binding.tieAddress.text.toString()!!.isEmpty()){
            binding.tieAddress.requestApplyInsets();
            Toast.makeText(requireContext(),"Enter full address",Toast.LENGTH_SHORT).show()
        }else if (binding.tieLandmark.text.toString()!!.isEmpty()){
            binding.tieLandmark.requestApplyInsets();
            Toast.makeText(requireContext(),"Enter landmark",Toast.LENGTH_SHORT).show()
        }else if (binding.tieCity.text.toString()!!.isEmpty()){
            binding.tieCity.requestApplyInsets();
            Toast.makeText(requireContext(),"Enter city",Toast.LENGTH_SHORT).show()
        }else if (binding.tieState.text.toString()!!.isEmpty()){
            binding.tieState.requestApplyInsets();
            Toast.makeText(requireContext(),"Enter state",Toast.LENGTH_SHORT).show()
        }else if (binding.tiePincode.text.toString()!!.isEmpty()){
            binding.tiePincode.requestApplyInsets();
            Toast.makeText(requireContext(),"Enter pincode",Toast.LENGTH_SHORT).show()
        }else if (binding.tieMobile.text.toString()!!.isEmpty()){
            binding.tieMobile.requestApplyInsets();
            Toast.makeText(requireContext(),"Enter Mobile No",Toast.LENGTH_SHORT).show()
        }else{
            saveAddress()
        }
    }

    private fun saveAddress() {
        CustomProgressDialog.showDialog(requireContext(),true)
        if(binding.swAddressType.isChecked()){
            addressType = "Default"
        }else{
            addressType = ""
        }
        val sharedPreference = context?.getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
        val uid = sharedPreference!!.getString("uid", "")
        if(arguments?.containsKey("addressId")==true){
            retrofitApiInterface.updateAddress(arguments?.getString("addressId"),uid,binding.tieName.text.toString(),binding.tieEmail.text.toString(),
                binding.tieAddress.text.toString(),binding.tieLandmark.text.toString(), binding.tieState.text.toString(),
                binding.tieCity.text.toString(),binding.tiePincode.text.toString(),binding.tieMobile.text.toString()).enqueue(object : Callback<SuccessModel?> {
                override fun onResponse(
                    call: Call<SuccessModel?>,
                    response: Response<SuccessModel?>
                ) {
                    CustomProgressDialog.showDialog(requireContext(),false)
                    if(response.body()!!.status.equals("success", ignoreCase = true)){
                        Toast.makeText(requireContext(),"Address updated successfully",Toast.LENGTH_SHORT).show()
                        view!!.findNavController().navigate(R.id.nav_to_add_address_address_list)
                    }
                }

                override fun onFailure(call: Call<SuccessModel?>, t: Throwable) {
                    CustomProgressDialog.showDialog(requireContext(),false)
                    Toast.makeText(requireContext(),"Getting some troubles",Toast.LENGTH_SHORT).show()
                }
            })
        }else{
            retrofitApiInterface.addAddress(uid,binding.tieName.text.toString(),binding.tieEmail.text.toString(),
                binding.tieAddress.text.toString(),binding.tieLandmark.text.toString(), binding.tieState.text.toString(),
                binding.tieCity.text.toString(),binding.tiePincode.text.toString(),binding.tieMobile.text.toString(),addressType).enqueue(object : Callback<AddAddressModel?> {
                override fun onResponse(
                    call: Call<AddAddressModel?>,
                    response: Response<AddAddressModel?>
                ) {
                    CustomProgressDialog.showDialog(requireContext(),false)
                    if(response.body()!!.status.equals("success", ignoreCase = true)){
                        Toast.makeText(requireContext(),"Address added successfully",Toast.LENGTH_SHORT).show()
                        view!!.findNavController().navigate(R.id.nav_to_add_address_address_list)
                    }
                }

                override fun onFailure(call: Call<AddAddressModel?>, t: Throwable) {
                    CustomProgressDialog.showDialog(requireContext(),false)
                    Toast.makeText(requireContext(),"Getting some troubles",Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}