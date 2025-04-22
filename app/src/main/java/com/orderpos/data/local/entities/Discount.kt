package com.orderpos.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "discounts")
data class Discount(
    @PrimaryKey(autoGenerate = true) val discountId: Long = 0,
    val restaurantId: Long, // Foreign key
    val name: String,
    val description: String?,
    val discountType: String, // Percentage, Fixed Amount
    val value: Double,
    val code: String?,
    val startDate: Long,
    val endDate: Long,
    val isActive: Boolean = true,
    val minOrderAmount: Double?,
    val applicableCategories: String? // JSON array of category IDs
)