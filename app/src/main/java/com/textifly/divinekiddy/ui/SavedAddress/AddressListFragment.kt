package com.textifly.divinekiddy.ui.SavedAddress

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.textifly.divinekiddy.ApiManager.RetrofitHelper
import com.textifly.divinekiddy.CustomDialog.CustomProgressDialog
import com.textifly.divinekiddy.R
import com.textifly.divinekiddy.Utils.WebService
import com.textifly.divinekiddy.databinding.FragmentAddressListBinding
import com.textifly.divinekiddy.ui.Cart.Adapter.CartAdapter
import com.textifly.divinekiddy.ui.SavedAddress.Adapter.SavedAddressAdapter
import com.textifly.divinekiddy.ui.SavedAddress.Model.SavedAddressModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddressListFragment : Fragment(),View.OnClickListener {
    private var _binding : FragmentAddressListBinding? = null
    private val  binding get() = _binding!!

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
        // Inflate the layout for this fragment
        _binding = FragmentAddressListBinding.inflate(inflater,container,false)
        try {
            var bottomNav : BottomNavigationView?  = activity?.findViewById(R.id.bottom_nav)
            bottomNav?.visibility = View.GONE
        }catch (e:Exception){}

        btnClick()
        setLayout()
        loadAddressList()
        return binding.root
    }

    private fun setLayout() {
        binding.rvSavedAddressList.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
    }

    private fun loadAddressList() {
        CustomProgressDialog.showDialog(requireContext(), true)
        val sharedPreference = requireContext().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
        val uid = sharedPreference.getString("uid", "")
        retrofitApiInterface.getAddressList(uid).enqueue(object : Callback<SavedAddressModel?> {
            override fun onResponse(
                call: Call<SavedAddressModel?>,
                response: Response<SavedAddressModel?>
            ) {
                Log.d("Addresslist_RES",response.body()!!.toString())
                CustomProgressDialog.showDialog(requireContext(), false)
                if(response.body()!!.status.equals("success",ignoreCase = true)){
                    binding.rvSavedAddressList.visibility = View.VISIBLE
                    binding.rlNoRecordsFound.visibility = View.GONE
                    val savedAddressAdapter = SavedAddressAdapter(response.body()!!.addressList)
                    binding.rvSavedAddressList.adapter = savedAddressAdapter

                    savedAddressAdapter.setListner(object : onDataRecived {
                        override fun onCallBack(pos: String?) {
                            loadAddressList()
                        }
                    })
                }else{
                    binding.rvSavedAddressList.visibility = View.GONE
                    binding.rlNoRecordsFound.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<SavedAddressModel?>, t: Throwable) {
                CustomProgressDialog.showDialog(requireContext(), false)
            }
        })
    }

    private fun btnClick() {
        binding.ivBack.setOnClickListener(this)
        binding.tvAddAddress.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.ivBack -> view.findNavController().navigate(R.id.nav_address_list_to_account)
            R.id.tvAddAddress -> view.findNavController().navigate(R.id.nav_address_list_to_add_address)
        }
    }

    interface onDataRecived {
        fun onCallBack(pos: String?)
    }

}