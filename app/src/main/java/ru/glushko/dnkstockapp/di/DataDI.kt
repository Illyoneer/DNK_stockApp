package ru.glushko.dnkstockapp.di

import org.koin.dsl.module
import ru.glushko.dnkstockapp.app.AppInstance
import ru.glushko.dnkstockapp.data.mapper.ItemMapper
import ru.glushko.dnkstockapp.data.repository.ItemRepositoryImpl
import ru.glushko.dnkstockapp.data.source.ItemsDatabase
import ru.glushko.dnkstockapp.domain.ItemRepository

val dataModule = module {

    single {
        ItemsDatabase.getInstance(AppInstance.instance).userDao()
    }

     single {
         ItemMapper()
     }

    single <ItemRepository>{
        ItemRepositoryImpl(_itemDao = get(), mapper = get())
    }
}