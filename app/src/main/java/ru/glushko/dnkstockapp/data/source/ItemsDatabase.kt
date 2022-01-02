package ru.glushko.dnkstockapp.data.source

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.glushko.dnkstockapp.data.model.DBItem

@Database(entities = [DBItem::class],
    version = 4)
abstract class ItemsDatabase : RoomDatabase() {

    companion object {
        private var instance: ItemsDatabase? = null
        private val LOCK = Any()
        private const val DB_NAME = "items_test"

        fun getInstance(application: Application): ItemsDatabase {
            instance?.let {
                return it
            }

            synchronized(LOCK) {
                instance?.let {
                    return it
                }
            }
            val database = Room.databaseBuilder(application, ItemsDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()

            instance = database
            return database
        }
    }

    abstract fun userDao(): ItemDao
}