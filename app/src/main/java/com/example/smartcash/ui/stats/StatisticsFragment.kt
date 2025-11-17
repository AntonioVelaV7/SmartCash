package com.example.smartcash.ui.stats

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.smartcash.databinding.FragmentStatisticsBinding
import com.example.smartcash.util.navigateHome
import com.example.smartcash.viewmodel.FinanceViewModel
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class StatisticsFragment : Fragment() {

    private var _binding: FragmentStatisticsBinding? = null
    private val binding get() = _binding!!

    private val financeViewModel: FinanceViewModel by activityViewModels()

    private val palette = listOf(
        Color.parseColor("#5D5D5D"),
        Color.parseColor("#8E8E93"),
        Color.parseColor("#BFBFC4"),
        Color.parseColor("#D6D6D8"),
        Color.parseColor("#A0A0A5")
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupChart()
        setupButtons()
        observeData()
    }

    private fun setupChart() = with(binding.pieChart) {
        description.isEnabled = false
        isRotationEnabled = false
        setUsePercentValues(true)
        legend.orientation = Legend.LegendOrientation.HORIZONTAL
        legend.isEnabled = false
        holeRadius = 60f
        setHoleColor(Color.TRANSPARENT)
        setEntryLabelColor(Color.BLACK)
        setDrawEntryLabels(false)
    }

    private fun setupButtons() = with(binding) {
        btnBack.setOnClickListener { findNavController().navigateUp() }
        btnHome.setOnClickListener { findNavController().navigateHome() }
    }

    private fun observeData() {
        financeViewModel.expenseByCategory.observe(viewLifecycleOwner) { map ->
            renderChart(map)
        }
    }

    private fun renderChart(values: Map<String, Double>) {
        val entries = values.entries
            .filter { it.value > 0 }
            .map { PieEntry(it.value.toFloat(), it.key) }

        val dataEntries = if (entries.isEmpty()) {
            listOf(PieEntry(1f, "Sin datos"))
        } else entries

        val dataSet = PieDataSet(dataEntries, "")
        dataSet.sliceSpace = 3f
        dataSet.selectionShift = 5f
        dataSet.colors = if (entries.isEmpty()) {
            listOf(Color.LTGRAY)
        } else {
            palette.takeIf { it.size >= dataEntries.size }?.subList(0, dataEntries.size)
                ?: palette
        }

        val data = PieData(dataSet)
        data.setDrawValues(true)
        data.setValueTextSize(12f)
        data.setValueTextColor(Color.WHITE)

        binding.pieChart.data = data
        binding.pieChart.invalidate()

        renderLegend(dataEntries, dataSet.colors)
    }

    private fun renderLegend(entries: List<PieEntry>, colors: List<Int>) {
        binding.legendContainer.removeAllViews()
        entries.forEachIndexed { index, entry ->
            val row = LinearLayout(requireContext()).apply {
                orientation = LinearLayout.HORIZONTAL
                setPadding(0, 8, 0, 8)
            }

            View(requireContext()).apply {
                layoutParams = LinearLayout.LayoutParams(24, 24).also {
                    it.marginEnd = 12
                }
                setBackgroundColor(colors[index % colors.size])
                row.addView(this)
            }

            val label = TextView(requireContext()).apply {
                text = entry.label
                setTextColor(Color.DKGRAY)
            }
            row.addView(label)

            binding.legendContainer.addView(row)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

