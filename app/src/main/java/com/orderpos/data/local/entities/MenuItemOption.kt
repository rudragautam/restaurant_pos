package com.orderpos.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "menu_item_options")
data class MenuItemOption(
    @PrimaryKey(autoGenerate = true) val optionId: Long = 0,
    val itemId: Long, // Foreign key
    val name: String,
    val isRequired: Boolean,
    val isMultiSelect: Boolean,
    val maxSelections: Int
)

@Entity(tableName = "menu_item_option_choices")
data class MenuItemOptionChoice(
    @PrimaryKey(autoGenerate = true) val choiceId: Long = 0,
    val optionId: Long, // Foreign key
    val name: String,
    val additionalPrice: Double,
    val isDefault: Boolean = false
)