package ru.glushko.dnkstockapp.data.source

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.glushko.dnkstockapp.data.model.DBItem
import ru.glushko.dnkstockapp.data.model.DBStaff
import ru.glushko.dnkstockapp.data.model.DBStockItem

@Database(
    entities = [DBItem::class, DBStockItem::class, DBStaff::class],

    version = 7
)
abstract class DNKStockDatabase : RoomDatabase() {

    companion object {
        private var instance: DNKStockDatabase? = null
        private val LOCK = Any()
        private const val DB_NAME = "DNKStockDatabase"

        fun getInstance(application: Application): DNKStockDatabase {
            instance?.let {
                return it
            }

            synchronized(LOCK) {
                instance?.let {
                    return it
                }
            }
            val database = Room.databaseBuilder(application, DNKStockDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()

            instance = database
            return database
        }
    }

    abstract fun itemDao(): ItemDao
    abstract fun stockItemDao(): StockItemDao
    abstract fun staffDao(): StaffDao
}