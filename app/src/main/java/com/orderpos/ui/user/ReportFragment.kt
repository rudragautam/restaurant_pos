package com.orderpos.ui.user

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.CombinedChart
import com.github.mikephil.charting.charts.HorizontalBarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.CombinedData
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.PercentFormatter
import com.google.android.material.button.MaterialButton
import com.orderpos.R
import com.orderpos.databinding.FragmentDashboardBinding
import com.orderpos.databinding.FragmentReportBinding


class ReportFragment : Fragment() {

    private var _binding: FragmentReportBinding? = null
    private val binding get() = _binding!!
    private var currentFilter = FilterType.WEEKLY
    enum class FilterType {
        TODAY, WEEKLY, MONTHLY, YEARLY
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentReportBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupPieChart(binding.pieChart)
        loadPieChartData(binding.pieChart)

        setupCombinedChart(binding.pieChart2)
        loadCombinedChartData(binding.pieChart2)


        setupHorizontalBarChart(binding.rightLayoutpieChart)
        loadHorizontalBarChartData(binding.rightLayoutpieChart)

        setupBarChart(binding.rightLayoutpieChart2)
        loadBarChartData(binding.rightLayoutpieChart2)

        binding.dropdownMenuTwo.setOnClickListener { view ->
            showFilterMenu(binding.dropdownMenuTwo)
        }

        binding.dropdownMenuTwo2.setOnClickListener { view ->
            showFilterMenu(binding.dropdownMenuTwo2)
        }

        binding.rightLayoutdropdownMenuTwo.setOnClickListener { view ->
            showFilterMenu(binding.rightLayoutdropdownMenuTwo)
        }

        binding.rightLayoutdropdownMenuTwo2.setOnClickListener { view ->
            showFilterMenu(binding.rightLayoutdropdownMenuTwo2)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupPieChart(pieChart: PieChart) {
        pieChart.apply {
            setUsePercentValues(true) // Show values in %
            description.isEnabled = false // Hide description
            isDrawHoleEnabled = true // Show hole in the middle
            holeRadius = 50f // Size of the hole (in %)
            setHoleColor(Color.BLACK) // Hole color
            setDrawEntryLabels(true) // Show labels on slices
            setEntryLabelColor(Color.BLACK) // Label text color
            setEntryLabelTextSize(12f) // Label text size
            animateY(1000) // Animation
        }
    }

    private fun loadPieChartData(pieChart: PieChart) {
        val entries = listOf(
            PieEntry(25f, "Food"),
            PieEntry(35f, "Transport"),
            PieEntry(20f, "Entertainment"),
            PieEntry(20f, "Bills")
        )

        val colors = listOf(
            Color.parseColor("#FFA726"), // Orange
            Color.parseColor("#66BB6A"), // Green
            Color.parseColor("#42A5F5"), // Blue
            Color.parseColor("#EF5350")  // Red
        )

        val dataSet = PieDataSet(entries, "Expense Categories").apply {
            setColors(colors)
            valueTextSize = 12f
            valueTextColor = Color.BLACK
        }

        val pieData = PieData(dataSet).apply {
            setValueFormatter(PercentFormatter(pieChart)) // Show % values
        }

        pieChart.data = pieData
        pieChart.invalidate() // Refresh chart
    }

    private fun setupHorizontalBarChart(chart: HorizontalBarChart) {
        chart.apply {
            description.isEnabled = false // Hide description
            setDrawValueAboveBar(true) // Display values above bars
            setMaxVisibleValueCount(5) // Show only 5 bars at once
            setPinchZoom(false) // Disable zoom
            setDrawBarShadow(false) // Disable bar shadows
            setDrawGridBackground(false) // Disable grid

            // Axis customization
            xAxis.apply {
                setDrawGridLines(false) // Hide vertical grid lines
                position = com.github.mikephil.charting.components.XAxis.XAxisPosition.BOTTOM
                granularity = 1f // Prevent decimal values
            }

            axisLeft.apply {
                setDrawGridLines(false) // Hide horizontal grid lines
                axisMinimum = 0f // Start Y-axis from 0
            }

            axisRight.isEnabled = false // Disable right axis
            legend.isEnabled = false // Hide legend
            animateY(1000) // Animation
        }
    }

    private fun loadHorizontalBarChartData(chart: HorizontalBarChart) {
        // Sample data (X = index, Y = value)
        val entries = listOf(
            BarEntry(0f, 20f), // "Product A"
            BarEntry(1f, 35f), // "Product B"
            BarEntry(2f, 15f), // "Product C"
            BarEntry(3f, 50f)  // "Product D"
        )

        val labels = listOf("Product A", "Product B", "Product C", "Product D") // X-axis labels

        val dataSet = BarDataSet(entries, "Sales").apply {
            color = Color.parseColor("#2E86AB") // Bar color
            valueTextColor = Color.BLACK // Value text color
            valueTextSize = 12f
        }

        val barData = BarData(dataSet).apply {
            barWidth = 0.5f // Set bar width (0.0f to 1.0f)
        }

        chart.apply {
            data = barData
            xAxis.valueFormatter = IndexAxisValueFormatter(labels) // Set X-axis labels
            invalidate() // Refresh chart
        }
    }

    private fun setupBarChart(chart: BarChart) {
        chart.apply {
            description.isEnabled = false
            setDrawGridBackground(false)
            setDrawBarShadow(false)
            setPinchZoom(false)

            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                granularity = 1f
                setDrawGridLines(false)
                valueFormatter = IndexAxisValueFormatter(listOf("Jan", "Feb", "Mar", "Apr"))
            }

            axisLeft.apply {
                axisMinimum = 0f
                setDrawGridLines(true)
            }

            axisRight.isEnabled = false
            animateY(1000)
        }
    }

