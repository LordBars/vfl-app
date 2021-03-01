package com.avalon.vflapp.ui.hub

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.avalon.vflapp.R
import com.avalon.vflapp.databinding.ActivityHubBinding

class HubActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityHubBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val drawer = binding.hubDrawer
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.QRFragment, R.id.noticeFragment, R.id.questionFragment), drawer)

        val navView = binding.hubNavView
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.hub_nav_host_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        navView.setupWithNavController(navController)

        val toolbar = binding.hubToolbar
        val collapsingToolbar = binding.hubCollapsingToolbarLayout
        collapsingToolbar.setupWithNavController(toolbar, navController, appBarConfiguration)

     //   setupActionBarWithNavController(navController, appBarConfiguration)
    }
}