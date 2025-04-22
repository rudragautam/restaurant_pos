package com.orderpos.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date



@Entity(tableName = "reservations")
data class ReservationEntity(
    @PrimaryKey(autoGenerate = true) val reservationId: Long = 0,
    val id: String,
    val customerName: String,
    val date: String,
    val time: String,
    val tableNumber: Int,
    val guests: Int,
    val status: String
)