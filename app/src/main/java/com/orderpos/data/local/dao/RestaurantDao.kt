package com.orderpos.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Embedded
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Relation
import androidx.room.Transaction
import androidx.room.Update
import com.orderpos.data.local.entities.AuditLog
import com.orderpos.data.local.entities.Category
import com.orderpos.data.local.entities.DeliveryOrder
import com.orderpos.data.local.entities.DeliveryZone
import com.orderpos.data.local.entities.Discount
import com.orderpos.data.local.entities.Employee
import com.orderpos.data.local.entities.EmployeeShift
import com.orderpos.data.local.entities.InventoryItem
import com.orderpos.data.local.entities.KOT
import com.orderpos.data.local.entities.KOTItem
import com.orderpos.data.local.entities.LoyaltyProgram
import com.orderpos.data.local.entities.LoyaltyTransaction
import com.orderpos.data.local.entities.MenuItem
import com.orderpos.data.local.entities.MenuItemInventory
import com.orderpos.data.local.entities.MenuItemOption
import com.orderpos.data.local.entities.MenuItemOptionChoice
import com.orderpos.data.local.entities.Order
import com.orderpos.data.local.entities.OrderHistory
import com.orderpos.data.local.entities.OrderItem
import com.orderpos.data.local.entities.OrderItemOption
import com.orderpos.data.local.entities.Payment
import com.orderpos.data.local.entities.Printer
import com.orderpos.data.local.entities.ReservationEntity
import com.orderpos.data.local.entities.Restaurant
import com.orderpos.data.local.entities.Shift
import com.orderpos.data.local.entities.SyncLog
import com.orderpos.data.local.entities.TaxGroup
import com.orderpos.data.local.entities.TaxRate
import com.orderpos.data.local.entities.User
import kotlinx.coroutines.flow.Flow

@Dao
interface RestaurantDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRestaurant(restaurant: Restaurant): Long

    @Update
    suspend fun updateRestaurant(restaurant: Restaurant)

    @Delete
    suspend fun deleteRestaurant(restaurant: Restaurant)

    @Query("SELECT * FROM restaurants ORDER BY name ASC")
    fun getAllRestaurants(): Flow<List<Restaurant>>

    @Query("SELECT * FROM restaurants WHERE restaurantId = :id")
    suspend fun getRestaurantById(id: Long): Restaurant?

    @Query("SELECT * FROM restaurants WHERE isActive = 1 ORDER BY name ASC")
    fun getActiveRestaurants(): Flow<List<Restaurant>>
}

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: Category): Long

    @Update
    suspend fun updateCategory(category: Category)

    @Delete
    suspend fun deleteCategory(category: Category)

    @Query("SELECT * FROM categories ORDER BY displayOrder ASC")
    fun getAllCategories(): Flow<List<Category>>

    @Query("SELECT * FROM categories WHERE restaurantId = :restaurantId ORDER BY displayOrder ASC")
    fun getCategoriesByRestaurant(restaurantId: Long): Flow<List<Category>>

    @Query("SELECT * FROM categories WHERE categoryId = :id")
    suspend fun getCategoryById(id: Long): Category?

    @Query("SELECT * FROM categories WHERE isActive = 1 AND restaurantId = :restaurantId")
    fun getActiveCategories(restaurantId: Long): Flow<List<Category>>
}

@Dao
interface MenuItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMenuItem(item: MenuItem): Long

    @Update
    suspend fun updateMenuItem(item: MenuItem)

    @Delete
    suspend fun deleteMenuItem(item: MenuItem)

    @Query("SELECT * FROM menu_items ORDER BY createdAt DESC")
    fun getAllMenuItems(): Flow<List<MenuItem>>

    @Query("SELECT * FROM menu_items WHERE categoryId = :categoryId AND isAvailable = 1")
    fun getMenuItemsByCategory(categoryId: Long): Flow<List<MenuItem>>

    @Query("SELECT * FROM menu_items WHERE itemId = :id")
    suspend fun getMenuItemById(id: Long): MenuItem?

    @Query("SELECT * FROM menu_items WHERE restaurantId = :restaurantId AND isAvailable = 1")
    fun getAvailableMenuItems(restaurantId: Long): Flow<List<MenuItem>>

    @Transaction
    @Query("SELECT * FROM menu_items WHERE itemId = :id")
    suspend fun getMenuItemWithOptions(id: Long): MenuItemWithOptions

}

