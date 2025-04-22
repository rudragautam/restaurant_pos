package com.orderpos.ui.admin.adddetail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.orderpos.databinding.FragmentAddEmployeeBinding
import com.orderpos.viewmodal.AddEmployeeViewModel
import java.util.*

class AddEmployeeFragment : Fragment() {

    private var _binding: FragmentAddEmployeeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AddEmployeeViewModel by viewModels()

    private val calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddEmployeeBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.restaurantList.observe(viewLifecycleOwner) { restaurants ->
            val names = restaurants.map { it.name }
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, names)
            binding.restaurantDropdown.setAdapter(adapter)

            binding.restaurantDropdown.setOnItemClickListener { _, _, position, _ ->
                val selectedRestaurant = restaurants[position]
                viewModel.restaurantId.value = selectedRestaurant.restaurantId
                viewModel.selectedRestaurantName.value = selectedRestaurant.name
            }
        }

        viewModel.successMessage.observe(viewLifecycleOwner) {
            // Show toast or navigate
            it?.let { msg ->
                android.widget.Toast.makeText(requireContext(), msg, android.widget.Toast.LENGTH_SHORT).show()
            }
        }
    }

/*    override fun onSaveClicked() {
        viewModel.addEmployee()
    }

    override fun onPickHireDateClicked() {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val dialog = DatePickerDialog(requireContext(), { _, y, m, d ->
            calendar.set(y, m, d)
            viewModel.hireDate.value = calendar.timeInMillis
            viewModel.hireDateText.value = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.time)
        }, year, month, day)

        dialog.show()
    }*/

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
