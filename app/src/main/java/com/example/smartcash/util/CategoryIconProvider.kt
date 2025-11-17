package com.example.smartcash.util

import androidx.annotation.DrawableRes
import com.example.smartcash.R

object CategoryIconProvider {

    private val sharedIcons = mapOf(
        "Hogar" to R.drawable.ic_category_home,
        "Comida" to R.drawable.ic_category_food,
        "Transporte" to R.drawable.ic_category_bus,
        "Cuentas" to R.drawable.ic_category_bills
    )

    @DrawableRes
    fun incomeIconFor(category: String): Int =
        sharedIcons[category] ?: R.drawable.ic_money

    @DrawableRes
    fun expenseIconFor(category: String): Int =
        sharedIcons[category] ?: R.drawable.ic_expense
}

