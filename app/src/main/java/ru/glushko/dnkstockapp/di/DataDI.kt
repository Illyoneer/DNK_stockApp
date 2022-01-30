package ru.glushko.dnkstockapp.di

import org.koin.dsl.module
import ru.glushko.dnkstockapp.app.AppInstance
import ru.glushko.dnkstockapp.data.mappers.ArchiveItemMapper
import ru.glushko.dnkstockapp.data.mappers.ItemMapper
import ru.glushko.dnkstockapp.data.mappers.StaffMapper
import ru.glushko.dnkstockapp.data.mappers.StockItemMapper
import ru.glushko.dnkstockapp.data.repositories.ArchiveItemRepositoryImpl
import ru.glushko.dnkstockapp.data.repositories.ItemRepositoryImpl
import ru.glushko.dnkstockapp.data.repositories.StaffRepositoryImpl
import ru.glushko.dnkstockapp.data.repositories.StockItemRepositoryImpl
import ru.glushko.dnkstockapp.data.source.DNKStockDatabase
import ru.glushko.dnkstockapp.domain.repositories.ArchiveItemRepository
import ru.glushko.dnkstockapp.domain.repositories.ItemRepository
import ru.glushko.dnkstockapp.domain.repositories.StaffRepository
import ru.glushko.dnkstockapp.domain.repositories.StockItemRepository

val dataModule = module {

    //----------------DAO--------------//
    single {
        DNKStockDatabase.getInstance(AppInstance.instance).itemDao()
    }

    single {
        DNKStockDatabase.getInstance(AppInstance.instance).stockItemDao()
    }

    single{
        DNKStockDatabase.getInstance(AppInstance.instance).staffDao()
    }

    single{
        DNKStockDatabase.getInstance(AppInstance.instance).archiveDao()
    }

    //----------------Mappers--------------//
     single {
         ItemMapper()
     }

    single {
        StockItemMapper()
    }

    single {
        StaffMapper()
    }

    single {
       ArchiveItemMapper()
    }

    //----------------Repositories--------------//
    single <ItemRepository>{
        ItemRepositoryImpl(_itemDao = get(), _mapper = get())
    }

    single <StockItemRepository>{
        StockItemRepositoryImpl(_stockItemDao = get(), _mapper = get())
    }

    single <StaffRepository> {
        StaffRepositoryImpl(_staffItemDao = get(), _mapper = get())
    }

    single <ArchiveItemRepository> {
        ArchiveItemRepositoryImpl(_archiveItemDao = get(), _mapper = get())
    }



}