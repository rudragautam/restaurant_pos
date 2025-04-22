package com.orderpos.ui.admin.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.orderpos.data.local.entities.Employee
import com.orderpos.databinding.ItemAddButtonBinding
import com.orderpos.databinding.ItemSettingsItemBinding

class EmployeeAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_ADD_ITEM = 0
        private const val VIEW_TYPE_MENU_ITEM = 1
    }

    interface OnCartItemClickListener {
        fun onAddItemClicked()
        fun onMenuItemClicked(position: Int, item: Employee)
    }

    private var clickListener: OnCartItemClickListener? = null

    fun setOnCartItemClickListener(listener: OnCartItemClickListener) {
        this.clickListener = listener
    }

    private var itemList: List<Employee> = emptyList()

    fun submitList(list: List<Employee>) {
        Log.d("RestaurantListFragment", "list restaurants${list}")
        itemList = list
        notifyDataSetChanged()
    }

    // ViewHolder for Add Item row
    inner class AddItemViewHolder(val binding: ItemAddButtonBinding) : RecyclerView.ViewHolder(binding.root)

    // ViewHolder for Menu Items
    inner class MenuItemViewHolder(val binding: ItemSettingsItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) VIEW_TYPE_ADD_ITEM else VIEW_TYPE_MENU_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_ADD_ITEM -> {
                val binding = ItemAddButtonBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                AddItemViewHolder(binding)
            }
            VIEW_TYPE_MENU_ITEM -> {
                val binding = ItemSettingsItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                MenuItemViewHolder(binding)
            }

            else -> {
                val binding = ItemSettingsItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                MenuItemViewHolder(binding)
            }
        }
    }

    override fun getItemCount(): Int = itemList.size + 1 // +1 for Add Item

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is AddItemViewHolder -> {
                holder.binding.root.setOnClickListener {
                    clickListener?.onAddItemClicked()
                }
            }

            is MenuItemViewHolder -> {
                val item = itemList[position - 1] // Offset for Add button
                with(holder.binding) {
                    textView6.text = item.name
                    textView7.text = item.phone
                    root.setOnClickListener {
                        clickListener?.onMenuItemClicked(position - 1, item)
                    }
                }
            }
        }
    }
}
