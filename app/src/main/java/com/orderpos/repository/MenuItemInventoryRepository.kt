package com.orderpos.repository

import com.orderpos.data.local.dao.InventoryDao
import com.orderpos.data.local.dao.MenuItemInventoryDao
import com.orderpos.data.local.entities.InventoryItem
import com.orderpos.data.local.entities.MenuItemInventory
import kotlinx.coroutines.flow.Flow

class MenuItemInventoryRepository(private val dao: MenuItemInventoryDao) {
    suspend fun insertMapping(mapping: MenuItemInventory) = dao.insert(mapping)
    fun getMappingByMenuItem(itemId: Long): Flow<List<MenuItemInventory>> =
        dao.getInventoryForMenuItem(itemId)
}

class InventoryRepository(private val dao: InventoryDao) {
    suspend fun insertInventoryItem(item: InventoryItem) = dao.insert(item)
    fun getInventoryItemsByRestaurant(restaurantId: Long): Flow<List<InventoryItem>> =
        dao.getInventoryItemsByRestaurant(restaurantId)
}
