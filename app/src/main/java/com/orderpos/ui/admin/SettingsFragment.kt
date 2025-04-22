package com.orderpos.ui.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.orderpos.R
import com.orderpos.databinding.FragmentSettingsBinding
import com.orderpos.ui.admin.adapter.SettingsAdapter
import com.orderpos.ui.admin.adapter.SettingsItem


class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val leftItemList: List<SettingsItem> = listOf(
            SettingsItem(title = "Restaurant Management", id = 1, isSelected = false, description = "Edit your restaurant details", navDestination = R.id.nav_reataurants_list),
            SettingsItem(title = "Categories Management", id = 1,  isSelected = false, description = "Edit your category details", navDestination = R.id.categoryListFragment),
            SettingsItem(title = "Menu Management", id = 1, isSelected = false, description = "Add, edit, delete menu items", navDestination = R.id.menuItemListFragment),
            SettingsItem(title = "Inventory Management", id = 1,  isSelected = false, description = "Manage inventory", navDestination = R.id.inventoryListFragment),
            SettingsItem(title = "User Management", id = 1,  isSelected = false, description = "Manage users", navDestination = R.id.employeeListFragment),
//            SettingsItem(title = "Notifications", id = 1, isSelected = false, description = "Manage notifications", navDestination = R.id.employeeListFragment),
//            SettingsItem(title = "Security", id = 1, isSelected = false, description = "Manage security settings", navDestination = R.id.nav_settings),
//            SettingsItem(title = "About Us", id = 1, isSelected = false, description = "Learn more about us", navDestination = R.id.nav_settings),

        )



        binding.cartrectangles.apply {
            layoutManager= LinearLayoutManager(requireContext())
            val cartAdapter= SettingsAdapter(leftItemList)
            adapter=cartAdapter

            cartAdapter.onItemClicked = { item ->
                /*val navController = childFragmentManager.findFragmentById(R.id.nav_host_fragment_content_settings)
                    ?.findNavController()

                when (item.id) {
                    1 -> navController?.navigate(R.id.restaurantFragment)
                    2 -> navController?.navigate(R.id.nav_settings)
                    3 -> navController?.navigate(R.id.nav_tables)
                }*/
                val navController = childFragmentManager.findFragmentById(R.id.nav_host_fragment_content_settings)
                    ?.findNavController()
                navController?.navigate(item.navDestination)
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

