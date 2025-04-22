package com.orderpos.ui.admin.adddetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.orderpos.databinding.FragmentAddRestaurantBinding
import com.orderpos.viewmodal.AddRestaurantViewModel

class AddRestaurantFragment : Fragment() {

    private var _binding: FragmentAddRestaurantBinding? = null
    private val binding get() = _binding!!


    private val addRestaurantViewModel: AddRestaurantViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddRestaurantBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = addRestaurantViewModel

        addRestaurantViewModel.successMessage.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
