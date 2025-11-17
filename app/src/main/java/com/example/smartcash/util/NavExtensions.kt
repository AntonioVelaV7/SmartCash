package com.example.smartcash.util

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.example.smartcash.R

fun NavController.navigateHome() {
    val options = NavOptions.Builder()
        .setPopUpTo(R.id.dashboardFragment, false)
        .setLaunchSingleTop(true)
        .build()
    navigate(R.id.dashboardFragment, null, options)
}

