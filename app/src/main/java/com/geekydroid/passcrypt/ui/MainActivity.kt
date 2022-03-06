package com.geekydroid.passcrypt.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import com.geekydroid.passcrypt.PasscryptApp
import com.geekydroid.passcrypt.R
import com.geekydroid.passcrypt.datasources.LocalDataSource
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    @Inject
    lateinit var database: LocalDataSource
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    private lateinit var navGraph: NavGraph

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val prefs = getSharedPreferences("myPrefs", MODE_PRIVATE)
        val isFirstLaunch = prefs.getBoolean((application as PasscryptApp).FIRST_LAUNCH, true)
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        navController = navHostFragment.navController
        val graphInflater = navHostFragment.navController.navInflater
        navGraph = graphInflater.inflate(R.navigation.nav_graph)
        if (isFirstLaunch) {
            navGraph.setStartDestination(R.id.setMasterPassFragment)
        }
        navController.graph = navGraph


    }

}