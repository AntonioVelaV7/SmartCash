package com.example.smartcash.ui.expense

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.smartcash.R
import com.example.smartcash.databinding.FragmentExpenseHistoryBinding
import com.example.smartcash.model.Expense
import com.example.smartcash.ui.adapters.ExpenseHistoryAdapter
import com.example.smartcash.util.navigateHome
import com.example.smartcash.viewmodel.FinanceViewModel

class ExpenseHistoryFragment : Fragment() {

    private var _binding: FragmentExpenseHistoryBinding? = null
    private val binding get() = _binding!!

    private val financeViewModel: FinanceViewModel by activityViewModels()
    private lateinit var adapter: ExpenseHistoryAdapter
    private var expenseList: List<Expense> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExpenseHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
        setupListeners()
        observeExpenses()
    }

    private fun setupRecycler() {
        adapter = ExpenseHistoryAdapter { expense ->
            financeViewModel.deleteExpense(expense.id)
        }
        binding.rvExpenseHistory.adapter = adapter
    }

    private fun setupListeners() = with(binding) {
        btnBack.setOnClickListener { findNavController().navigateUp() }
        btnStatistics.setOnClickListener {
            findNavController().navigate(R.id.action_expenseHistoryFragment_to_statisticsFragment)
        }
        btnHome.setOnClickListener { findNavController().navigateHome() }
        etSearch.doAfterTextChanged { filterList(it?.toString().orEmpty()) }
    }

    private fun observeExpenses() {
        financeViewModel.expenses.observe(viewLifecycleOwner) { list ->
            expenseList = list
            filterList(binding.etSearch.text?.toString().orEmpty())
        }
    }

    private fun filterList(query: String) {
        val filtered = if (query.isBlank()) {
            expenseList
        } else {
            expenseList.filter {
                it.category.contains(query, ignoreCase = true) ||
                        it.description.contains(query, ignoreCase = true)
            }
        }
        adapter.submitList(filtered)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

