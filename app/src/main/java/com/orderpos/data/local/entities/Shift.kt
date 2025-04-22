package com.orderpos.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shifts")
data class Shift(
    @PrimaryKey(autoGenerate = true) val shiftId: Long = 0,
    val restaurantId: Long,
    val name: String,
    val startTime: String,
    val endTime: String,
    val colorCode: String // For UI display
)

@Entity(tableName = "employee_shifts")
data class EmployeeShift(
    @PrimaryKey(autoGenerate = true) val employeeShiftId: Long = 0,
    val employeeId: Long,
    val shiftId: Long,
    val date: Long, // Date in milliseconds
    val actualStartTime: Long?, // When they actually clocked in
    val actualEndTime: Long?, // When they actually clocked out
    val status: String // Scheduled, Started, Completed, Missed
)