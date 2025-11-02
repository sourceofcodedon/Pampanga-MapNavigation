package com.pampang.nav.screens.buyer

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.pampang.nav.R
import com.pampang.nav.databinding.ActivityBuyerMainBinding
import com.pampang.nav.viewmodels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BuyerMainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityBuyerMainBinding
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
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_buyer_main)
        mBinding.lifecycleOwner = this
    }

    private fun initBottomNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.frame_layout) as NavHostFragment
        navController = navHostFragment.navController

        mBinding.bottomNavigation.setupWithNavController(navController)

        mBinding.bottomNavigation.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.main -> {
                    navController.navigate(R.id.mainFragment)
                }

                R.id.profile -> {
                    navController.navigate(R.id.profileFragment)
                }
            }

            return@setOnItemSelectedListener true
        }
    }
}
