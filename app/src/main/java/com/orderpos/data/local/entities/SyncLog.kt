package com.orderpos.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sync_logs")
data class SyncLog(
    @PrimaryKey(autoGenerate = true) val syncId: Long = 0,
    val deviceId: String,
    val restaurantId: Long,
    val syncType: String, // Initial, Daily, Manual
    val syncTime: Long = System.currentTimeMillis(),
    val recordsSynced: Int,
    val status: String // Success, Failed, Partial
)