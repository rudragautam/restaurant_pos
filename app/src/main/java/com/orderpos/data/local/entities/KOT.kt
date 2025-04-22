package com.orderpos.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "kots")
data class KOT(
    @PrimaryKey(autoGenerate = true) val kotId: Long = 0,
    val orderId: Long,
    val restaurantId: Long,
    val kitchenStation: String, // Which kitchen station this goes to
    val status: String, // New, Preparing, Ready, Served
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "kot_items")
data class KOTItem(
    @PrimaryKey(autoGenerate = true) val kotItemId: Long = 0,
    val kotId: Long,
    val orderItemId: Long,
    val itemName: String,
    val quantity: Int,
    val specialInstructions: String?,
    val status: String // New, Preparing, Ready
)