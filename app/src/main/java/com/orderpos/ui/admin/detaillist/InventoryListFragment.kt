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
import com.orderpos.data.local.entities.InventoryItem
import com.orderpos.databinding.FragmentInventoryListBinding
import com.orderpos.ui.admin.adapter.InventoryAdapter
import com.orderpos.viewmodal.AddInventoryViewModel

class InventoryListFragment : Fragment() {

    private var _binding: FragmentInventoryListBinding? = null
    private val categoryAdapter by lazy { InventoryAdapter() }
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val addRestaurantViewModel: AddInventoryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentInventoryListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupRecyclerView()
        observeRestaurants()

        return root
    }

    private fun setupRecyclerView() {

        binding.rectangles.apply {
            layoutManager= GridLayoutManager(requireContext(), 4)
            adapter= categoryAdapter

            categoryAdapter.setOnCartItemClickListener(object : InventoryAdapter.OnCartItemClickListener {

                override fun onAddItemClicked() {
                    findNavController().navigate(R.id.addInventoryFragment)
//                    startActivity(Intent(requireActivity(),AddM
                //                    enuActivity::class.java))
                }

                override fun onMenuItemClicked(position: Int, item: InventoryItem) {
                }
            })
        }
    }

    private fun observeRestaurants() {

        addRestaurantViewModel.allInventory.observe(viewLifecycleOwner) { restaurants ->
            Log.d("RestaurantListFragment", "Observing restaurants${restaurants}")
            categoryAdapter.submitList(restaurants)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}




