package ru.glushko.dnkstockapp.di

import org.koin.dsl.module
import ru.glushko.dnkstockapp.domain.usecases.item.*
import ru.glushko.dnkstockapp.domain.usecases.stockitem.AddStockItemUseCase
import ru.glushko.dnkstockapp.domain.usecases.stockitem.DeleteStockItemUseCase
import ru.glushko.dnkstockapp.domain.usecases.stockitem.LoadAllStockItemsUseCase
import ru.glushko.dnkstockapp.domain.usecases.stockitem.UpdateStockItemUseCase

val domainModule = module {

    factory {
        AddItemUseCase(_itemRepository  = get())
    }

    factory {
        DeleteItemUseCase(_itemRepository = get())
    }

    factory {
        UpdateItemUseCase(_itemRepository = get())
    }

    factory {
        GetConsumablesItemsUseCase(_itemRepository  = get())
    }

    factory {
        GetHardwareItemsUseCase(_itemRepository  = get())
    }


    factory {
        AddStockItemUseCase(_stockItemRepository = get())
    }

    factory {
        DeleteStockItemUseCase(_stockItemRepository = get())
    }

    factory {
        UpdateStockItemUseCase(_stockItemRepository = get())
    }

    factory {
        LoadAllStockItemsUseCase(_stockItemRepository = get())
    }
}