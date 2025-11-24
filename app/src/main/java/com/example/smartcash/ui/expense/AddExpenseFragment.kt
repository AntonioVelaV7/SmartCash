package com.example.smartcash.ui.expense

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.smartcash.R
import com.example.smartcash.databinding.FragmentAddExpenseBinding
import com.example.smartcash.model.Expense
import com.example.smartcash.util.Formatters
import com.example.smartcash.ui.adapters.CategoryDropdownAdapter
import com.example.smartcash.ui.adapters.CategoryOption
import com.example.smartcash.util.navigateHome
import com.example.smartcash.viewmodel.FinanceViewModel
import com.google.android.material.snackbar.Snackbar
import java.time.LocalDate
import java.util.Calendar

class AddExpenseFragment : Fragment() {

    private var _binding: FragmentAddExpenseBinding? = null
    private val binding get() = _binding!!

    private val financeViewModel: FinanceViewModel by activityViewModels()
    private var selectedDate: LocalDate? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddExpenseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCategoryInput()
        setupInteractions()
    }

    private fun setupCategoryInput() = with(binding) {
        val options = listOf(
            CategoryOption("Hogar", R.drawable.ic_category_home),
            CategoryOption("Comida", R.drawable.ic_category_food),
            CategoryOption("Transporte", R.drawable.ic_category_bus),
            CategoryOption("Cuentas", R.drawable.ic_category_bills)
        )
        val adapter = CategoryDropdownAdapter(requireContext(), options)
        actCategory.setAdapter(adapter)
        actCategory.setOnItemClickListener { _, _, position, _ ->
            actCategory.setText(options[position].label, false)
        }
        actCategory.keyListener = null
        actCategory.setOnClickListener { actCategory.showDropDown() }
        actCategory.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) actCategory.showDropDown()
        }
    }

    private fun setupInteractions() = with(binding) {
        btnBack.setOnClickListener { findNavController().navigateUp() }
        btnGoToHistory.setOnClickListener {
            findNavController().navigate(R.id.action_addExpenseFragment_to_expenseHistoryFragment)
        }
        homeFooter.btnHome.setOnClickListener { findNavController().navigateHome() }

        etDate.setOnClickListener { showDatePicker() }
        tilDate.setEndIconOnClickListener { showDatePicker() }

        btnSaveExpense.setOnClickListener { saveExpense() }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
                binding.etDate.setText(Formatters.formatDate(selectedDate!!))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun saveExpense() = with(binding) {
        val category = actCategory.text?.toString().orEmpty()
        val amount = etAmount.text?.toString()?.toDoubleOrNull()
        val date = selectedDate

        when {
            category.isBlank() || amount == null || date == null -> {
                Snackbar.make(root, R.string.error_required_fields, Snackbar.LENGTH_SHORT).show()
            }
            amount <= 0 -> {
                tilAmount.error = getString(R.string.error_amount_positive)
            }
            else -> {
                tilAmount.error = null
                financeViewModel.addExpense(
                    Expense(
                        category = category,
                        amount = amount,
                        date = date,
                        description = ""
                    )
                )
                Snackbar.make(root, R.string.success_expense_added, Snackbar.LENGTH_SHORT).show()
                clearForm()
            }
        }
    }

    private fun clearForm() = with(binding) {
        actCategory.text?.clear()
        etAmount.text?.clear()
        etDate.setText("")
        selectedDate = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

