package com.orderpos.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey(autoGenerate = true) val categoryId: Long = 0,
    val restaurantId: Long,
    val name: String,
    val description: String?,
    val imageUrl: String?,
    val displayOrder: Int,
    val isActive: Boolean = true
)