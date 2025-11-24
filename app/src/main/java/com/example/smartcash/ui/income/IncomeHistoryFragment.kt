package com.example.smartcash.ui.income

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.smartcash.databinding.FragmentIncomeHistoryBinding
import com.example.smartcash.model.Income
import com.example.smartcash.ui.adapters.IncomeHistoryAdapter
import com.example.smartcash.util.navigateHome
import com.example.smartcash.viewmodel.FinanceViewModel

class IncomeHistoryFragment : Fragment() {

    private var _binding: FragmentIncomeHistoryBinding? = null
    private val binding get() = _binding!!

    private val financeViewModel: FinanceViewModel by activityViewModels()
    private lateinit var adapter: IncomeHistoryAdapter
    private var incomeList: List<Income> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIncomeHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
        setupListeners()
        observeIncomes()
    }

    private fun setupRecycler() {
        adapter = IncomeHistoryAdapter { income ->
            financeViewModel.deleteIncome(income.id)
        }
        binding.rvIncomeHistory.adapter = adapter
    }

    private fun setupListeners() = with(binding) {
        btnBack.setOnClickListener { findNavController().navigateUp() }
        homeFooter.btnHome.setOnClickListener { findNavController().navigateHome() }
        etSearch.doAfterTextChanged { filterList(it?.toString().orEmpty()) }
    }

    private fun observeIncomes() {
        financeViewModel.incomes.observe(viewLifecycleOwner) { list ->
            incomeList = list
            filterList(binding.etSearch.text?.toString().orEmpty())
        }
    }

    private fun filterList(query: String) {
        val filtered = if (query.isBlank()) {
            incomeList
        } else {
            incomeList.filter {
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

