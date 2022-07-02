package com.hsbc.hsbcdemo

import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.gyf.immersionbar.ImmersionBar
import com.gyf.immersionbar.ktx.hideStatusBar
import com.gyf.immersionbar.ktx.immersionBar
import com.hsbc.hsbcdemo.base.BaseActivity
import com.hsbc.hsbcdemo.databinding.ActivityMainBinding
import com.hsbc.hsbcdemo.ui.login.LoginActivity
import com.hsbc.hsbcdemo.utils.MMKVUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ImmersionBar.with(this)
            .statusBarDarkFont(true)
            .keyboardEnable(true)
            .init()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)

        if(MMKVUtils.get("isLogin",false)==null || !(MMKVUtils.get("isLogin",false) as Boolean)){
           // navController.navigate(R.id.action_mainFragment_to_loginFragment,null);
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
        }
    }
}