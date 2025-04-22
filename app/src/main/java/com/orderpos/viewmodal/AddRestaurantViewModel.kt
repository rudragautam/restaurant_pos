package com.orderpos.viewmodal

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.orderpos.data.local.AppDatabase
import com.orderpos.data.local.entities.Restaurant
import com.orderpos.repository.RestaurantRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddRestaurantViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: RestaurantRepository
    val allRestaurants: LiveData<List<Restaurant>>
    init {
        val restaurantDao = AppDatabase.getDatabase(application).restaurantDao()
        repository = RestaurantRepository(restaurantDao)
        allRestaurants = repository.getAllRestaurants().asLiveData()

    }

    val chainId = MutableLiveData<String>()
    val name = MutableLiveData<String>()
    val address = MutableLiveData<String>()
    val city = MutableLiveData<String>()
    val state = MutableLiveData<String>()
    val zipCode = MutableLiveData<String>()
    val country = MutableLiveData<String>()
    val phone = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val openingTime = MutableLiveData<String>()
    val closingTime = MutableLiveData<String>()
    val timeZone = MutableLiveData<String>()
    val taxNumber = MutableLiveData<String>()
    val currency = MutableLiveData<String>("USD")
    val isActive = MutableLiveData<Boolean>(true)

    val successMessage = MutableLiveData<String>()

    fun addRestaurant() {
        val restaurant = Restaurant(
            chainId = chainId.value?.toLongOrNull(),
            name = name.value ?: "",
            address = address.value ?: "",
            city = city.value ?: "",
            state = state.value ?: "",
            zipCode = zipCode.value ?: "",
            country = country.value ?: "",
            phone = phone.value ?: "",
            email = email.value ?: "",
            openingTime = openingTime.value ?: "",
            closingTime = closingTime.value ?: "",
            timeZone = timeZone.value ?: "",
            taxRegistrationNumber = taxNumber.value,
            currency = currency.value ?: "USD",
            isActive = isActive.value ?: true,
            createdAt = System.currentTimeMillis(),
            updatedAt = System.currentTimeMillis()
        )

        viewModelScope.launch(Dispatchers.IO) {
            repository.insertRestaurant(restaurant)
            successMessage.postValue("Restaurant added!")
        }

    }
}
