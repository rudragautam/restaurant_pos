package com.orderpos.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "restaurants")
data class Restaurant(
    @PrimaryKey(autoGenerate = true) val restaurantId: Long = 0,
    val chainId: Long?, // For restaurant chains
    val name: String,
    val address: String,
    val city: String,
    val state: String,
    val zipCode: String,
    val country: String,
    val phone: String,
    val email: String,
    val openingTime: String,
    val closingTime: String,
    val timeZone: String,
    val taxRegistrationNumber: String?,
    val currency: String = "USD",
    val isActive: Boolean = true,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)