data class MenuItemWithOptions(
    @Embedded val menuItem: MenuItem,
    @Relation(
        parentColumn = "itemId",
        entityColumn = "itemId"
    )
    val options: List<MenuItemOption>
)

@Dao
interface MenuItemOptionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOption(option: MenuItemOption): Long

    @Update
    suspend fun updateOption(option: MenuItemOption)

    @Delete
    suspend fun deleteOption(option: MenuItemOption)

    @Query("SELECT * FROM menu_item_options WHERE itemId = :itemId")
    fun getOptionsForItem(itemId: Long): Flow<List<MenuItemOption>>

    @Transaction
    @Query("SELECT * FROM menu_item_options WHERE optionId = :id")
    suspend fun getOptionWithChoices(id: Long): OptionWithChoices
}

data class OptionWithChoices(
    @Embedded val option: MenuItemOption,
    @Relation(
        parentColumn = "optionId",
        entityColumn = "optionId"
    )
    val choices: List<MenuItemOptionChoice>
)

@Dao
interface MenuItemOptionChoiceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChoice(choice: MenuItemOptionChoice): Long

    @Update
    suspend fun updateChoice(choice: MenuItemOptionChoice)

    @Delete
    suspend fun deleteChoice(choice: MenuItemOptionChoice)

    @Query("SELECT * FROM menu_item_option_choices WHERE optionId = :optionId")
    fun getChoicesForOption(optionId: Long): Flow<List<MenuItemOptionChoice>>
}

@Dao
interface OrderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: Order): Long

    @Update
    suspend fun updateOrder(order: Order)

    @Delete
    suspend fun deleteOrder(order: Order)

    @Query("SELECT * FROM orders WHERE status NOT IN ('COMPLETED', 'CANCELLED')")
    fun getActiveOrders(): Flow<List<Order>>

    @Query("SELECT * FROM orders WHERE orderId = :id")
    suspend fun getOrderById(id: Long): Order?

    @Query("SELECT * FROM orders WHERE tableId = :tableId AND status NOT IN ('COMPLETED', 'CANCELLED')")
    fun getActiveOrdersForTable(tableId: Long): Flow<List<Order>>

    @Transaction
    @Query("SELECT * FROM orders WHERE orderId = :id")
    suspend fun getOrderWithItems(id: Long): OrderWithItems
}

data class OrderWithItems(
    @Embedded val order: Order,
    @Relation(
        parentColumn = "orderId",
        entityColumn = "orderId"
    )
    val items: List<OrderItem>
)

@Dao
interface OrderItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrderItem(orderItem: OrderItem): Long

    @Update
    suspend fun updateOrderItem(orderItem: OrderItem)

    @Delete
    suspend fun deleteOrderItem(orderItem: OrderItem)

    @Query("SELECT * FROM order_items WHERE orderId = :orderId")
    fun getItemsForOrder(orderId: Long): Flow<List<OrderItem>>

    @Transaction
    @Query("SELECT * FROM order_items WHERE orderItemId = :id")
    suspend fun getOrderItemWithOptions(id: Long): OrderItemWithOptions
}

data class OrderItemWithOptions(
    @Embedded val orderItem: OrderItem,
    @Relation(
        parentColumn = "orderItemId",
        entityColumn = "orderItemId"
    )
    val options: List<OrderItemOption>
)

