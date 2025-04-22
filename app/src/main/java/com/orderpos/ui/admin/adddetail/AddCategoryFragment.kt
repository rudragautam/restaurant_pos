package com.orderpos.ui.admin.adddetail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.orderpos.databinding.FragmentAddCategoryBinding
import com.orderpos.viewmodal.AddCategoryViewModel

class AddCategoryFragment : Fragment() {

    private lateinit var binding: FragmentAddCategoryBinding
    private val viewModel: AddCategoryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddCategoryBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.successMessage.observe(viewLifecycleOwner, Observer { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            // Optionally clear fields here
        })



        viewModel.fetchRestaurants()

        viewModel.restaurantList.observe(viewLifecycleOwner) { list ->
            /*val adapter = ArrayAdapter(
                requireContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                list.map { it.name }
            )
            binding.restaurantDropdown.setAdapter(adapter)

            binding.restaurantDropdown.setOnItemClickListener { _, _, position, _ ->
                val selectedRestaurant = list[position]
                viewModel.restaurantId.value = selectedRestaurant.restaurantId
                viewModel.selectedRestaurantName.value = selectedRestaurant.name
            }*/
            val names = list.map { it.name }
            val adapter = ArrayAdapter(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, names)
            binding.restaurantDropdown.setAdapter(adapter)
        }

        return binding.root
    }
}
