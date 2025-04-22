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
import com.orderpos.data.local.entities.ReservationEntity
import com.orderpos.data.local.entities.Restaurant
import com.orderpos.repository.CategoryRepository
import com.orderpos.repository.MenuItemRepository
import com.orderpos.repository.ReservationRepository
import com.orderpos.repository.RestaurantRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddMenuViewModel(application: Application) : AndroidViewModel(application) {

    private val reservationRepository: ReservationRepository
    private val menuItemRepository: MenuItemRepository
    private val restaurantRepository: RestaurantRepository
    private val categoryRepository: CategoryRepository
    val allMenuList: LiveData<List<MenuItem>>

    init {
        val db = AppDatabase.getDatabase(application)

        reservationRepository = ReservationRepository(db.reservation())
        menuItemRepository = MenuItemRepository(db.menuItemDao())
        restaurantRepository = RestaurantRepository(db.restaurantDao())
        categoryRepository = CategoryRepository(db.categoryDao())
        allMenuList = menuItemRepository.getAllMenuItems().asLiveData()

    }

    // Dropdown LiveData
    val restaurantList = MutableLiveData<List<Restaurant>>()
    val categoryList = MutableLiveData<List<Category>>()

    // Input fields
    val selectedRestaurantName = MutableLiveData<String>()
    val restaurantId = MutableLiveData<Long>()

    val selectedCategoryName = MutableLiveData<String>()
    val categoryId = MutableLiveData<Long>()

    val name = MutableLiveData<String>()
    val description = MutableLiveData<String>()
    val price = MutableLiveData<String>()
    val cost = MutableLiveData<String>()
    val imageUrl = MutableLiveData<String>()
    val preparationTime = MutableLiveData<String>()

    // Checkboxes
    val isAvailable = MutableLiveData<Boolean>(true)
    val isVegetarian = MutableLiveData<Boolean>(false)
    val isVegan = MutableLiveData<Boolean>(false)
    val isGlutenFree = MutableLiveData<Boolean>(false)
    val isSpicy = MutableLiveData<Boolean>(false)
    val isPopular = MutableLiveData<Boolean>(false)
    val isSeasonal = MutableLiveData<Boolean>(false)
    val hasOptions = MutableLiveData<Boolean>(false)

    // Other fields
    val taxGroup = MutableLiveData<String>()
    val barcode = MutableLiveData<String>()
    val sku = MutableLiveData<String>()

    // UI response
    val successMessage = MutableLiveData<String>()

    fun addMenuItem() {
        val item = MenuItem(
            restaurantId = restaurantId.value ?: 0L,
            categoryId = categoryId.value ?: 0L,
            name = name.value ?: "",
            description = description.value,
            price = price.value?.toDoubleOrNull() ?: 0.0,
            cost = cost.value?.toDoubleOrNull() ?: 0.0,
            imageUrl = imageUrl.value,
            preparationTime = preparationTime.value?.toIntOrNull() ?: 0,
            isAvailable = isAvailable.value ?: true,
            isVegetarian = isVegetarian.value ?: false,
            isVegan = isVegan.value ?: false,
            isGlutenFree = isGlutenFree.value ?: false,
            isSpicy = isSpicy.value ?: false,
            isPopular = isPopular.value ?: false,
            isSeasonal = isSeasonal.value ?: false,
            hasOptions = hasOptions.value ?: false,
            taxGroup = taxGroup.value,
            barcode = barcode.value,
            sku = sku.value
        )

        viewModelScope.launch(Dispatchers.IO) {
            menuItemRepository.insertMenuItem(item)
            successMessage.postValue("Menu Item Added!")
        }
    }

    fun fetchRestaurants() {
        viewModelScope.launch {
            restaurantRepository.getAllRestaurants().collect { list ->
                restaurantList.postValue(list)
            }
        }
    }

    fun fetchCategories() {
        viewModelScope.launch {
            categoryRepository.getAllCategories().collect { list ->
                categoryList.postValue(list)
            }
        }
    }

    fun setRestaurantIdFromName(name: String) {
        val id = restaurantList.value?.find { it.name == name }?.restaurantId ?: 0L
        restaurantId.value = id
    }

    fun setCategoryIdFromName(name: String) {
        val id = categoryList.value?.find { it.name == name }?.categoryId ?: 0L
        categoryId.value = id
    }

    fun addReservation(reservationEntity: ReservationEntity) {

        viewModelScope.launch(Dispatchers.IO) {
            reservationRepository.saveReservations(reservationEntity)
//            successMessage.postValue("Category added!")
        }
    }
}
