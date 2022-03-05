package com.geekydroid.passcrypt.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import com.geekydroid.passcrypt.R
import com.geekydroid.passcrypt.datasources.LocalDataSource
import com.geekydroid.passcrypt.entities.User
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment

        authenticateUser()

    }

    private fun authenticateUser() {
        CoroutineScope(IO).launch {
            navController = navHostFragment.navController
            val graphInflater = navController.navInflater
            navGraph = graphInflater.inflate(R.navigation.nav_graph)
            val user: User? = database.getUserdao().getUserForAuth()
            if (user != null && user.isMasterPassSet) {
                Log.d(TAG, "authenticateUser: if $user")
                navGraph.setStartDestination(R.id.enterMasterPasswordFragment)
            } else {
                Log.d(TAG, "authenticateUser: else")
                navGraph.setStartDestination(R.id.setMasterPassFragment)
            }
            withContext(Dispatchers.Main)
            {
                navController.graph = navGraph
            }
        }
    }
}