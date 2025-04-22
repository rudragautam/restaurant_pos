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
import com.orderpos.data.local.entities.Restaurant
import com.orderpos.databinding.FragmentRestaurantsListBinding
import com.orderpos.ui.admin.adapter.RestaurantAdapter
import com.orderpos.viewmodal.AddRestaurantViewModel

class RestaurantListFragment : Fragment() {

    private var _binding: FragmentRestaurantsListBinding? = null
    private val restaurantAdapter by lazy { RestaurantAdapter() }
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val addRestaurantViewModel: AddRestaurantViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentRestaurantsListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupRecyclerView()
        observeRestaurants()








        return root
    }

    private fun setupRecyclerView() {



        binding.rectangles.apply {
            layoutManager= GridLayoutManager(requireContext(), 4)
            adapter= restaurantAdapter

            restaurantAdapter.setOnCartItemClickListener(object : RestaurantAdapter.OnCartItemClickListener {

                override fun onAddItemClicked() {
                    findNavController().navigate(R.id.nav_dashboard)
                }

                override fun onMenuItemClicked(position: Int, item: Restaurant) {
                }
            })
        }
    }

    private fun observeRestaurants() {

        addRestaurantViewModel.allRestaurants.observe(viewLifecycleOwner) { restaurants ->
            Log.d("RestaurantListFragment", "Observing restaurants${restaurants}")
            restaurantAdapter.submitList(restaurants)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}




