package ru.glushko.dnkstockapp.di

import org.koin.dsl.module
import ru.glushko.dnkstockapp.app.AppInstance
import ru.glushko.dnkstockapp.data.mappers.ItemMapper
import ru.glushko.dnkstockapp.data.mappers.StockItemMapper
import ru.glushko.dnkstockapp.data.repositories.ItemRepositoryImpl
import ru.glushko.dnkstockapp.data.repositories.StockItemRepositoryImpl
import ru.glushko.dnkstockapp.data.source.DNKStockDatabase
import ru.glushko.dnkstockapp.domain.ItemRepository
import ru.glushko.dnkstockapp.domain.StockItemRepository

val dataModule = module {

    single {
        DNKStockDatabase.getInstance(AppInstance.instance).itemDao()
    }

    single {
        DNKStockDatabase.getInstance(AppInstance.instance).stockItemDao()
    }

     single {
         ItemMapper()
     }

    single {
        StockItemMapper()
    }

    single <ItemRepository>{
        ItemRepositoryImpl(_itemDao = get(), _mapper = get())
    }

    single <StockItemRepository>{
        StockItemRepositoryImpl(_stockItemDao = get(), _mapper = get())
    }


}