package com.example.smartcash.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import android.widget.ArrayAdapter
import android.widget.Filter
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
        setupFilters()
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

    private fun setupFilters() = with(binding) {
        val years = resources.getStringArray(R.array.filter_years)
        actYear.setAdapter(alwaysFullAdapter(years))
        actYear.setText(years.firstOrNull() ?: "", false)
        actYear.setOnClickListener { actYear.showDropDown() }
        actYear.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) actYear.showDropDown()
        }

        val months = resources.getStringArray(R.array.filter_months)
        actMonth.setAdapter(alwaysFullAdapter(months))
        actMonth.setText(months.firstOrNull() ?: "", false)
        actMonth.setOnClickListener { actMonth.showDropDown() }
        actMonth.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) actMonth.showDropDown()
        }
    }

    private fun alwaysFullAdapter(items: Array<String>): ArrayAdapter<String> {
        return object : ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_list_item_1,
            items
        ) {
            override fun getFilter(): Filter {
                return object : Filter() {
                    override fun performFiltering(constraint: CharSequence?): FilterResults {
                        return FilterResults().apply {
                            values = items
                            count = items.size
                        }
                    }
                    override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                        notifyDataSetChanged()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

