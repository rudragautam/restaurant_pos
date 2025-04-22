package com.orderpos.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.orderpos.R
import com.orderpos.databinding.FragmentActiveOrderBinding
import com.orderpos.databinding.FragmentDashboardBinding
import com.orderpos.ui.admin.adapter.OrderAdapter
import com.orderpos.ui.admin.adapter.OrderItem


class ActiveOrderFragment : Fragment() {

    private var _binding: FragmentActiveOrderBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentActiveOrderBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val leftItemList: List<OrderItem> = listOf(
            OrderItem(title = "Eren Jaegar", id = 1, isSelected = false, description = "$125", navDestination = R.id.nav_reataurants_list),
            OrderItem(title = "Reiner Braunn", id = 1,  isSelected = false, description = "$125", navDestination = R.id.categoryListFragment),
            OrderItem(title = "Levi Ackerman", id = 1, isSelected = false, description = "$125", navDestination = R.id.menuItemListFragment),
            OrderItem(title = "Historia Reiss", id = 1,  isSelected = false, description = "$125", navDestination = R.id.inventoryListFragment),
            OrderItem(title = "Hanji Zoe", id = 1,  isSelected = false, description = "$125", navDestination = R.id.employeeListFragment),
            OrderItem(title = "Reiner Braunn", id = 1,  isSelected = false, description = "$125", navDestination = R.id.categoryListFragment),
            OrderItem(title = "Levi Ackerman", id = 1, isSelected = false, description = "$125", navDestination = R.id.menuItemListFragment),
            OrderItem(title = "Historia Reiss", id = 1,  isSelected = false, description = "$125", navDestination = R.id.inventoryListFragment),
            OrderItem(title = "Hanji Zoe", id = 1,  isSelected = false, description = "$125", navDestination = R.id.employeeListFragment),
            OrderItem(title = "Eren Jaegar", id = 1, isSelected = false, description = "$125", navDestination = R.id.nav_reataurants_list),
            OrderItem(title = "Reiner Braunn", id = 1,  isSelected = false, description = "$125", navDestination = R.id.categoryListFragment),
            OrderItem(title = "Levi Ackerman", id = 1, isSelected = false, description = "$125", navDestination = R.id.menuItemListFragment),
            OrderItem(title = "Historia Reiss", id = 1,  isSelected = false, description = "$125", navDestination = R.id.inventoryListFragment),
            OrderItem(title = "Hanji Zoe", id = 1,  isSelected = false, description = "$125", navDestination = R.id.employeeListFragment),
            OrderItem(title = "Reiner Braunn", id = 1,  isSelected = false, description = "$125", navDestination = R.id.categoryListFragment),
            OrderItem(title = "Levi Ackerman", id = 1, isSelected = false, description = "$125", navDestination = R.id.menuItemListFragment),
            OrderItem(title = "Historia Reiss", id = 1,  isSelected = false, description = "$125", navDestination = R.id.inventoryListFragment),
            OrderItem(title = "Hanji Zoe", id = 1,  isSelected = false, description = "$125", navDestination = R.id.employeeListFragment)

        )

        binding.orderList.apply {
            layoutManager= LinearLayoutManager(requireContext())
            val cartAdapter= OrderAdapter(leftItemList)
            adapter=cartAdapter

            cartAdapter.onItemClicked = { item ->
                /*val navController = childFragmentManager.findFragmentById(R.id.nav_host_fragment_content_settings)
                    ?.findNavController()
                navController?.navigate(item.navDestination)*/
            }

            /*navController?.navigate(item.navDestination)
*/

        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}