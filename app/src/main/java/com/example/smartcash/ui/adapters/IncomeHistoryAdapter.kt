package com.example.smartcash.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.smartcash.R
import com.example.smartcash.databinding.ItemHistoryEntryBinding
import com.example.smartcash.model.Income
import com.example.smartcash.util.CategoryIconProvider
import com.example.smartcash.util.Formatters

class IncomeHistoryAdapter(
    private val onDelete: (Income) -> Unit
) : ListAdapter<Income, IncomeHistoryAdapter.IncomeViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IncomeViewHolder {
        val binding = ItemHistoryEntryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return IncomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IncomeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class IncomeViewHolder(
        private val binding: ItemHistoryEntryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(income: Income) = with(binding) {
            tvCategory.text = income.category
            tvDate.text = Formatters.formatDate(income.date)
            tvAmount.text = Formatters.formatCurrency(income.amount)
            tvAmount.setTextColor(root.context.getColor(R.color.accent_positive))
            ivCategoryIcon.setImageResource(
                CategoryIconProvider.incomeIconFor(income.category)
            )
            btnDelete.isVisible = true
            btnDelete.setOnClickListener { onDelete(income) }
        }
    }

    private object DiffCallback : DiffUtil.ItemCallback<Income>() {
        override fun areItemsTheSame(oldItem: Income, newItem: Income): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Income, newItem: Income): Boolean =
            oldItem == newItem
    }
}