@Dao
interface OrderItemOptionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrderItemOption(option: OrderItemOption): Long

    @Update
    suspend fun updateOrderItemOption(option: OrderItemOption)

    @Delete
    suspend fun deleteOrderItemOption(option: OrderItemOption)

    @Query("SELECT * FROM order_item_options WHERE orderItemId = :orderItemId")
    fun getOptionsForOrderItem(orderItemId: Long): Flow<List<OrderItemOption>>
}

@Dao
interface OrderHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrderHistory(orderHistory: OrderHistory)

    @Query("SELECT * FROM order_history ORDER BY completedAt DESC")
    fun getOrderHistory(): Flow<List<OrderHistory>>

    @Query("SELECT * FROM order_history WHERE restaurantId = :restaurantId AND completedAt BETWEEN :startDate AND :endDate")
    fun getOrderHistoryByDateRange(restaurantId: Long, startDate: Long, endDate: Long): Flow<List<OrderHistory>>

    @Query("SELECT * FROM order_history WHERE orderId = :id")
    suspend fun getOrderHistoryById(id: Long): OrderHistory?
}

@Dao
interface PaymentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPayment(payment: Payment): Long

    @Update
    suspend fun updatePayment(payment: Payment)

    @Delete
    suspend fun deletePayment(payment: Payment)

    @Query("SELECT * FROM payments WHERE orderId = :orderId")
    fun getPaymentsForOrder(orderId: Long): Flow<List<Payment>>

    @Query("SELECT SUM(amount) FROM payments WHERE paymentTime BETWEEN :startDate AND :endDate AND restaurantId = :restaurantId")
    suspend fun getTotalPaymentsForPeriod(restaurantId: Long, startDate: Long, endDate: Long): Double

    @Query("SELECT * FROM payments WHERE paymentId = :id")
    suspend fun getPaymentById(id: Long): Payment?
}

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User): Long

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("SELECT * FROM users WHERE userId = :id")
    suspend fun getUserById(id: Long): User?

    @Query("SELECT * FROM users WHERE phone = :phone")
    suspend fun getUserByPhone(phone: String): User?

    @Query("SELECT * FROM users ORDER BY name ASC")
    fun getAllUsers(): Flow<List<User>>
}

@Dao
interface EmployeeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmployee(employee: Employee): Long

    @Update
    suspend fun updateEmployee(employee: Employee)

    @Delete
    suspend fun deleteEmployee(employee: Employee)

    @Query("SELECT * FROM employees WHERE employeeId = :id")
    suspend fun getEmployeeById(id: Long): Employee?

    @Query("SELECT * FROM employees WHERE pinCode = :pinCode AND isActive = 1")
    suspend fun getEmployeeByPin(pinCode: String): Employee?

    @Query("SELECT * FROM employees WHERE restaurantId = :restaurantId AND isActive = 1")
    fun getActiveEmployees(restaurantId: Long): Flow<List<Employee>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(employee: Employee)

    @Query("SELECT * FROM employees")
    fun getAllEmployees(): Flow<List<Employee>>
}

@Dao
interface DiscountDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDiscount(discount: Discount): Long

    @Update
    suspend fun updateDiscount(discount: Discount)

    @Delete
    suspend fun deleteDiscount(discount: Discount)

    @Query("SELECT * FROM discounts WHERE :currentTime BETWEEN startDate AND endDate AND isActive = 1")
    fun getActiveDiscounts(currentTime: Long = System.currentTimeMillis()): Flow<List<Discount>>

    @Query("SELECT * FROM discounts WHERE code = :code AND :currentTime BETWEEN startDate AND endDate AND isActive = 1")
    suspend fun getDiscountByCode(code: String, currentTime: Long = System.currentTimeMillis()): Discount?

    @Query("SELECT * FROM discounts WHERE restaurantId = :restaurantId")
    fun getDiscountsByRestaurant(restaurantId: Long): Flow<List<Discount>>
}

