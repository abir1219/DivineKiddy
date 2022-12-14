package com.textifly.divinekiddy.ui.ProfileEdit

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.textifly.divinekiddy.ApiManager.RetrofitHelper
import com.textifly.divinekiddy.CommonSuccessModel.SuccessModel
import com.textifly.divinekiddy.CustomDialog.CustomProgressDialog
import com.textifly.divinekiddy.R
import com.textifly.divinekiddy.Utils.WebService
import com.textifly.divinekiddy.databinding.FragmentNameChangeBinding
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NameChangeFragment : Fragment(),View.OnClickListener {
    private var _binding : FragmentNameChangeBinding? = null
    private val  binding get() = _binding!!

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
        _binding = FragmentNameChangeBinding.inflate(inflater,container,false)
        try {
            val bottomNav : BottomNavigationView?  = activity?.findViewById(R.id.bottom_nav)
            bottomNav?.visibility = View.GONE
        }catch (e:Exception){}
        btnClick()
        initView()
        return binding.root
    }

    private fun initView() {
        CustomProgressDialog.showDialog(requireContext(),true)
        binding.tieName.setText(arguments?.getString("name",""))
        CustomProgressDialog.showDialog(requireContext(),false)
    }

    private fun btnClick() {
        binding.llMenu.setOnClickListener(this)
        binding.tvSaveName.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.llMenu -> view.findNavController().navigate(R.id.nav_name_change_to_profile_details)
            R.id.tvSaveName ->{
                updateName()
            }

        //view.findNavController().navigate(R.id.nav_name_change_to_profile_details)
        }
    }

    private fun updateName() {
        CustomProgressDialog.showDialog(requireContext(),true)
        val sharedPreference = context?.getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
        val uid = sharedPreference?.getString("uid", "")
        val userId: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), uid!!)
        val name: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), binding.tieName.text.toString())

        val call: Call<SuccessModel?> = retrofitApiInterface.profileUpdate(userId,null,name,null,null)

        call.enqueue(object : Callback<SuccessModel?> {
            override fun onResponse(call: Call<SuccessModel?>, response: Response<SuccessModel?>) {
                CustomProgressDialog.showDialog(requireContext(),false)
                Toast.makeText(requireContext(),response.body()!!.message, Toast.LENGTH_SHORT).show()
                val edit = sharedPreference.edit()
                edit?.putString("uname",binding.tieName.text.toString())
                edit?.commit()
                view?.findNavController()?.navigate(R.id.nav_name_change_to_profile_details)
            }

            override fun onFailure(call: Call<SuccessModel?>, t: Throwable) {
                CustomProgressDialog.showDialog(requireContext(),false)
                Toast.makeText(requireContext(),"Getting some troubles", Toast.LENGTH_SHORT).show()
            }
        })

        /*retrofitApiInterface.profileUpdate(uid,"",,"","").enqueue(object :
            Callback<SuccessModel?> {
            override fun onResponse(call: Call<SuccessModel?>, response: Response<SuccessModel?>) {
                CustomProgressDialog.showDialog(requireContext(),false)
                Toast.makeText(requireContext(),response.body()!!.message, Toast.LENGTH_SHORT).show()
                val edit = sharedPreference?.edit()
                edit?.putString("uname",binding.tieName.text.toString())
                edit?.commit()
                view?.findNavController()?.navigate(R.id.nav_name_change_to_profile_details)
            }

            override fun onFailure(call: Call<SuccessModel?>, t: Throwable) {
                CustomProgressDialog.showDialog(requireContext(),false)
                Toast.makeText(requireContext(),"Getting some troubles", Toast.LENGTH_SHORT).show()
            }
        })*/
    }
}