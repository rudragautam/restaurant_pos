package com.orderpos.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "delivery_zones")
data class DeliveryZone(
    @PrimaryKey(autoGenerate = true) val zoneId: Long = 0,
    val restaurantId: Long,
    val name: String,
    val deliveryFee: Double,
    val minOrderAmount: Double?,
    val estimatedDeliveryTime: Int // In minutes
)

@Entity(tableName = "delivery_orders")
data class DeliveryOrder(
    @PrimaryKey val orderId: Long, // Same as in orders table
    val restaurantId: Long?,
    val driverId: Long?,
    val customerAddress: String,
    val customerPhone: String,
    val deliveryInstructions: String?,
    val deliveryFee: Double,
    val status: String, // Pending, Assigned, PickedUp, Delivered, Cancelled
    val estimatedDeliveryTime: Long?,
    val actualDeliveryTime: Long?
)