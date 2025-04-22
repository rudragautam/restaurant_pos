package com.orderpos.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "inventory_items")
data class InventoryItem(
    @PrimaryKey(autoGenerate = true) val inventoryId: Long = 0,
    val restaurantId: Long, // Foreign key
    val name: String,
    val unit: String, // kg, liter, piece, etc.
    val currentStock: Double,
    val alertThreshold: Double,
    val costPerUnit: Double,
    val supplier: String?
)

@Entity(tableName = "menu_item_inventory")
data class MenuItemInventory(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val itemId: Long, // Foreign key to MenuItem
    val inventoryId: Long, // Foreign key to InventoryItem
    val quantityUsed: Double // Amount used per serving
)