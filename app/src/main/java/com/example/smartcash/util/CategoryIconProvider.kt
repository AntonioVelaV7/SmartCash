package com.example.smartcash.util

import androidx.annotation.DrawableRes
import com.example.smartcash.R

object CategoryIconProvider {

    private val incomeIcons = mapOf(
        "Salario" to R.drawable.ic_category_salary,
        "Negocio" to R.drawable.ic_category_business,
        "Inversiones" to R.drawable.ic_category_investment,
        "Extras / Bonos" to R.drawable.ic_category_bonus
    )

    private val expenseIcons = mapOf(
        "Hogar" to R.drawable.ic_category_home,
        "Comida" to R.drawable.ic_category_food,
        "Transporte" to R.drawable.ic_category_bus,
        "Cuentas" to R.drawable.ic_category_bills
    )

    @DrawableRes
    fun incomeIconFor(category: String): Int =
        incomeIcons[category] ?: R.drawable.ic_money

    @DrawableRes
    fun expenseIconFor(category: String): Int =
        expenseIcons[category] ?: R.drawable.ic_expense
}

