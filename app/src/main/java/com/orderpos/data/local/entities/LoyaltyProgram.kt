package com.orderpos.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "loyalty_programs")
data class LoyaltyProgram(
    @PrimaryKey(autoGenerate = true) val programId: Long = 0,
    val restaurantId: Long,
    val name: String,
    val pointsPerDollar: Double,
    val redemptionRate: Double, // Points needed per dollar
    val isActive: Boolean = true
)

@Entity(tableName = "loyalty_transactions")
data class LoyaltyTransaction(
    @PrimaryKey(autoGenerate = true) val transactionId: Long = 0,
    val customerId: Long,
    val orderId: Long?,
    val pointsEarned: Int,
    val pointsRedeemed: Int,
    val balance: Int,
    val transactionType: String, // Earn, Redeem, Adjust
    val notes: String?,
    val timestamp: Long = System.currentTimeMillis()
)