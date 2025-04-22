package com.orderpos.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "menu_items")
data class MenuItem(
    @PrimaryKey(autoGenerate = true) val itemId: Long = 0,
    val restaurantId: Long,
    val categoryId: Long,
    val name: String,
    val description: String?,
    val price: Double,
    val cost: Double,
    val imageUrl: String?,
    val preparationTime: Int,
    val isAvailable: Boolean = true,
    val isVegetarian: Boolean = false,
    val isVegan: Boolean = false,
    val isGlutenFree: Boolean = false,
    val isSpicy: Boolean = false,
    val isPopular: Boolean = false,
    val isSeasonal: Boolean = false,
    val hasOptions: Boolean = false,
    val taxGroup: String?,
    val barcode: String?,
    val sku: String?,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)