@Dao
interface InventoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInventoryItem(item: InventoryItem): Long

    @Update
    suspend fun updateInventoryItem(item: InventoryItem)

    @Delete
    suspend fun deleteInventoryItem(item: InventoryItem)

    @Query("SELECT * FROM inventory_items WHERE restaurantId = :restaurantId ORDER BY name ASC")
    fun getInventoryItems(restaurantId: Long): Flow<List<InventoryItem>>

    @Query("SELECT * FROM inventory_items WHERE currentStock <= alertThreshold")
    fun getLowStockItems(): Flow<List<InventoryItem>>

    @Query("SELECT * FROM inventory_items WHERE inventoryId = :id")
    suspend fun getInventoryItemById(id: Long): InventoryItem?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(inventoryItem: InventoryItem)

    @Query("SELECT * FROM inventory_items WHERE restaurantId = :restaurantId")
    fun getInventoryItemsByRestaurant(restaurantId: Long): Flow<List<InventoryItem>>

    @Query("SELECT * FROM inventory_items")
    fun getAll(): Flow<List<InventoryItem>>
}

@Dao
interface MenuItemInventoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMenuItemInventory(item: MenuItemInventory): Long

    @Update
    suspend fun updateMenuItemInventory(item: MenuItemInventory)

    @Delete
    suspend fun deleteMenuItemInventory(item: MenuItemInventory)

    @Query("SELECT * FROM menu_item_inventory WHERE itemId = :itemId")
    fun getInventoryForMenuItem(itemId: Long): Flow<List<MenuItemInventory>>

    @Query("SELECT * FROM menu_item_inventory WHERE inventoryId = :inventoryId")
    fun getMenuItemsUsingInventory(inventoryId: Long): Flow<List<MenuItemInventory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(menuItemInventory: MenuItemInventory)


}

@Dao
interface ShiftDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShift(shift: Shift): Long

    @Update
    suspend fun updateShift(shift: Shift)

    @Delete
    suspend fun deleteShift(shift: Shift)

    @Query("SELECT * FROM shifts WHERE restaurantId = :restaurantId ORDER BY startTime ASC")
    fun getShiftsByRestaurant(restaurantId: Long): Flow<List<Shift>>

    @Query("SELECT * FROM shifts WHERE shiftId = :id")
    suspend fun getShiftById(id: Long): Shift?
}

@Dao
interface EmployeeShiftDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmployeeShift(employeeShift: EmployeeShift): Long

    @Update
    suspend fun updateEmployeeShift(employeeShift: EmployeeShift)

    @Delete
    suspend fun deleteEmployeeShift(employeeShift: EmployeeShift)

    @Query("SELECT * FROM employee_shifts WHERE employeeId = :employeeId AND date = :date")
    suspend fun getEmployeeShiftForDate(employeeId: Long, date: Long): EmployeeShift?

    @Query("SELECT * FROM employee_shifts WHERE date = :date AND status = 'SCHEDULED'")
    fun getScheduledShiftsForDate(date: Long): Flow<List<EmployeeShift>>

    @Query("SELECT * FROM employee_shifts WHERE employeeId = :employeeId AND date BETWEEN :startDate AND :endDate")
    fun getEmployeeShiftsForPeriod(employeeId: Long, startDate: Long, endDate: Long): Flow<List<EmployeeShift>>
}

@Dao
interface KotDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKot(kot: KOT): Long

    @Update
    suspend fun updateKot(kot: KOT)

    @Delete
    suspend fun deleteKot(kot: KOT)

    @Query("SELECT * FROM kots WHERE status NOT IN ('SERVED', 'CANCELLED') AND kitchenStation = :station")
    fun getActiveKotsForStation(station: String): Flow<List<KOT>>

    @Query("SELECT * FROM kots WHERE orderId = :orderId")
    fun getKotsForOrder(orderId: Long): Flow<List<KOT>>

    @Transaction
    @Query("SELECT * FROM kots WHERE kotId = :id")
    suspend fun getKotWithItems(id: Long): KotWithItems
}

