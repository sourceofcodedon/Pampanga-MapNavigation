package com.pampang.nav.screens.seller

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.pampang.nav.R
import com.pampang.nav.databinding.ActivitySellerMainBinding
import com.pampang.nav.viewmodels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SellerMainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivitySellerMainBinding
    private val authViewModel: AuthViewModel by viewModels()
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initConfig()
    }

    private fun initConfig() {
        initBinding()
        initBottomNavigation()
    }

    private fun initBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_seller_main)
        mBinding.lifecycleOwner = this
    }

    private fun initBottomNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.frame_layout) as NavHostFragment
        navController = navHostFragment.navController

        mBinding.bottomNavigation.setupWithNavController(navController)
    }
}