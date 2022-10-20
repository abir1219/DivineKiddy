package com.textifly.divinekiddy.ui.ProfileEdit

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
import com.textifly.divinekiddy.databinding.FragmentEmailChangeBinding
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmailChangeFragment : Fragment(),View.OnClickListener  {
    private var _binding : FragmentEmailChangeBinding? = null
    private val  binding get() = _binding!!

    private var retrofitHelper = RetrofitHelper.getRetrofitInstance()
    private var retrofitApiInterface = retrofitHelper.create(WebService::class.java)

    override fun onResume() {
        super.onResume()
        try {
            val bottomNav : BottomNavigationView?  = activity?.findViewById(R.id.bottom_nav)
            bottomNav?.visibility = View.GONE
        }catch (e:Exception){}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEmailChangeBinding.inflate(inflater,container,false)
        try {
            var bottomNav : BottomNavigationView?  = activity?.findViewById(R.id.bottom_nav)
            bottomNav?.visibility = View.GONE
        }catch (e:Exception){}
        btnClick()
        initView()
        return binding.root
    }

    private fun initView() {
        CustomProgressDialog.showDialog(requireContext(),true)
        binding.tieEmail.setText(arguments?.getString("email",""))
        CustomProgressDialog.showDialog(requireContext(),false)
    }

    private fun btnClick() {
        binding.llMenu.setOnClickListener(this)
        binding.tvSaveEmail.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(view?.id){
            R.id.llMenu -> view?.findNavController()?.navigate(R.id.nav_email_change_to_profile_details)
            R.id.tvSaveName ->{
                updateEmail()
            }

            //view.findNavController().navigate(R.id.nav_name_change_to_profile_details)
        }
    }

    private fun updateEmail() {
        CustomProgressDialog.showDialog(requireContext(),true)
        val sharedPreference = context?.getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
        val uid = sharedPreference?.getString("uid", "")
        val userId: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), uid!!)
        val email: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), binding.tieEmail.text.toString())

        val call: Call<SuccessModel?> = retrofitApiInterface.profileUpdate(userId,null,null,email,null)

        call.enqueue(object : Callback<SuccessModel?> {
            override fun onResponse(call: Call<SuccessModel?>, response: Response<SuccessModel?>) {
                CustomProgressDialog.showDialog(requireContext(),false)
                Toast.makeText(requireContext(),response.body()!!.message, Toast.LENGTH_SHORT).show()
                val edit = sharedPreference.edit()
                edit?.putString("email",binding.tieEmail.text.toString())
                edit?.commit()
                view?.findNavController()?.navigate(R.id.nav_email_change_to_profile_details)
            }

            override fun onFailure(call: Call<SuccessModel?>, t: Throwable) {
                CustomProgressDialog.showDialog(requireContext(),false)
                Toast.makeText(requireContext(),"Getting some troubles", Toast.LENGTH_SHORT).show()
            }
        })


        /*CustomProgressDialog.showDialog(requireContext(),true)
        val sharedPreference = context?.getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
        val uid = sharedPreference?.getString("uid", "")
        retrofitApiInterface.profileUpdate(uid,"","",binding.tieEmail.text.toString(),"").enqueue(object :
            Callback<SuccessModel?> {
            override fun onResponse(call: Call<SuccessModel?>, response: Response<SuccessModel?>) {
                CustomProgressDialog.showDialog(requireContext(),false)
                Toast.makeText(requireContext(),response.body()!!.message, Toast.LENGTH_SHORT).show()
                val edit = sharedPreference?.edit()
                edit?.putString("email",binding.tieEmail.text.toString())
                edit?.commit()
                view?.findNavController()?.navigate(R.id.nav_email_change_to_profile_details)
            }

            override fun onFailure(call: Call<SuccessModel?>, t: Throwable) {
                CustomProgressDialog.showDialog(requireContext(),false)
                Toast.makeText(requireContext(),"Getting some troubles", Toast.LENGTH_SHORT).show()
            }
        })*/
    }
}