package com.orderpos.viewmodal


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.orderpos.data.local.AppDatabase
import com.orderpos.data.local.entities.Category
import com.orderpos.data.local.entities.MenuItem
import com.orderpos.data.local.entities.Restaurant
import com.orderpos.repository.CategoryRepository
import com.orderpos.repository.RestaurantRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddCategoryViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: CategoryRepository
    private val restaurantRepository: RestaurantRepository
    val allCategory: LiveData<List<Category>>


    init {
        val categoryDao = AppDatabase.getDatabase(application).categoryDao()
        val resurantDao = AppDatabase.getDatabase(application).restaurantDao()
        repository = CategoryRepository(categoryDao)
        restaurantRepository=RestaurantRepository(resurantDao)
        allCategory = repository.getAllCategories().asLiveData()

    }

    val selectedRestaurantName = MutableLiveData<String>()
    val restaurantId = MutableLiveData<Long>()
    val name = MutableLiveData<String>()
    val description = MutableLiveData<String>()
    val imageUrl = MutableLiveData<String>()
    val displayOrder = MutableLiveData<String>()
    val isActive = MutableLiveData<Boolean>(true)
    val restaurantList = MutableLiveData<List<Restaurant>>()
    val successMessage = MutableLiveData<String>()

    fun addCategory() {
        val category = Category(
            restaurantId = restaurantId.value ?: 0L,
            name = name.value ?: "",
            description = description.value,
            imageUrl = imageUrl.value,
            displayOrder = displayOrder.value?.toIntOrNull() ?: 0,
            isActive = isActive.value ?: true
        )

        viewModelScope.launch(Dispatchers.IO) {
            repository.insertCategory(category)
            successMessage.postValue("Category added!")
        }
    }

    fun fetchRestaurants() {
        viewModelScope.launch {
            restaurantRepository.getAllRestaurants().collect { restaurants ->
                restaurantList.postValue(restaurants)
            }
        }
    }
}
