package com.orderpos.repository


import com.orderpos.data.local.dao.MenuItemDao
import com.orderpos.data.local.entities.MenuItem
import kotlinx.coroutines.flow.Flow

class MenuItemRepository(private val menuItemDao: MenuItemDao) {

    suspend fun insertMenuItem(menuItem: MenuItem) {
        menuItemDao.insertMenuItem(menuItem)
    }

    fun getAllMenuItems(): Flow<List<MenuItem>> {
        return menuItemDao.getAllMenuItems()
    }

    suspend fun getMenuItemById(itemId: Long): MenuItem? {
        return menuItemDao.getMenuItemById(itemId)
    }

    suspend fun deleteMenuItem(menuItem: MenuItem) {
        menuItemDao.deleteMenuItem(menuItem)
    }

    suspend fun updateMenuItem(menuItem: MenuItem) {
        menuItemDao.updateMenuItem(menuItem)
    }


}

