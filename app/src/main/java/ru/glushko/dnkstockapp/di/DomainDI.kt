package ru.glushko.dnkstockapp.di

import org.koin.dsl.module
import ru.glushko.dnkstockapp.domain.usecases.item.*
import ru.glushko.dnkstockapp.domain.usecases.staff.AddStaffUseCase
import ru.glushko.dnkstockapp.domain.usecases.staff.DeleteStaffUseCase
import ru.glushko.dnkstockapp.domain.usecases.staff.LoadAllStaffUseCase
import ru.glushko.dnkstockapp.domain.usecases.staff.UpdateStaffUseCase
import ru.glushko.dnkstockapp.domain.usecases.stockitem.AddStockItemUseCase
import ru.glushko.dnkstockapp.domain.usecases.stockitem.DeleteStockItemUseCase
import ru.glushko.dnkstockapp.domain.usecases.stockitem.LoadAllStockItemsUseCase
import ru.glushko.dnkstockapp.domain.usecases.stockitem.UpdateStockItemUseCase

val domainModule = module {

    //------------------------Items-----------------------------
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
        LoadConsumablesItemsUseCase(_itemRepository  = get())
    }

    factory {
        LoadHardwareItemsUseCase(_itemRepository  = get())
    }

    //----------------Stock Items----------------------------
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

    //-----------------Staff-------------------------
    factory {
        LoadAllStaffUseCase(_staffRepository = get())
    }

    factory {
        AddStaffUseCase(_staffRepository = get())
    }

    factory {
        UpdateStaffUseCase(_staffRepository = get())
    }

    factory {
        DeleteStaffUseCase(_staffRepository = get())
    }

}