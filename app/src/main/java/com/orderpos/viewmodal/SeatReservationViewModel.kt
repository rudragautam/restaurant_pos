package com.orderpos.viewmodal

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.orderpos.data.local.AppDatabase
import com.orderpos.data.local.entities.Category
import com.orderpos.data.local.entities.ReservationEntity
import com.orderpos.repository.ReservationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

class SeatReservationViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ReservationRepository
//    val reservationList = MutableLiveData<List<ReservationEntity>>()

    val reservationList: LiveData<List<ReservationEntity>>

    private val _selectedDate = MutableLiveData<String>().apply {
        value = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
    }
    val selectedDate: LiveData<String> = _selectedDate

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private var currentStatusFilter: String? = null
    private var currentDateFilter: String? = null

    init {
//        addReservation()
//        loadReservations()
        val reservationDao = AppDatabase.getDatabase(application).reservation()
        repository = ReservationRepository(reservationDao)
        reservationList = repository.getReservations().asLiveData()
    }

/*    fun loadReservations() {
        viewModelScope.launch {
            repository.getReservations().collect { restaurants ->
                reservationList.postValue(restaurants)
            }
        }
    }*/

    fun filterByDate(date: String) {
        currentDateFilter = date
//        loadReservations()
    }

    fun filterByStatus(status: String?) {
        currentStatusFilter = status
//        loadReservations()
    }

    fun setSelectedDate(date: String) {
        _selectedDate.value = date
    }

    fun confirmReservation(reservationId: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                repository.updateReservationStatus(reservationId, "confirmed")
//                loadReservations() // Refresh the list
            } catch (e: Exception) {
                _errorMessage.value = "Failed to confirm reservation"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun cancelReservation(reservationId: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                repository.updateReservationStatus(reservationId, "cancelled")
//                loadReservations() // Refresh the list
            } catch (e: Exception) {
                _errorMessage.value = "Failed to cancel reservation"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addReservation() {

        viewModelScope.launch(Dispatchers.IO) {
            val currentDateTime = "22-04-2023 10:00"

            val dummyList = listOf(
                ReservationEntity(id = "R001", customerName = "Tom Hanks", date = currentDateTime.toString(), time = currentDateTime.toString(), tableNumber = 1, guests = 2, status = "Confirmed"),
                ReservationEntity(id = "R002", customerName = "Emma Watson", date = currentDateTime.toString(), time = currentDateTime.toString(), tableNumber = 2, guests = 3, status = "Pending")
            )
            repository.saveReservations(dummyList)
//            successMessage.postValue("Category added!")
        }
    }
}