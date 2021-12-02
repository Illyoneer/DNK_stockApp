package ru.glushko.dnkstockapp.model.provider

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.glushko.dnkstockapp.model.Item

@Database(entities = [Item::class], version = 2, exportSchema = false)
abstract class ItemsDatabase: RoomDatabase () {
    abstract fun userDao() : ItemDao
}