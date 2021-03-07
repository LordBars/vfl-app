package com.avalon.vflapp.ui.hub

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.avalon.vflapp.R
import com.avalon.vflapp.databinding.ActivityHubBinding
import com.avalon.vflapp.util.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class HubActivity : AppCompatActivity() {

    private val viewModel: HubViewModel by viewModels()

    private lateinit var binding: ActivityHubBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHubBinding.inflate(layoutInflater)
        setContentView(binding.root)

        headerViewRole = binding.hubNavView.getHeaderView(0).findViewById<TextView>(R.id.hub_nav_head_role)

        val drawer = binding.hubDrawer
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.QRFragment, R.id.noticeFragment, R.id.questionFragment), drawer)

        val navView = binding.hubNavView
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.hub_nav_host_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        navView.setupWithNavController(navController)

        val toolbar = binding.hubToolbar
        val collapsingToolbar = binding.hubCollapsingToolbarLayout
        collapsingToolbar.setupWithNavController(toolbar, navController, appBarConfiguration)

        subscribeObservers()
        viewModel.getRole()

        //   setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private lateinit var headerViewRole: TextView

    @SuppressLint("SetTextI18n")
    private fun subscribeObservers() {

        viewModel.dataStateRole.observe(this) { dataState ->
            when(dataState) {
                is DataState.Success -> {
                    val role = dataState.data
                    headerViewRole.text = "Rol: $role"
                    if (role == "admin" || role == "superadmin") {
                        binding.hubNavView.menu.getItem(adminIndex).isEnabled = true
                    }
                }

                is DataState.Error, DataState.Cancel()-> {
                    headerViewRole.text = resources.getString(R.string.not_found_role)
                }
            }
        }
    }

    companion object {
        const val adminIndex = 3
    }
}