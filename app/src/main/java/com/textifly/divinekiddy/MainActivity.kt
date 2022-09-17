package com.textifly.divinekiddy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.textifly.divinekiddy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private lateinit var navBuilder: NavOptions.Builder


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setDefaultView()
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
}