    private fun loadBarChartData(chart: BarChart) {
        val entries = listOf(
            BarEntry(0f, 100f),
            BarEntry(1f, 150f),
            BarEntry(2f, 80f),
            BarEntry(3f, 200f)
        )

        val dataSet = BarDataSet(entries, "Monthly Sales").apply {
            color = Color.parseColor("#FF5722")
            valueTextColor = Color.BLACK
            valueTextSize = 12f
        }

        chart.data = BarData(dataSet).apply {
            barWidth = 0.5f
        }
        chart.invalidate()
    }


    private fun setupCombinedChart(chart: CombinedChart) {
        chart.apply {
            description.isEnabled = false
            setDrawGridBackground(false)
            drawOrder = listOf(
                CombinedChart.DrawOrder.BAR,
                CombinedChart.DrawOrder.LINE
            ).toTypedArray()

            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                granularity = 1f
                valueFormatter = IndexAxisValueFormatter(listOf("Q1", "Q2", "Q3", "Q4"))
            }

            axisLeft.apply {
                axisMinimum = 0f
            }

            axisRight.isEnabled = false
            animateY(1000)
        }
    }

    private fun loadCombinedChartData(chart: CombinedChart) {
        // Bar Data
        val barEntries = listOf(
            BarEntry(0f, 100f),
            BarEntry(1f, 150f),
            BarEntry(2f, 80f),
            BarEntry(3f, 200f)
        )
        val barDataSet = BarDataSet(barEntries, "Revenue").apply {
            color = Color.parseColor("#4CAF50")
            valueTextColor = Color.BLACK
        }

        // Line Data
        val lineEntries = listOf(
            Entry(0f, 50f),
            Entry(1f, 120f),
            Entry(2f, 60f),
            Entry(3f, 180f)
        )
        val lineDataSet = LineDataSet(lineEntries, "Profit Margin").apply {
            color = Color.parseColor("#2196F3")
            lineWidth = 2.5f
            setCircleColor(Color.BLUE)
            circleRadius = 5f
            valueTextColor = Color.BLACK
        }

        // Combine Data
        val combinedData = CombinedData().apply {
            setData(BarData(barDataSet).apply { barWidth = 0.4f })
            setData(LineData(lineDataSet))
        }

        chart.data = combinedData
        chart.invalidate()
    }

    private fun showFilterMenu(anchor: MaterialButton) {
        val popup = PopupMenu(requireActivity(), anchor).apply {
            menuInflater.inflate(R.menu.chart_filter_menu, menu)

            setOnMenuItemClickListener { item ->
                currentFilter = when (item.itemId) {
                    R.id.filter_today -> FilterType.TODAY
                    R.id.filter_weekly -> FilterType.WEEKLY
                    R.id.filter_monthly -> FilterType.MONTHLY
                    R.id.filter_yearly -> FilterType.YEARLY
                    else -> currentFilter
                }
                anchor.text = item.title
//                loadData(currentFilter)
                true
            }
            show()
        }
    }

}