data class KotWithItems(
    @Embedded val kot: KOT,
    @Relation(
        parentColumn = "kotId",
        entityColumn = "kotId"
    )
    val items: List<KOTItem>
)

@Dao
interface KotItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKotItem(kotItem: KOTItem): Long

    @Update
    suspend fun updateKotItem(kotItem: KOTItem)

    @Delete
    suspend fun deleteKotItem(kotItem: KOTItem)

    @Query("SELECT * FROM kot_items WHERE kotId = :kotId")
    fun getItemsForKot(kotId: Long): Flow<List<KOTItem>>

    @Query("UPDATE kot_items SET status = :status WHERE kotItemId = :id")
    suspend fun updateKotItemStatus(id: Long, status: String)
}

@Dao
interface TaxGroupDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTaxGroup(taxGroup: TaxGroup): Long

    @Update
    suspend fun updateTaxGroup(taxGroup: TaxGroup)

    @Delete
    suspend fun deleteTaxGroup(taxGroup: TaxGroup)

    @Query("SELECT * FROM tax_groups WHERE restaurantId = :restaurantId")
    fun getTaxGroupsByRestaurant(restaurantId: Long): Flow<List<TaxGroup>>

    @Transaction
    @Query("SELECT * FROM tax_groups WHERE taxGroupId = :id")
    suspend fun getTaxGroupWithRates(id: Long): TaxGroupWithRates
}

data class TaxGroupWithRates(
    @Embedded val taxGroup: TaxGroup,
    @Relation(
        parentColumn = "taxGroupId",
        entityColumn = "taxGroupId"
    )
    val rates: List<TaxRate>
)

@Dao
interface TaxRateDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTaxRate(taxRate: TaxRate): Long

    @Update
    suspend fun updateTaxRate(taxRate: TaxRate)

    @Delete
    suspend fun deleteTaxRate(taxRate: TaxRate)

    @Query("SELECT * FROM tax_rates WHERE taxGroupId = :taxGroupId")
    fun getRatesForTaxGroup(taxGroupId: Long): Flow<List<TaxRate>>
}

@Dao
interface PrinterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPrinter(printer: Printer): Long

    @Update
    suspend fun updatePrinter(printer: Printer)

    @Delete
    suspend fun deletePrinter(printer: Printer)

    @Query("SELECT * FROM printers WHERE restaurantId = :restaurantId AND isActive = 1")
    fun getActivePrinters(restaurantId: Long): Flow<List<Printer>>

    @Query("SELECT * FROM printers WHERE printerType = :type AND restaurantId = :restaurantId AND isActive = 1")
    fun getPrintersByType(restaurantId: Long, type: String): Flow<List<Printer>>
}

@Dao
interface SyncLogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSyncLog(syncLog: SyncLog): Long

    @Query("SELECT * FROM sync_logs ORDER BY syncTime DESC LIMIT 1")
    suspend fun getLastSync(): SyncLog?

    @Query("SELECT * FROM sync_logs WHERE deviceId = :deviceId ORDER BY syncTime DESC LIMIT 1")
    suspend fun getLastSyncForDevice(deviceId: String): SyncLog?

    @Query("SELECT * FROM sync_logs WHERE restaurantId = :restaurantId ORDER BY syncTime DESC")
    fun getSyncHistory(restaurantId: Long): Flow<List<SyncLog>>
}

@Dao
interface AuditLogDao {
    @Insert
    suspend fun insertAuditLog(auditLog: AuditLog): Long

    @Query("SELECT * FROM audit_logs WHERE restaurantId = :restaurantId ORDER BY timestamp DESC")
    fun getAuditLogs(restaurantId: Long): Flow<List<AuditLog>>

