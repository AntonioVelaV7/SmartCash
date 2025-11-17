package com.example.smartcash.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.smartcash.R
import com.example.smartcash.databinding.FragmentDashboardBinding
import com.example.smartcash.DrawerHost
import com.example.smartcash.util.Formatters
import com.example.smartcash.viewmodel.FinanceViewModel

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val financeViewModel: FinanceViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupButtons()
    }

    private fun setupObservers() = with(binding) {
        financeViewModel.totalIncome.observe(viewLifecycleOwner) { amount ->
            tvIncomeAmount.text = Formatters.formatCurrency(amount)
        }
        financeViewModel.totalExpenses.observe(viewLifecycleOwner) { amount ->
            tvExpenseAmount.text = Formatters.formatCurrency(amount)
        }
        financeViewModel.balance.observe(viewLifecycleOwner) { amount ->
            tvBalanceAmount.text = Formatters.formatCurrency(amount)
        }
    }

    private fun setupButtons() = with(binding) {
        btnAddIncome.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_addIncomeFragment)
        }
        btnIncomeHistory.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_incomeHistoryFragment)
        }
        btnAddExpense.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_addExpenseFragment)
        }
        btnExpenseHistory.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_expenseHistoryFragment)
        }
        btnStatistics.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_statisticsFragment)
        }
        btnSettings.setOnClickListener {
            (requireActivity() as? DrawerHost)?.openDrawer()
        }
        btnHome.isEnabled = false
        btnHome.alpha = 0.4f
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

