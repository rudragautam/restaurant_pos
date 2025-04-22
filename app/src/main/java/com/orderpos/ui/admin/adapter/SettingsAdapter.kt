package com.orderpos.ui.admin.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.orderpos.R
import com.orderpos.databinding.ItemSettingsBinding

class SettingsAdapter(
    private val itemList: List<SettingsItem>
) : RecyclerView.Adapter<SettingsAdapter.ItemViewHolder>() {

    private var selectedPosition = RecyclerView.NO_POSITION

    var onItemClicked: ((SettingsItem) -> Unit)? = null

    inner class ItemViewHolder(val binding: ItemSettingsBinding) : RecyclerView.ViewHolder(binding.root)
    {
        init {
            binding.root.setOnClickListener {
                val previous = selectedPosition
                selectedPosition = bindingAdapterPosition

                itemList.forEachIndexed { index, item ->
                    item.isSelected = index == selectedPosition
                }

                notifyItemChanged(previous)
                notifyItemChanged(selectedPosition)

                onItemClicked?.invoke(itemList[selectedPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemSettingsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itemList[position]
        with(holder.binding) {
            textView6.text = item.title

            textView7.text = item.description

            root.setBackgroundColor(
                if (item.isSelected)
                    ContextCompat.getColor(root.context, R.color.selector_light)
                else
                    Color.TRANSPARENT
            )

            /*tvAvailable.apply {
                text = if (item.available) "Available" else "Not Available"
                setTextColor(
                    ContextCompat.getColor(
                        root.context,
                        if (item.available) android.R.color.holo_green_dark else android.R.color.holo_red_dark
                    )
                )
            }*/
        }
    }
}

data class SettingsItem(
    val id: Int,
    val title: String,
    val description: String,
    var isSelected: Boolean = false,
    @IdRes val navDestination: Int
)




