package com.orderpos.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class Order(
    @PrimaryKey(autoGenerate = true) val orderId: Long = 0,
    val restaurantId: Long, // Foreign key
    val tableId: Long?, // Null for takeaway
    val customerId: Long?, // Null for walk-ins
    val employeeId: Long, // Who created the order
    val orderType: String, // Dine-in, Takeaway, Delivery
    val status: String, // New, In Progress, Ready, Served, Cancelled
    val notes: String?,
    val subtotal: Double,
    val taxAmount: Double,
    val discountAmount: Double,
    val totalAmount: Double,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "order_items")
data class OrderItem(
    @PrimaryKey(autoGenerate = true) val orderItemId: Long = 0,
    val orderId: Long, // Foreign key
    val itemId: Long, // Foreign key to MenuItem
    val quantity: Int,
    val unitPrice: Double,
    val totalPrice: Double,
    val notes: String?,
    val status: String // New, In Progress, Ready, Served, Cancelled
)

@Entity(tableName = "order_item_options")
data class OrderItemOption(
    @PrimaryKey(autoGenerate = true) val orderItemOptionId: Long = 0,
    val orderItemId: Long, // Foreign key
    val optionId: Long, // Foreign key to MenuItemOption
    val choiceId: Long, // Foreign key to MenuItemOptionChoice
    val choiceName: String,
    val additionalPrice: Double
)