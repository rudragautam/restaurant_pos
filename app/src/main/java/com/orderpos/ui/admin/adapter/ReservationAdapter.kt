package com.orderpos.ui.admin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.orderpos.R
import com.orderpos.data.local.entities.ReservationEntity
import com.orderpos.databinding.ItemReservationBinding
import com.orderpos.ui.user.FloorPlanFragment

class ReservationAdapter(
    private val onActionClicked: (ReservationEntity, FloorPlanFragment.ReservationAction) -> Unit
) : ListAdapter<ReservationEntity, ReservationAdapter.ReservationViewHolder>(ReservationDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationViewHolder {
        val binding = ItemReservationBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ReservationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReservationViewHolder, position: Int) {
        val reservation = getItem(position)
        holder.bind(reservation)
    }

    inner class ReservationViewHolder(private val binding: ItemReservationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(reservation: ReservationEntity) {
            binding.customerNameText.text = reservation.customerName
            binding.dateText.text = reservation.date
            binding.timeText.text = reservation.date
            binding.tableInfoText.text = "Table ${reservation.tableNumber} • ${reservation.guests} guests"

            // Set status
            binding.statusText.text = reservation.status.capitalize()
            val (bgColor, textColor) = when (reservation.status) {
                "confirmed" -> Pair(R.color.green_500, android.R.color.white)
                "cancelled" -> Pair(R.color.red_500, android.R.color.white)
                else -> Pair(R.color.orange_500, android.R.color.white)
            }
            binding.statusText.setBackgroundColor(
                ContextCompat.getColor(binding.root.context, bgColor)
            )
            binding.statusText.setTextColor(
                ContextCompat.getColor(binding.root.context, textColor)
            )

            // Setup buttons
            binding.confirmButton.visibility =
                if (reservation.status == "pending") View.VISIBLE else View.GONE
            binding.cancelButton.visibility =
                if (reservation.status != "cancelled") View.VISIBLE else View.GONE

            binding.confirmButton.setOnClickListener {
                onActionClicked(reservation, FloorPlanFragment.ReservationAction.CONFIRM)
            }
            binding.cancelButton.setOnClickListener {
                onActionClicked(reservation, FloorPlanFragment.ReservationAction.CANCEL)
            }
        }
    }

    class ReservationDiffCallback : DiffUtil.ItemCallback<ReservationEntity>() {
        override fun areItemsTheSame(oldItem: ReservationEntity, newItem: ReservationEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ReservationEntity, newItem: ReservationEntity): Boolean {
            return oldItem == newItem
        }
    }
}
