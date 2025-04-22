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
import com.orderpos.data.local.entities.MenuItem
import com.orderpos.databinding.FragmentMenuitemListBinding
import com.orderpos.ui.admin.adapter.MenuItemAdapter
import com.orderpos.viewmodal.AddMenuViewModel

class MenuItemListFragment : Fragment() {

    private var _binding: FragmentMenuitemListBinding? = null
    private val menuItemAdapter by lazy { MenuItemAdapter() }
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val addRestaurantViewModel: AddMenuViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentMenuitemListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupRecyclerView()
        observeRestaurants()
        return root
    }

    private fun setupRecyclerView() {



        binding.rectangles.apply {
            layoutManager= GridLayoutManager(requireContext(), 4)
            adapter= menuItemAdapter

            menuItemAdapter.setOnCartItemClickListener(object : MenuItemAdapter.OnCartItemClickListener {

                override fun onAddItemClicked() {
                    findNavController().navigate(R.id.addMenuFragment)
//                    startActivity(Intent(requireActivity(),AddMenuActivity::class.java))
                }

                override fun onMenuItemClicked(position: Int, item: MenuItem) {
                }
            })
        }
    }

    private fun observeRestaurants() {

        addRestaurantViewModel.allMenuList.observe(viewLifecycleOwner) { restaurants ->
            Log.d("RestaurantListFragment", "Observing restaurants${restaurants}")
            menuItemAdapter.submitList(restaurants)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}




