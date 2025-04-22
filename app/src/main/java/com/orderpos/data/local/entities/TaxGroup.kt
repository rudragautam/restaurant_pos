package com.orderpos.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tax_groups")
data class TaxGroup(
    @PrimaryKey(autoGenerate = true) val taxGroupId: Long = 0,
    val restaurantId: Long,
    val name: String,
    val description: String?
)

@Entity(tableName = "tax_rates")
data class TaxRate(
    @PrimaryKey(autoGenerate = true) val taxRateId: Long = 0,
    val taxGroupId: Long,
    val name: String,
    val rate: Double, // Percentage
    val isInclusive: Boolean // Whether tax is included in price
)