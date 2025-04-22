package com.orderpos.ui.admin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.orderpos.data.local.entities.MenuItem
import com.orderpos.databinding.ItemCartBinding

class CartAdapter : RecyclerView.Adapter<CartAdapter.CartItemViewHolder>() {

    interface OnCartItemClickListener {
        fun onQuantityClicked(position: Int, item: MenuItem)
    }

    private var clickListener: OnCartItemClickListener? = null
    private var itemList: List<MenuItem> = emptyList()

    fun setOnCartItemClickListener(listener: OnCartItemClickListener) {
        this.clickListener = listener
    }

    fun submitList(list: List<MenuItem>) {
        itemList = list
        notifyDataSetChanged()
    }

    inner class CartItemViewHolder(val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        val binding = ItemCartBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CartItemViewHolder(binding)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        val item = itemList[position]
        with(holder.binding) {
            textView6.text = item.name
            textView7.text = when {
                item.price != null -> "Price: ₹${item.price}"
                else -> "Price not available"
            }
            textView8.setOnClickListener {
                clickListener?.onQuantityClicked(position, item)
            }
        }
    }

    fun updateItem(position: Int, newQuantity: Int) {
        // Implementation remains the same as before
        /* itemList[position].quantityType = newQuantity
         notifyItemChanged(position)*/
    }
}