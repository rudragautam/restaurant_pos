package com.orderpos.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

// Note: In practice, you might just move completed orders from 'orders' to an archive table
// or keep them in the same table with a 'isCompleted' flag
@Entity(tableName = "order_history")
data class OrderHistory(
    @PrimaryKey val orderId: Long,
    // All fields from Order table plus:
    val restaurantId: Long,
    val completedAt: Long,
    val paymentMethod: String?
)