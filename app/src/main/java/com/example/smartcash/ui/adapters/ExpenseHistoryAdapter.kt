package com.example.smartcash.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.smartcash.R
import com.example.smartcash.databinding.ItemHistoryEntryBinding
import com.example.smartcash.model.Expense
import com.example.smartcash.util.CategoryIconProvider
import com.example.smartcash.util.Formatters

class ExpenseHistoryAdapter(
    private val onDelete: (Expense) -> Unit
) : ListAdapter<Expense, ExpenseHistoryAdapter.ExpenseViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val binding = ItemHistoryEntryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ExpenseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ExpenseViewHolder(
        private val binding: ItemHistoryEntryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(expense: Expense) = with(binding) {
            tvCategory.text = expense.category
            tvDate.text = Formatters.formatDate(expense.date)
            tvAmount.text = Formatters.formatCurrency(expense.amount)
            tvAmount.setTextColor(root.context.getColor(R.color.accent_negative))
            ivCategoryIcon.setImageResource(
                CategoryIconProvider.expenseIconFor(expense.category)
            )
            btnDelete.isVisible = true
            btnDelete.setOnClickListener { onDelete(expense) }
        }
    }

    private object DiffCallback : DiffUtil.ItemCallback<Expense>() {
        override fun areItemsTheSame(oldItem: Expense, newItem: Expense): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Expense, newItem: Expense): Boolean =
            oldItem == newItem
    }
}

