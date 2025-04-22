package com.orderpos.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "payments")
data class Payment(
    @PrimaryKey(autoGenerate = true) val paymentId: Long = 0,
    val orderId: Long, // Foreign key
    val restaurantId: Long,
    val amount: Double,
    val paymentMethod: String, // Cash, Credit Card, Debit Card, Mobile Payment, etc.
    val transactionId: String?,
    val paymentStatus: String, // Pending, Completed, Failed, Refunded
    val tipAmount: Double,
    val paymentTime: Long = System.currentTimeMillis(),
    val employeeId: Long // Who processed the payment
)