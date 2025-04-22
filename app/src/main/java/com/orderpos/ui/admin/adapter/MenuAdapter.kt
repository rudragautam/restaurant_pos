package com.orderpos.ui.admin.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.orderpos.data.local.entities.MenuItem
import com.orderpos.databinding.ItemSettingsItemBinding

class MenuAdapter : RecyclerView.Adapter<MenuAdapter.MenuItemViewHolder>() {

    interface OnMenuItemClickListener {
        fun onMenuItemClicked(position: Int, item: MenuItem)
    }

    private var clickListener: OnMenuItemClickListener? = null

    fun setOnMenuItemClickListener(listener: OnMenuItemClickListener) {
        this.clickListener = listener
    }

    private var itemList: List<MenuItem> = emptyList()

    fun submitList(list: List<MenuItem>) {
        Log.d("MenuAdapter", "Submitted list: ${list.size} items")
        itemList = list
        notifyDataSetChanged()
    }

    inner class MenuItemViewHolder(val binding: ItemSettingsItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuItemViewHolder {
        val binding = ItemSettingsItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MenuItemViewHolder(binding)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: MenuItemViewHolder, position: Int) {
        val item = itemList[position]
        with(holder.binding) {
            textView6.text = item.name
            textView7.text = item.description
            root.setOnClickListener {
                clickListener?.onMenuItemClicked(position, item)
            }
        }
    }
}