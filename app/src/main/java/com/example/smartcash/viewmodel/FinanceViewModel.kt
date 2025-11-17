package com.example.smartcash.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.smartcash.model.Expense
import com.example.smartcash.model.Income

class FinanceViewModel : ViewModel() {

    private val _incomes = MutableLiveData<List<Income>>(emptyList())
    val incomes: LiveData<List<Income>> = _incomes

    private val _expenses = MutableLiveData<List<Expense>>(emptyList())
    val expenses: LiveData<List<Expense>> = _expenses

    val totalIncome: LiveData<Double> = MediatorLiveData<Double>().apply {
        fun update() {
            val list = _incomes.value.orEmpty()
            value = list.sumOf { income: Income -> income.amount }
        }
        addSource(_incomes) { update() }
        value = 0.0
    }

    val totalExpenses: LiveData<Double> = MediatorLiveData<Double>().apply {
        fun update() {
            val list = _expenses.value.orEmpty()
            value = list.sumOf { expense: Expense -> expense.amount }
        }
        addSource(_expenses) { update() }
        value = 0.0
    }

    val balance: LiveData<Double> = MediatorLiveData<Double>().apply {
        fun updateBalance() {
            val income = _incomes.value?.sumOf { it.amount } ?: 0.0
            val expense = _expenses.value?.sumOf { it.amount } ?: 0.0
            value = income - expense
        }

        addSource(_incomes) { updateBalance() }
        addSource(_expenses) { updateBalance() }
    }

    val expenseByCategory: LiveData<Map<String, Double>> = MediatorLiveData<Map<String, Double>>().apply {
        fun updateTotals() {
            val data = _expenses.value.orEmpty()
                .groupBy { it.category }
                .mapValues { entry -> entry.value.sumOf { it.amount } }
            value = data
        }
        addSource(_expenses) { updateTotals() }
        value = emptyMap()
    }

    fun addIncome(income: Income) {
        _incomes.value = _incomes.value.orEmpty() + income
    }

    fun addExpense(expense: Expense) {
        _expenses.value = _expenses.value.orEmpty() + expense
    }

    fun deleteIncome(id: String) {
        _incomes.value = _incomes.value.orEmpty().filterNot { it.id == id }
    }

    fun deleteExpense(id: String) {
        _expenses.value = _expenses.value.orEmpty().filterNot { it.id == id }
    }

    fun resetAll() {
        _incomes.value = emptyList()
        _expenses.value = emptyList()
    }
}

