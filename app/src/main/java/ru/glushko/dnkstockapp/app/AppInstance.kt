package ru.glushko.dnkstockapp.app

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import ru.glushko.dnkstockapp.model.provider.ItemsDatabase
import ru.glushko.dnkstockapp.viewmodels.MainViewModel

class AppInstance : Application() {
    private lateinit var _database: ItemsDatabase

    companion object {
        var instance = AppInstance()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        _database = Room.databaseBuilder(applicationContext, ItemsDatabase::class.java, "items_new")
        .allowMainThreadQueries()
            .build()
    }

    fun getDatabase(): ItemsDatabase {
        return _database
    }

}