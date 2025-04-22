package com.orderpos.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "audit_logs")
data class AuditLog(
    @PrimaryKey(autoGenerate = true) val logId: Long = 0,
    val restaurantId: Long,
    val userId: Long?, // Who performed the action
    val actionType: String, // Create, Update, Delete, Login, etc.
    val entityType: String, // Order, MenuItem, etc.
    val entityId: Long?,
    val oldValue: String?,
    val newValue: String?,
    val timestamp: Long = System.currentTimeMillis(),
    val deviceId: String?,
    val ipAddress: String?
)