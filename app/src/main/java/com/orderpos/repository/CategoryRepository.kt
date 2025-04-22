package com.orderpos.repository


import com.orderpos.data.local.dao.CategoryDao
import com.orderpos.data.local.entities.Category
import com.orderpos.data.local.entities.Restaurant
import kotlinx.coroutines.flow.Flow

class CategoryRepository(private val dao: CategoryDao) {
    suspend fun insertCategory(category: Category) = dao.insertCategory(category)
    // Add other methods as needed

    fun getAllCategories(): Flow<List<Category>> {
        return dao.getAllCategories()
    }
}
