package com.orderpos.ui.admin.adddetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.orderpos.databinding.FragmentAddInventoryBinding
import com.orderpos.viewmodal.AddInventoryViewModel


class AddInventoryFragment : Fragment(){

    private lateinit var binding: FragmentAddInventoryBinding
    private val viewModel: AddInventoryViewModel by viewModels()
    private lateinit var restaurantAdapter: ArrayAdapter<String>
    private val restaurantMap = mutableMapOf<String, Long>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAddInventoryBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        observeRestaurantList()
        return binding.root
    }

    private fun observeRestaurantList() {
        viewModel.restaurantList.observe(viewLifecycleOwner) { list ->
            restaurantAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, list.map { it.name })
            binding.restaurantDropdown.setAdapter(restaurantAdapter)
            restaurantMap.clear()
            list.forEach { restaurantMap[it.name] = it.restaurantId }

            binding.restaurantDropdown.setOnItemClickListener { _, _, position, _ ->
                val selectedName = restaurantAdapter.getItem(position) ?: return@setOnItemClickListener
                viewModel.restaurantId.value = restaurantMap[selectedName]
            }
        }
    }

}
