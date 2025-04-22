package com.orderpos.repository

import com.orderpos.data.local.dao.ReservationDao
import com.orderpos.data.local.entities.Category
import com.orderpos.data.local.entities.ReservationEntity
import com.orderpos.data.local.entities.Restaurant
import kotlinx.coroutines.flow.Flow

class ReservationRepository(private val reservationDao: ReservationDao) {



    fun getReservations(): Flow<List<ReservationEntity>> {
        return reservationDao.getReservations()
    }

    suspend fun saveReservations(reservations: List<ReservationEntity>) {
        reservationDao.insertAll(reservations)
    }

    suspend fun updateReservationStatus(reservationId: String, status: String) {
        reservationDao.updateStatus(reservationId, status)
    }
}

