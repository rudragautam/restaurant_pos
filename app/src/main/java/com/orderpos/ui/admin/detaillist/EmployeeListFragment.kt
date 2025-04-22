package com.orderpos.ui.admin.detaillist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.orderpos.R
import com.orderpos.data.local.entities.Employee
import com.orderpos.databinding.FragmentEmployeeListBinding
import com.orderpos.ui.admin.adapter.EmployeeAdapter
import com.orderpos.viewmodal.AddEmployeeViewModel

class EmployeeListFragment : Fragment() {

    private var _binding: FragmentEmployeeListBinding? = null
    private val categoryAdapter by lazy { EmployeeAdapter() }
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val addRestaurantViewModel: AddEmployeeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentEmployeeListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupRecyclerView()
        observeRestaurants()

        return root
    }

    private fun setupRecyclerView() {

        binding.rectangles.apply {
            layoutManager= GridLayoutManager(requireContext(), 4)
            adapter= categoryAdapter

            categoryAdapter.setOnCartItemClickListener(object : EmployeeAdapter.OnCartItemClickListener {

                override fun onAddItemClicked() {
                    findNavController().navigate(R.id.addEmployeeFragment)

                }

                override fun onMenuItemClicked(position: Int, item: Employee) {
                }
            })
        }
    }

    private fun observeRestaurants() {

        addRestaurantViewModel.employeeList.observe(viewLifecycleOwner) { restaurants ->
            Log.d("RestaurantListFragment", "Observing restaurants${restaurants}")
            categoryAdapter.submitList(restaurants)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}




