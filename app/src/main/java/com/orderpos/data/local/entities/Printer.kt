package com.orderpos.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "printers")
data class Printer(
    @PrimaryKey(autoGenerate = true) val printerId: Long = 0,
    val restaurantId: Long,
    val name: String,
    val macAddress: String,
    val ipAddress: String,
    val connectionType: String, // Bluetooth, Network, USB
    val printerType: String, // Receipt, Kitchen, Bar
    val paperWidth: Int, // mm
    val isActive: Boolean = true,
    val lastConnected: Long?
)