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
import com.orderpos.data.local.entities.Category
import com.orderpos.databinding.FragmentCategoryListBinding
import com.orderpos.ui.admin.adapter.CategoryAdapter
import com.orderpos.viewmodal.AddCategoryViewModel

class CategoryListFragment : Fragment() {

    private var _binding: FragmentCategoryListBinding? = null
    private val categoryAdapter by lazy { CategoryAdapter() }
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val addRestaurantViewModel: AddCategoryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentCategoryListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupRecyclerView()
        observeRestaurants()

        return root
    }

    private fun setupRecyclerView() {

        binding.rectangles.apply {
            layoutManager= GridLayoutManager(requireContext(), 4)
            adapter= categoryAdapter

            categoryAdapter.setOnCartItemClickListener(object : CategoryAdapter.OnCartItemClickListener {

                override fun onAddItemClicked() {
                    findNavController().navigate(R.id.addCategoryFragment)
//                    startActivity(Intent(requireActivity(),AddM
                //                    enuActivity::class.java))
                }

                override fun onMenuItemClicked(position: Int, item: Category) {
                }
            })
        }
    }

    private fun observeRestaurants() {

        addRestaurantViewModel.allCategory.observe(viewLifecycleOwner) { restaurants ->
            Log.d("RestaurantListFragment", "Observing restaurants${restaurants}")
            categoryAdapter.submitList(restaurants)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}




