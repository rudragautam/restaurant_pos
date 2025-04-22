package com.orderpos.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.orderpos.data.local.dao.AuditLogDao
import com.orderpos.data.local.dao.CategoryDao
import com.orderpos.data.local.dao.DeliveryOrderDao
import com.orderpos.data.local.dao.DeliveryZoneDao
import com.orderpos.data.local.dao.DiscountDao
import com.orderpos.data.local.dao.EmployeeDao
import com.orderpos.data.local.dao.EmployeeShiftDao
import com.orderpos.data.local.dao.InventoryDao
import com.orderpos.data.local.dao.KotDao
import com.orderpos.data.local.dao.KotItemDao
import com.orderpos.data.local.dao.LoyaltyProgramDao
import com.orderpos.data.local.dao.LoyaltyTransactionDao
import com.orderpos.data.local.dao.MenuItemDao
import com.orderpos.data.local.dao.MenuItemInventoryDao
import com.orderpos.data.local.dao.MenuItemOptionChoiceDao
import com.orderpos.data.local.dao.MenuItemOptionDao
import com.orderpos.data.local.dao.OrderDao
import com.orderpos.data.local.dao.OrderHistoryDao
import com.orderpos.data.local.dao.OrderItemDao
import com.orderpos.data.local.dao.OrderItemOptionDao
import com.orderpos.data.local.dao.PaymentDao
import com.orderpos.data.local.dao.PrinterDao
import com.orderpos.data.local.dao.ReservationDao
import com.orderpos.data.local.dao.RestaurantDao
import com.orderpos.data.local.dao.ShiftDao
import com.orderpos.data.local.dao.SyncLogDao
import com.orderpos.data.local.dao.TaxGroupDao
import com.orderpos.data.local.dao.TaxRateDao
import com.orderpos.data.local.dao.UserDao
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

//@Database(entities = [Category::class, CategoryTranslation::class], version = 2, exportSchema = false)
@Database(
    entities = [
        Restaurant::class,
        Category::class,
        MenuItem::class,
        MenuItemOption::class,
        MenuItemOptionChoice::class,
        Order::class,
        OrderItem::class,
        OrderItemOption::class,
        OrderHistory::class,
        Payment::class,
        User::class,
        Employee::class,
        Discount::class,
        InventoryItem::class,
        MenuItemInventory::class,
        Shift::class,
        EmployeeShift::class,
        KOT::class,
        KOTItem::class,
        TaxGroup::class,
        TaxRate::class,
        Printer::class,
        SyncLog::class,
        AuditLog::class,
        LoyaltyProgram::class,
        LoyaltyTransaction::class,
        DeliveryZone::class,
        DeliveryOrder::class, ReservationEntity::class
    ],
    version = 8,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun restaurantDao(): RestaurantDao
    abstract fun categoryDao(): CategoryDao
    abstract fun menuItemDao(): MenuItemDao
    abstract fun menuItemOptionDao(): MenuItemOptionDao
    abstract fun menuItemOptionChoiceDao(): MenuItemOptionChoiceDao
    abstract fun orderDao(): OrderDao
    abstract fun orderItemDao(): OrderItemDao
    abstract fun orderItemOptionDao(): OrderItemOptionDao
    abstract fun orderHistoryDao(): OrderHistoryDao
    abstract fun paymentDao(): PaymentDao
    abstract fun userDao(): UserDao
    abstract fun employeeDao(): EmployeeDao
    abstract fun discountDao(): DiscountDao
    abstract fun inventoryDao(): InventoryDao
    abstract fun menuItemInventoryDao(): MenuItemInventoryDao
    abstract fun shiftDao(): ShiftDao
    abstract fun employeeShiftDao(): EmployeeShiftDao
    abstract fun kotDao(): KotDao
    abstract fun kotItemDao(): KotItemDao
    abstract fun taxGroupDao(): TaxGroupDao
    abstract fun taxRateDao(): TaxRateDao
    abstract fun printerDao(): PrinterDao
    abstract fun syncLogDao(): SyncLogDao
    abstract fun auditLogDao(): AuditLogDao
    abstract fun loyaltyProgramDao(): LoyaltyProgramDao
    abstract fun loyaltyTransactionDao(): LoyaltyTransactionDao
    abstract fun deliveryZoneDao(): DeliveryZoneDao
    abstract fun deliveryOrderDao(): DeliveryOrderDao
    abstract fun reservation(): ReservationDao



    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "feedback_database"
                ).addMigrations(MIGRATION_1_2).build()
                INSTANCE = instance
                instance
            }
        }

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    "ALTER TABLE category_translation_table ADD COLUMN questionCategoryId INTEGER DEFAULT 0 NOT NULL",
                )
                db.execSQL(
                    "ALTER TABLE question_translation_table ADD COLUMN questionId INTEGER DEFAULT 0 NOT NULL",
                )
                db.execSQL(
                    "ALTER TABLE option_translation_table ADD COLUMN optionId INTEGER DEFAULT 0 NOT NULL",
                )

            }
        }

    }
}
