package com.orderpos.repository

import com.orderpos.data.local.dao.RestaurantDao
import com.orderpos.data.local.entities.Restaurant
import kotlinx.coroutines.flow.Flow

class RestaurantRepository(private val dao: RestaurantDao) {

    suspend fun insertRestaurant(restaurant: Restaurant) {
        dao.insertRestaurant(restaurant)
    }

    fun getAllRestaurants(): Flow<List<Restaurant>> {
        return dao.getAllRestaurants()
    }


}
