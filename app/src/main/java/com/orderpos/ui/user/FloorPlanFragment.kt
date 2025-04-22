package com.orderpos.ui.user

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.orderpos.R
import com.orderpos.databinding.FragmentDashboardBinding
import com.orderpos.databinding.FragmentFloorPlanBinding
import com.orderpos.ui.admin.adapter.ReservationAdapter
import com.orderpos.viewmodal.SeatReservationViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.GregorianCalendar
import java.util.Locale


class FloorPlanFragment : Fragment() {

    private var _binding: FragmentFloorPlanBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ReservationAdapter
    private val viewModel: SeatReservationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFloorPlanBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupUI()
        setupObservers()
//        viewModel.addReservation()
//        viewModel.loadReservations()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun setupUI() {
        // Setup toolbar
        binding.toolbar.setNavigationOnClickListener {
//            findNavController().navigateUp()
        }

        // Setup date picker
        binding.dateEditText.setOnClickListener {
            showDatePicker()
        }

        // Setup filter button
        binding.filterButton.setOnClickListener {
            viewModel.filterByDate(binding.dateEditText.text.toString())
        }

        // Setup status filter chips
        binding.statusFilterGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            val selectedStatus = when (checkedIds.firstOrNull()) {
                R.id.pendingChip -> "pending"
                R.id.confirmedChip -> "confirmed"
                R.id.cancelledChip -> "cancelled"
                else -> null
            }
            viewModel.filterByStatus(selectedStatus)
        }

        // Setup adapter
        adapter = ReservationAdapter { reservation, action ->
            when (action) {
                ReservationAction.CONFIRM -> viewModel.confirmReservation(reservation.id)
                ReservationAction.CANCEL -> viewModel.cancelReservation(reservation.id)
            }
        }
        binding.reservationsRecyclerView.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.reservationList.observe(viewLifecycleOwner) { reservations ->
            adapter.submitList(reservations)
            binding.emptyStateText.visibility = if (reservations.isEmpty()) View.VISIBLE else View.GONE
        }

        viewModel.selectedDate.observe(viewLifecycleOwner) { date ->
            binding.dateEditText.setText(date)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            // Show/hide loading indicator
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            requireContext(),
            { _, year, month, day ->
                val selectedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    .format(GregorianCalendar(year, month, day).time)
                viewModel.setSelectedDate(selectedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }



    sealed class ReservationAction {
        object CONFIRM : ReservationAction()
        object CANCEL : ReservationAction()
    }
}