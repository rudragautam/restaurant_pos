package com.orderpos.viewmodal

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.orderpos.data.local.AppDatabase
import com.orderpos.data.local.entities.Category
import com.orderpos.data.local.entities.InventoryItem
import com.orderpos.data.local.entities.Restaurant
import com.orderpos.repository.InventoryRepository
import com.orderpos.repository.RestaurantRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddInventoryViewModel(application: Application) : AndroidViewModel(application) {

    private val inventoryRepository: InventoryRepository
    private val restaurantRepository: RestaurantRepository
    val allInventory: LiveData<List<InventoryItem>>

    val restaurantList = MutableLiveData<List<Restaurant>>()
    val restaurantId = MutableLiveData<Long>()
    val name = MutableLiveData<String>()
    val unit = MutableLiveData<String>()
    val currentStock = MutableLiveData<String>()
    val alertThreshold = MutableLiveData<String>()
    val costPerUnit = MutableLiveData<String>()
    val supplier = MutableLiveData<String?>()
    val successMessage = MutableLiveData<String>()

    init {
        val db = AppDatabase.getDatabase(application)
        inventoryRepository = InventoryRepository(db.inventoryDao())
        restaurantRepository = RestaurantRepository(db.restaurantDao())
        allInventory = inventoryRepository.getInventoryItemsByRestaurant(1).asLiveData()

        fetchRestaurants()
    }

    fun fetchRestaurants() {
        viewModelScope.launch {
            restaurantRepository.getAllRestaurants().collect {
                restaurantList.postValue(it)
            }
        }
    }

    fun saveInventoryItem() {
        val item = InventoryItem(
            restaurantId = restaurantId.value ?: 0L,
            name = name.value.orEmpty(),
            unit = unit.value.orEmpty(),
            currentStock = currentStock.value?.toDoubleOrNull() ?: 0.0,
            alertThreshold = alertThreshold.value?.toDoubleOrNull() ?: 0.0,
            costPerUnit = costPerUnit.value?.toDoubleOrNull() ?: 0.0,
            supplier = supplier.value
        )
        viewModelScope.launch(Dispatchers.IO) {
            inventoryRepository.insertInventoryItem(item)
            successMessage.postValue("Inventory Item Added")
        }
    }

}
