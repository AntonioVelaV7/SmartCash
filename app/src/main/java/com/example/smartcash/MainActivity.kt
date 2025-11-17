package com.example.smartcash

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.fragment.NavHostFragment
import com.example.smartcash.databinding.ActivityMainBinding
import com.example.smartcash.viewmodel.AuthViewModel
import com.example.smartcash.viewmodel.FinanceViewModel

class MainActivity : AppCompatActivity(), DrawerHost {

    private lateinit var binding: ActivityMainBinding

    private val authViewModel: AuthViewModel by viewModels()
    private val financeViewModel: FinanceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupDrawerNavigation()
    }

    private fun setupDrawerNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        binding.navigationView.setNavigationItemSelectedListener { item ->
            if (item.itemId == R.id.menu_logout) {
                authViewModel.logout()
                financeViewModel.resetAll()
                navController.navigate(R.id.loginFragment)
            }
            binding.drawerLayout.closeDrawer(GravityCompat.END)
            true
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        return navHostFragment.navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun openDrawer() {
        binding.drawerLayout.openDrawer(GravityCompat.END)
    }
}