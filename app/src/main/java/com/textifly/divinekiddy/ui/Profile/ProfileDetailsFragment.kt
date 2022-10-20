package com.textifly.divinekiddy.ui.Profile

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.textifly.divinekiddy.ApiManager.RetrofitHelper
import com.textifly.divinekiddy.CommonSuccessModel.SuccessModel
import com.textifly.divinekiddy.CustomDialog.CustomProgressDialog
import com.textifly.divinekiddy.R
import com.textifly.divinekiddy.Utils.WebService
import com.textifly.divinekiddy.databinding.FragmentProfileDetailsBinding
import com.textifly.divinekiddy.ui.Profile.Model.ProfileModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*


class ProfileDetailsFragment : Fragment(),View.OnClickListener {
    private var _binding : FragmentProfileDetailsBinding? = null
    private val  binding get() = _binding!!

    private var retrofitHelper = RetrofitHelper.getRetrofitInstance()
    private var retrofitApiInterface = retrofitHelper.create(WebService::class.java)


    private val REQUEST_ID_MULTIPLE_PERMISSIONS = 101
    var imgFile: File? = null


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
        _binding = FragmentProfileDetailsBinding.inflate(inflater,container,false)
        try {
            var bottomNav : BottomNavigationView?  = activity?.findViewById(R.id.bottom_nav)
            bottomNav?.visibility = View.GONE
        }catch (e:Exception){}
        btnClick()
        loadProfile()
        return binding.root
    }

    private fun loadProfile() {
        CustomProgressDialog.showDialog(requireContext(), true)
        val sharedPreference = requireActivity().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
        val uid = sharedPreference.getString("uid", "")
        retrofitApiInterface.profileEdit(uid).enqueue(object : Callback<ProfileModel?> {
            override fun onResponse(call: Call<ProfileModel?>, response: Response<ProfileModel?>) {
                CustomProgressDialog.showDialog(requireContext(), false)
                binding.tvName.text= response.body()!!.name
                binding.tvMobile.text= response.body()!!.mobile
                binding.tvEmail.text= response.body()!!.email
                if(response.body()!!.image == null){
                    Glide.with(requireContext())
                        .load("https://divinekiddy.com/uploads/profile/${response.body()!!.image}")
                        .into(binding.ivProfile)
                }else{
                    Glide.with(requireContext())
                        .load(R.drawable.avatar)
                        .into(binding.ivProfile)
                }

            }

            override fun onFailure(call: Call<ProfileModel?>, t: Throwable) {
                CustomProgressDialog.showDialog(requireContext(), false)
                Toast.makeText(requireContext(),"Getting some troubles",Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun btnClick() {
        binding.ivBack.setOnClickListener(this)
        binding.rlName.setOnClickListener(this)
        binding.rlMobile.setOnClickListener(this)
        binding.rlEmail.setOnClickListener(this)
        binding.rlEmail.setOnClickListener(this)
        binding.llProfileImage.setOnClickListener(this)
        binding.rlEmail.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.ivBack -> view.findNavController().navigate(R.id.nav_profile_details_to_account)
            R.id.rlName ->
            {
                val bundle = Bundle()
                bundle.putString("name",binding.tvName.text.toString())
                view.findNavController().navigate(R.id.nav_profile_details_to_name_change,bundle)
            }
            R.id.rlMobile ->
            {
                val bundle = Bundle()
                bundle.putString("mobile",binding.tvMobile.text.toString())
                view.findNavController().navigate(R.id.nav_profile_details_to_mobile_change,bundle)
            }

            R.id.rlEmail -> {
                val bundle = Bundle()
                bundle.putString("email",binding.tvEmail.text.toString())
                view.findNavController().navigate(R.id.nav_profile_details_to_email_change,bundle)
            }

            R.id.llProfileImage -> {
                //Toast.makeText(getActivity(), "pressed", Toast.LENGTH_SHORT).show();
                if (checkAndRequestPermissions(requireActivity())) {
                    chooseImage(requireContext())
                }else{
                    Toast.makeText(requireContext(),"Accept all permissions",Toast.LENGTH_SHORT).show()
                }
                //get the permissions we have asked for before but are not granted..
                //we will store this in a global list to access later.
            }
        }
    }

    private fun chooseImage(context: Context) {
        Toast.makeText(requireContext(),"Choose Image",Toast.LENGTH_SHORT).show()
        val optionsMenu = arrayOf<CharSequence>(
            "Take Photo",
            "Choose from Gallery",
            "Exit"
        ) // create a menuOption Array

        // create a dialog for showing the optionsMenu
        // create a dialog for showing the optionsMenu
        val builder = AlertDialog.Builder(context)
        // set the items in builder
        // set the items in builder
        builder.setItems(optionsMenu) { dialogInterface, i ->
            if (optionsMenu[i] == "Take Photo") {
                // Open the camera and get the photo
                val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(takePicture, 0)
            } else if (optionsMenu[i] == "Choose from Gallery") {
                // choose from  external storage
                val pickPhoto = Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                )
                startActivityForResult(pickPhoto, 1)
            } else if (optionsMenu[i] == "Exit") {
                dialogInterface.dismiss()
            }
        }
        builder.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //Toast.makeText(this, "onRequestPermissionsResult", Toast.LENGTH_SHORT).show();
        if (resultCode != Activity.RESULT_CANCELED) {
            when (requestCode) {
                0 -> {
                    //Toast.makeText(this, "0", Toast.LENGTH_SHORT).show();
                    val photo = data!!.extras!!["data"] as Bitmap?
                    binding.ivProfile.setImageBitmap(photo)
                    // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                    val tempUri: Uri? = getImageUri(requireActivity(), photo!!)
                    // CALL THIS METHOD TO GET THE ACTUAL PATH
                    /*File finalFile = new File(getRealPathFromURI(tempUri));
                    Log.e("finalFile==", String.valueOf(finalFile));*/
                    /*listFile = new ArrayList<>();
                    listFile.add(finalFile);*/
                    imgFile = File(getRealPathFromURI(tempUri)!!)
                    Log.e("finalFile==", imgFile.toString())
                    updateImage(imgFile!!)

                }
                1 -> {
                    val selectedImageUri = data!!.data
                    if (null != selectedImageUri) {
                        //Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
                        val path: String? = getPathFromURI(selectedImageUri)
                        Log.e("path==", path!!)
                        val bitmap = BitmapFactory.decodeFile(path)
                        binding.ivProfile.setImageBitmap(bitmap)
                        /*File finalFile2 = new File(path);
                        listFile = new ArrayList<>();
                        listFile.add(finalFile2);*/
                        imgFile = File(path)
                        Log.e("finalFile==", imgFile.toString())
                        updateImage(imgFile!!)
                    }
                }
            }
        }
    }

    private fun updateImage(imgFile: File) {
        CustomProgressDialog.showDialog(requireContext(),true)
        val user_image: MultipartBody.Part
        val surveyBody: RequestBody = RequestBody.create("*/*".toMediaTypeOrNull(), imgFile)
        user_image = MultipartBody.Part.createFormData("image", imgFile.name, surveyBody)
        val sharedPreference = context?.getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
        val uid = sharedPreference?.getString("uid", "")
        val userId: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), uid!!)

        val call: Call<SuccessModel?> = retrofitApiInterface.profileUpdate(userId,null,null,null,user_image)
        call.enqueue(object : Callback<SuccessModel?> {
            override fun onResponse(call: Call<SuccessModel?>, response: Response<SuccessModel?>) {
                CustomProgressDialog.showDialog(requireContext(),false)
                Toast.makeText(requireContext(),response.body()!!.message, Toast.LENGTH_SHORT).show()
                val edit = sharedPreference.edit()
                edit?.putString("image",imgFile.name)
                edit?.commit()
                loadProfile()
            }

            override fun onFailure(call: Call<SuccessModel?>, t: Throwable) {
                CustomProgressDialog.showDialog(requireContext(),false)
                Toast.makeText(requireContext(),"Getting some troubles", Toast.LENGTH_SHORT).show()
            }
        })

    }

    fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            inContext.contentResolver,
            inImage,
            "IMG_" + Calendar.getInstance().time,
            null
        )
        return Uri.parse(path)
    }

    fun getRealPathFromURI(uri: Uri?): String? {
        val cursor = requireActivity().contentResolver.query(uri!!, null, null, null, null)
        cursor!!.moveToFirst()
        val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
        return cursor.getString(idx)
    }


    fun getPathFromURI(contentUri: Uri?): String? {
        var res: String? = null
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = requireActivity().contentResolver.query(contentUri!!, proj, "", null, "")
        if (cursor!!.moveToFirst()) {
            val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            res = cursor.getString(column_index)
        }
        cursor.close()
        return res
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.d("RequestCode",requestCode.toString())
        when (requestCode) {
            REQUEST_ID_MULTIPLE_PERMISSIONS -> {
                if (ContextCompat.checkSelfPermission(requireActivity(),
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(requireActivity().applicationContext, "FlagUp Requires Access to Camara.", Toast.LENGTH_SHORT).show()

                } else if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ) {
                    Toast.makeText(
                        requireActivity().applicationContext,
                        "FlagUp Requires Access to Your Storage.",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    chooseImage(requireContext())
                }
            }
        }
    }


    private fun checkAndRequestPermissions(context: Context): Boolean {
        Log.d("checkAndRequestPermissions","checkAndRequestPermissions")

        val WExtstorePermission = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val cameraPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
        val listPermissionsNeeded: ArrayList<String> = ArrayList()

        Log.d("checkAndRequestPermissions","WExtstorePermission : $WExtstorePermission")
        Log.d("cameraPermission","cameraPermission : $cameraPermission")

        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA)
        }
        if (WExtstorePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (listPermissionsNeeded.isNotEmpty()) {
            Log.d("listPermissionsNeeded",listPermissionsNeeded.toString())
            ActivityCompat.requestPermissions(requireActivity(), listPermissionsNeeded.toTypedArray(), REQUEST_ID_MULTIPLE_PERMISSIONS)
            return false
        }

        return true
    }

}

