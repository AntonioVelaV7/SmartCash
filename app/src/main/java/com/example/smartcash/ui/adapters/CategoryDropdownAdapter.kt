package com.example.smartcash.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.smartcash.R

data class CategoryOption(
    val label: String,
    val iconRes: Int
)

class CategoryDropdownAdapter(
    context: Context,
    private val options: List<CategoryOption>
) : ArrayAdapter<CategoryOption>(context, 0, options) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_category_dropdown, parent, false)
        bind(view, options[position])
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_category_dropdown, parent, false)
        bind(view, options[position])
        return view
    }

    private fun bind(view: View, option: CategoryOption) {
        val icon = view.findViewById<ImageView>(R.id.ivCategoryIcon)
        val label = view.findViewById<TextView>(R.id.tvCategoryLabel)
        icon.setImageResource(option.iconRes)
        label.text = option.label
    }
}

