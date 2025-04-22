package com.orderpos.repository

import com.orderpos.data.local.dao.ReservationDao
import com.orderpos.data.local.entities.ReservationEntity
import kotlinx.coroutines.flow.Flow

class ReservationRepository(private val reservationDao: ReservationDao) {

    fun getReservations(): Flow<List<ReservationEntity>> {
        return reservationDao.getReservations()
    }

    fun getReservationsFilter(currentStatusFilter: String?): Flow<List<ReservationEntity>> {
        return reservationDao.getDeliveryOrdersByStatus(currentStatusFilter!!)
    }

    suspend fun saveReservations(reservations: ReservationEntity) {
        reservationDao.insert(reservations)
    }

    suspend fun updateReservationStatus(reservationId: String, status: String) {
        reservationDao.updateStatus(reservationId, status)
    }
}

