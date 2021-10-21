package com.example.ashtanga1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.ashtanga1.adapter.CardAdapter
import com.example.ashtanga1.data.DataSource

class MainActivity : AppCompatActivity(R.layout.main_activity) {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val navHostFragment = supportFragmentManager
//            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
//            navController = navHostFragment.navController
//        setupActionBarWithNavController(navController)
    }
}