    @Query("SELECT * FROM audit_logs WHERE entityType = :entityType AND entityId = :entityId ORDER BY timestamp DESC")
    fun getAuditLogsForEntity(entityType: String, entityId: Long): Flow<List<AuditLog>>

    @Query("SELECT * FROM audit_logs WHERE userId = :userId AND timestamp BETWEEN :start AND :end")
    fun getUserActivityLogs(userId: Long, start: Long, end: Long): Flow<List<AuditLog>>
}

@Dao
interface LoyaltyProgramDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLoyaltyProgram(program: LoyaltyProgram): Long

    @Update
    suspend fun updateLoyaltyProgram(program: LoyaltyProgram)

    @Query("SELECT * FROM loyalty_programs WHERE restaurantId = :restaurantId AND isActive = 1")
    suspend fun getActiveLoyaltyProgram(restaurantId: Long): LoyaltyProgram?

    @Query("SELECT * FROM loyalty_programs WHERE restaurantId = :restaurantId")
    fun getLoyaltyPrograms(restaurantId: Long): Flow<List<LoyaltyProgram>>
}

@Dao
interface LoyaltyTransactionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLoyaltyTransaction(transaction: LoyaltyTransaction): Long

    @Query("SELECT * FROM loyalty_transactions WHERE customerId = :customerId ORDER BY timestamp DESC")
    fun getCustomerLoyaltyHistory(customerId: Long): Flow<List<LoyaltyTransaction>>

    @Query("SELECT SUM(pointsEarned) - SUM(pointsRedeemed) FROM loyalty_transactions WHERE customerId = :customerId")
    suspend fun getCustomerLoyaltyBalance(customerId: Long): Int

    @Query("SELECT * FROM loyalty_transactions WHERE orderId = :orderId")
    suspend fun getLoyaltyTransactionsForOrder(orderId: Long): List<LoyaltyTransaction>
}

@Dao
interface DeliveryZoneDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDeliveryZone(zone: DeliveryZone): Long

    @Update
    suspend fun updateDeliveryZone(zone: DeliveryZone)

    @Delete
    suspend fun deleteDeliveryZone(zone: DeliveryZone)

    @Query("SELECT * FROM delivery_zones WHERE restaurantId = :restaurantId")
    fun getDeliveryZones(restaurantId: Long): Flow<List<DeliveryZone>>

    @Query("SELECT * FROM delivery_zones WHERE restaurantId = :restaurantId AND :address LIKE '%' || name || '%'")
    suspend fun findZoneForAddress(restaurantId: Long, address: String): DeliveryZone?
}

@Dao
interface DeliveryOrderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDeliveryOrder(deliveryOrder: DeliveryOrder)

    @Update
    suspend fun updateDeliveryOrder(deliveryOrder: DeliveryOrder)

    @Query("SELECT * FROM delivery_orders WHERE orderId = :orderId")
    suspend fun getDeliveryOrder(orderId: Long): DeliveryOrder?

    @Query("SELECT * FROM delivery_orders WHERE status = :status AND restaurantId = :restaurantId")
    fun getDeliveryOrdersByStatus(restaurantId: Long, status: String): Flow<List<DeliveryOrder>>

    @Query("UPDATE delivery_orders SET status = :status, driverId = :driverId WHERE orderId = :orderId")
    suspend fun updateDeliveryStatus(orderId: Long, status: String, driverId: Long?)
}

@Dao
interface ReservationDao {

    @Query("SELECT * FROM reservations WHERE status = :status")
    fun getDeliveryOrdersByStatus(status: String): Flow<List<ReservationEntity>>

    @Query("SELECT * FROM reservations ORDER BY customerName ASC")
    fun getReservations(): Flow<List<ReservationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(reservations: List<ReservationEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(reservations: ReservationEntity)

    @Query("UPDATE reservations SET status = :status WHERE id = :reservationId")
    suspend fun updateStatus(reservationId: String, status: String)
}

