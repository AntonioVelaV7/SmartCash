package com.example.smartcash.model

import java.time.LocalDate
import java.util.UUID

data class Income(
    val id: String = UUID.randomUUID().toString(),
    val category: String,
    val amount: Double,
    val date: LocalDate,
    val description: String = ""
)

