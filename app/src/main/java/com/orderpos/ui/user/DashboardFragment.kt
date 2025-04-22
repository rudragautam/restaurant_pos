package com.orderpos.ui.user

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.orderpos.R
import com.orderpos.databinding.FragmentDashboardBinding
import com.orderpos.ui.admin.adapter.OrderAdapter
import com.orderpos.ui.admin.adapter.OrderItem
import com.orderpos.ui.admin.adapter.SettingsAdapter
import com.orderpos.ui.admin.adapter.SettingsItem
import com.orderpos.viewmodal.AddMenuViewModel


class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val leftItemList: List<OrderItem> = listOf(
            OrderItem(title = "Eren Jaegar", id = 1, isSelected = false, description = "$125", navDestination = R.id.nav_reataurants_list),
            OrderItem(title = "Reiner Braunn", id = 1,  isSelected = false, description = "$125", navDestination = R.id.categoryListFragment),
            OrderItem(title = "Levi Ackerman", id = 1, isSelected = false, description = "$125", navDestination = R.id.menuItemListFragment),
            OrderItem(title = "Historia Reiss", id = 1,  isSelected = false, description = "$125", navDestination = R.id.inventoryListFragment),
            OrderItem(title = "Hanji Zoe", id = 1,  isSelected = false, description = "$125", navDestination = R.id.employeeListFragment),
            OrderItem(title = "Reiner Braunn", id = 1,  isSelected = false, description = "$125", navDestination = R.id.categoryListFragment),
            OrderItem(title = "Levi Ackerman", id = 1, isSelected = false, description = "$125", navDestination = R.id.menuItemListFragment),
            OrderItem(title = "Historia Reiss", id = 1,  isSelected = false, description = "$125", navDestination = R.id.inventoryListFragment),
            OrderItem(title = "Hanji Zoe", id = 1,  isSelected = false, description = "$125", navDestination = R.id.employeeListFragment),
            OrderItem(title = "Eren Jaegar", id = 1, isSelected = false, description = "$125", navDestination = R.id.nav_reataurants_list),
            OrderItem(title = "Reiner Braunn", id = 1,  isSelected = false, description = "$125", navDestination = R.id.categoryListFragment),
            OrderItem(title = "Levi Ackerman", id = 1, isSelected = false, description = "$125", navDestination = R.id.menuItemListFragment),
            OrderItem(title = "Historia Reiss", id = 1,  isSelected = false, description = "$125", navDestination = R.id.inventoryListFragment),
            OrderItem(title = "Hanji Zoe", id = 1,  isSelected = false, description = "$125", navDestination = R.id.employeeListFragment),
            OrderItem(title = "Reiner Braunn", id = 1,  isSelected = false, description = "$125", navDestination = R.id.categoryListFragment),
            OrderItem(title = "Levi Ackerman", id = 1, isSelected = false, description = "$125", navDestination = R.id.menuItemListFragment),
            OrderItem(title = "Historia Reiss", id = 1,  isSelected = false, description = "$125", navDestination = R.id.inventoryListFragment),
            OrderItem(title = "Hanji Zoe", id = 1,  isSelected = false, description = "$125", navDestination = R.id.employeeListFragment),
//            SettingsItem(title = "Notifications", id = 1, isSelected = false, description = "Manage notifications", navDestination = R.id.employeeListFragment),
//            SettingsItem(title = "Security", id = 1, isSelected = false, description = "Manage security settings", navDestination = R.id.nav_settings),
//            SettingsItem(title = "About Us", id = 1, isSelected = false, description = "Learn more about us", navDestination = R.id.nav_settings),

        )

        setupPieChart(binding.pieChart)
        loadPieChartData(binding.pieChart)

        binding.orderList.apply {
            layoutManager= LinearLayoutManager(requireContext())
            val cartAdapter= OrderAdapter(leftItemList)
            adapter=cartAdapter

            cartAdapter.onItemClicked = { item ->
                /*val navController = childFragmentManager.findFragmentById(R.id.nav_host_fragment_content_settings)
                    ?.findNavController()
                navController?.navigate(item.navDestination)*/
            }

            /*navController?.navigate(item.navDestination)
*/

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
            setHoleColor(Color.WHITE) // Hole color
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
            valueTextColor = Color.WHITE
        }

        val pieData = PieData(dataSet).apply {
            setValueFormatter(PercentFormatter(pieChart)) // Show % values
        }

        pieChart.data = pieData
        pieChart.invalidate() // Refresh chart
    }
}