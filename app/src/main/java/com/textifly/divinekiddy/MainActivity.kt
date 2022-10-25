package com.textifly.divinekiddy

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.textifly.divinekiddy.CustomDialog.CustomProgressDialog
import com.textifly.divinekiddy.Drawer.Adapter.DrawerAdapter
import com.textifly.divinekiddy.Drawer.Model.DrawerModel
import com.textifly.divinekiddy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private lateinit var navBuilder: NavOptions.Builder

    var modelList: ArrayList<DrawerModel>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setDefaultView()

        setDrawerMenu()
    }

    private fun setDrawerMenu() {
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.rvMenu.layoutManager = layoutManager

        modelList = ArrayList()

        // if not logged in
        modelList!!.add(DrawerModel("Home"))
        modelList!!.add(DrawerModel("Profile"))
        modelList!!.add(DrawerModel("My Cart"))
        modelList!!.add(DrawerModel("My Orders"))
        val sharedPreference = getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
        if(sharedPreference.contains("uid")){
            modelList!!.add(DrawerModel("Logout"))
        }else{
            modelList!!.add(DrawerModel("Login"))
        }

        var adapter = DrawerAdapter(modelList!!)
        binding.rvMenu.adapter = adapter

        adapter.setListenerDrawerMenu(object : OnDrawerMenuListener {
            override fun onDrawerMenuClick(pos: Int) {
                var bundle = Bundle()
                when (pos){
                    0 -> {

                        navController.navigate(R.id.navigation_discover, bundle, navBuilder.build())
                        //This will open
                        openDrawer()
                    }
                    1 -> {
                        if(sharedPreference.contains("uid")) {
                            navController.navigate(
                                R.id.navigation_profile_details,
                                bundle,
                                navBuilder.build()
                            )
                            //This will open
                            openDrawer()
                        }else{
                            navController.navigate(R.id.navigation_signin_mobile, bundle, navBuilder.build())
                            openDrawer()
                        }
                    }
                    2 -> {
                        if(sharedPreference.contains("uid")) {
                            navController.navigate(
                                R.id.navigation_cart,
                                bundle,
                                navBuilder.build()
                            )
                            //This will open
                            openDrawer()
                        }else{
                            navController.navigate(R.id.navigation_signin_mobile, bundle, navBuilder.build())
                            openDrawer()
                        }
                    }
                    3 -> {
                        if(sharedPreference.contains("uid")) {
                            navController.navigate(
                                R.id.navigation_order_list,
                                bundle,
                                navBuilder.build()
                            )
                            //This will open
                            openDrawer()
                        }else{
                            navController.navigate(R.id.navigation_signin_mobile, bundle, navBuilder.build())
                            openDrawer()
                        }
                    }
                    4 -> {
                        if(sharedPreference.contains("uid")) {
                            CustomProgressDialog.showDialog(this@MainActivity,true)
                            //val sharedPreference = this@MainActivity.getSharedPreferences("PREFERENCE",Context.MODE_PRIVATE)
                            if(sharedPreference.contains("uid")){
                                val editor = sharedPreference.edit()
                                editor.clear()
                                editor.commit()
                                CustomProgressDialog.showDialog(this@MainActivity,false)
                                Toast.makeText(this@MainActivity,"Logout Successful", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this@MainActivity, MainActivity::class.java))
                                this@MainActivity.finish()
                            }else{
                                Toast.makeText(this@MainActivity,"Getting some troubles", Toast.LENGTH_SHORT).show()
                                CustomProgressDialog.showDialog(this@MainActivity,false)
                            }
                            //This will open
                            openDrawer()
                        }else{
                            navController.navigate(R.id.navigation_signin_mobile, bundle, navBuilder.build())
                            openDrawer()
                        }
                    }
                }
            }
        })

    }

    fun openDrawer() {
        if (!binding.drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            binding.drawerLayout.openDrawer(Gravity.LEFT)
        } else {
            binding.drawerLayout.closeDrawer(Gravity.LEFT)
        }
    }

    private fun setDefaultView() {
        appBarConfiguration = AppBarConfiguration.Builder(R.id.navigation_discover).build()
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(binding.appBarMain.bottomNav, navController)
        //NavigationUI.setupWithNavController(binding.navView, navController)
        navBuilder =  NavOptions.Builder()
        navBuilder.setEnterAnim(R.anim.fade_in_animation)
            .setExitAnim(R.anim.fade_out_animation)
            .setPopEnterAnim(R.anim.fade_in_animation)
            .setPopExitAnim(R.anim.fade_out_animation)
    }

    interface OnDrawerMenuListener {
        fun onDrawerMenuClick(pos: Int)
    }
}