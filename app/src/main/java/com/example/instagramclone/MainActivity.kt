package com.example.instagramclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.instagramclone.databinding.ActivityMainBinding
import com.example.instagramclone.utils.Constant
import com.example.instagramclone.utils.PrefManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding : ActivityMainBinding ?= null
    private val binding get() = _binding!!
    lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.fragmentContainerView
        ) as NavHostFragment
        navController = navHostFragment.navController
        val navGraph = navController.navInflater.inflate(R.navigation.my_nav)
        val isLoggedIn = PrefManager.getBoolean(
            Constant.IS_LOGIN,false
        )
        if (isLoggedIn){
            navGraph.setStartDestination(R.id.homeFragment2)
        }
        if (!isLoggedIn){
            navGraph.setStartDestination(R.id.loginFragment)
        }
        navController.graph = navGraph
        binding.bottomNavigationView.setupWithNavController(navController)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment2,
                R.id.profileFragment
            )
        )
        setCurrentDestinationListener()

    }
    private fun setCurrentDestinationListener(){
        navController.addOnDestinationChangedListener{_,destination,_->
            when(destination.id){

                    R.id.homeFragment2,
                    R.id.profileFragment,
                    ->{
                        binding.bottomNavigationView.isVisible = true
                    }
                else -> {
                    binding.bottomNavigationView.isVisible = false
                }
            }
        }
    }

}