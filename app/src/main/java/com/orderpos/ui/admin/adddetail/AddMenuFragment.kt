package com.orderpos.ui.admin.adddetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.fragment.app.Fragment
import com.orderpos.databinding.FragmentAddMenusBinding
import com.orderpos.viewmodal.AddMenuViewModel

class AddMenuFragment : Fragment() {

    private var _binding: FragmentAddMenusBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val addMenuViewModel: AddMenuViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddMenusBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = addMenuViewModel

        setupObservers()

        addMenuViewModel.fetchCategories()
        addMenuViewModel.fetchRestaurants()

        return binding.root
    }

    private fun setupObservers() {
        addMenuViewModel.restaurantList.observe(viewLifecycleOwner) { restaurants ->
            val names = restaurants.map { it.name }
            binding.restaurantDropdown.setAdapter(
                ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, names)
            )
        }

        addMenuViewModel.categoryList.observe(viewLifecycleOwner) { categories ->
            val names = categories.map { it.name }
            binding.categoryDropdown.setAdapter(
                ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, names)
            )
        }

        addMenuViewModel.successMessage.observe(viewLifecycleOwner) {
            // Toast or Snackbar
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
