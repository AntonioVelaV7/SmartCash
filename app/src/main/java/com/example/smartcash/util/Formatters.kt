package com.example.smartcash.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

object Formatters {
    private val peruvianLocale = Locale("es", "PE")
    private val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    fun formatCurrency(amount: Double): String {
        return "S/ " + String.format(peruvianLocale, "%,.2f", amount)
    }

    fun formatDate(date: LocalDate): String = date.format(dateFormatter)
}

