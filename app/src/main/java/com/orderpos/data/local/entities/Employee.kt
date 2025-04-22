package com.orderpos.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "employees")
data class Employee(
    @PrimaryKey(autoGenerate = true) val employeeId: Long = 0,
    val restaurantId: Long, // Foreign key
    val name: String,
    val phone: String,
    val email: String,
    val role: String, // Manager, Waiter, Cashier, Chef, etc.
    val pinCode: String, // For POS login
    val isActive: Boolean = true,
    val hireDate: Long,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)