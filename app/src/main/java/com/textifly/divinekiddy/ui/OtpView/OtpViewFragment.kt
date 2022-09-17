package com.textifly.divinekiddy.ui.OtpView

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.textifly.divinekiddy.R
import com.textifly.divinekiddy.Utils.WebService
import com.textifly.divinekiddy.databinding.FragmentOtpViewBinding
import com.textifly.divinekiddy.ApiManager.RetrofitHelper
import com.textifly.divinekiddy.CustomDialog.CustomProgressDialog
import com.textifly.divinekiddy.MainActivity
import com.textifly.divinekiddy.ui.SignIn.JoinModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit

class OtpViewFragment : Fragment(),View.OnClickListener {
    var _binding : FragmentOtpViewBinding? = null
    val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth
    // [END declare_auth]

    private var storedVerificationId: String? = ""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks


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
    ): View {
        _binding = FragmentOtpViewBinding.inflate(inflater,container,false)
        try {
            val bottomNav: BottomNavigationView? = activity?.findViewById(R.id.bottom_nav)
            bottomNav?.visibility = View.GONE
        } catch (e: Exception) {

        }
        binding.etOtp1.requestFocus()

        binding.etOtp1.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                
            }

            override fun afterTextChanged(text: Editable?) {
                if(text!!.length > 0){
                    binding.etOtp2.requestFocus()
                }
            }
        })

        binding.etOtp2.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                
            }

            override fun afterTextChanged(text: Editable?) {
                if(text!!.length > 0){
                    binding.etOtp3.requestFocus()
                }
            }
        })

        binding.etOtp3.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                
            }

            override fun afterTextChanged(text: Editable?) {
                if(text!!.length > 0){
                    binding.etOtp4.requestFocus()
                }
            }
        })

        binding.etOtp4.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                
            }

            override fun afterTextChanged(text: Editable?) {
                if(text!!.length > 0){
                    binding.etOtp5.requestFocus()
                }
            }
        })

        binding.etOtp5.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                
            }

            override fun afterTextChanged(text: Editable?) {
                if(text!!.length > 0){
                    binding.etOtp6.requestFocus()
                }
            }
        })

        binding.etOtp6.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //binding.etOtp1.requestFocus()
            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                
            }

            override fun afterTextChanged(text: Editable?) {
                
            }
        })

        auth = Firebase.auth
        btnClick()
        initView()
        return binding.root
    }

    private fun initView() {
        binding.tvMobileNo.text = "+91 "+arguments?.getString("mobile")
        //Toast.makeText(activity,"Mobile No : ${arguments?.getString("mobile")}",Toast.LENGTH_SHORT).show()

        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                Log.d("Credential", "onVerificationCompleted:$credential")
            }


            override fun onVerificationFailed(e: FirebaseException) {
                Log.w("FirebaseException", "onVerificationFailed", e)

                if (e is FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                } else if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                }
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d("CODE_SENT", "onCodeSent:$verificationId")

                // Save verification ID and resending token so we can use them later
                storedVerificationId = verificationId
                resendToken = token
            }
        }


        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber("+91"+arguments?.getString("mobile").toString())       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity())                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }


    private fun btnClick() {
        binding.llBack.setOnClickListener(this)
        binding.tvVerifyOtp.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.llBack-> view.findNavController().navigate(R.id.nav_otp_verification_to_join)
            R.id.tvVerifyOtp -> {
                val otp = binding.etOtp1.text.toString()+""+binding.etOtp2.text.toString()+""+binding.etOtp3.text.toString()+""+
                        binding.etOtp4.text.toString()+""+binding.etOtp5.text.toString()+""+binding.etOtp6.text.toString()
                Log.d("OTP",otp)

                val credential : PhoneAuthCredential = PhoneAuthProvider.getCredential(
                    storedVerificationId.toString(), otp)
                signInWithPhoneAuthCredential(credential)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun verifyPhoneNumberWithCode(verificationId: String?, code: String) {
        // [START verify_with_code]
        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
        // [END verify_with_code]
    }

    // [START resend_verification]
    private fun resendVerificationCode(
        phoneNumber: String,
        token: PhoneAuthProvider.ForceResendingToken?
    ) {
        val optionsBuilder = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber("+91"+phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity())                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
        if (token != null) {
            optionsBuilder.setForceResendingToken(token) // callback's ForceResendingToken
        }
        PhoneAuthProvider.verifyPhoneNumber(optionsBuilder.build())
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    //activity?.let { CustomProgressDialog.showDialog(it,true) }
                    CustomProgressDialog.showDialog(requireContext(),true)
                    //val user = task.result?.user
                    if(arguments?.getString("page").equals("join")){
                        joinUser()
                    }else if(arguments?.getString("page").equals("login")){
                        loginUser()
                    }
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                    // Update UI
                }
            }
    }

    private fun loginUser() {
        val phone = "+91 "+arguments?.getString("mobile")
        val retrofit = RetrofitHelper.getRetrofitInstance()
        val apiInterface = retrofit.create(WebService::class.java)

        apiInterface.login(phone!!).enqueue(object : Callback<JoinModel?> {
            override fun onResponse(
                call: Call<JoinModel?>,
                response: Response<JoinModel?>
            ) {
                CustomProgressDialog.showDialog(requireContext(),false)
                when (response.body()!!.status) {
                    "success" -> {
                        Toast.makeText(activity, response.body()!!.message, Toast.LENGTH_SHORT)
                            .show()
                        val sharedPreference =  requireActivity().getSharedPreferences("PREFERENCE",
                            Context.MODE_PRIVATE)
                        val editor = sharedPreference.edit()
                        editor.putString("uid",response.body()!!.id)
                        editor.putString("uname",response.body()!!.name)
                        editor.putString("email",response.body()!!.email)
                        editor.putString("mobile","91 "+response.body()!!.mobile)
                        editor.commit()

                        //view?.findNavController()?.navigate(R.id.nav_join_us_to_otp_verify)
                        startActivity(Intent(requireActivity(),MainActivity::class.java))
                        requireActivity().finish()
                    }
                    "error" -> {
                        Toast.makeText(activity, response.body()!!.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }

            override fun onFailure(call: Call<JoinModel?>, t: Throwable) {
                CustomProgressDialog.showDialog(requireContext(),false)
                Toast.makeText(requireContext(),"Getting some troubles",Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun joinUser() {
        val name = arguments?.getString("name")
        val email = arguments?.getString("email")
        val phone = "+91 "+arguments?.getString("mobile")
        val device_id: String = Settings.Secure.getString(requireActivity().contentResolver,
            Settings.Secure.ANDROID_ID)

        val retrofit = RetrofitHelper.getRetrofitInstance()
        val apiInterface = retrofit.create(WebService::class.java)

        apiInterface.join(name!!, email!!, phone!!,device_id).enqueue(object : Callback<JoinModel?> {
            override fun onResponse(
                call: Call<JoinModel?>,
                response: Response<JoinModel?>
            ) {
                CustomProgressDialog.showDialog(requireContext(),false)
                when (response.body()!!.status) {
                    "success" -> {
                        Toast.makeText(activity, response.body()!!.message, Toast.LENGTH_SHORT)
                            .show()
                        val sharedPreference =  requireActivity().getSharedPreferences("PREFERENCE",
                            Context.MODE_PRIVATE)
                        val editor = sharedPreference.edit()
                        editor.putString("uid",response.body()!!.id)
                        editor.putString("uname",response.body()!!.name)
                        editor.putString("email",response.body()!!.email)
                        editor.putString("mobile",response.body()!!.mobile)
                        editor.commit()

                        //view?.findNavController()?.navigate(R.id.nav_join_us_to_otp_verify)
                        startActivity(Intent(requireActivity(),MainActivity::class.java))
                        requireActivity().finish()
                    }
                    "error" -> {
                        Toast.makeText(activity, response.body()!!.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }

            override fun onFailure(call: Call<JoinModel?>, t: Throwable) {
                CustomProgressDialog.showDialog(requireContext(),false)
                Toast.makeText(requireContext(),"Getting some troubles",Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun updateUI(user: FirebaseUser? = auth.currentUser) {

    }

    companion object {
        private const val TAG = "PhoneAuthActivity"
    }
}