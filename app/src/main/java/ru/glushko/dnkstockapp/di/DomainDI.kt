package ru.glushko.dnkstockapp.di

import org.koin.dsl.module
import ru.glushko.dnkstockapp.domain.usecases.archive.AddArchiveItemUseCase
import ru.glushko.dnkstockapp.domain.usecases.archive.DeleteArchiveItemUseCase
import ru.glushko.dnkstockapp.domain.usecases.archive.LoadAllArchiveItemsUseCase
import ru.glushko.dnkstockapp.domain.usecases.item.*
import ru.glushko.dnkstockapp.domain.usecases.staff.AddStaffUseCase
import ru.glushko.dnkstockapp.domain.usecases.staff.DeleteStaffUseCase
import ru.glushko.dnkstockapp.domain.usecases.staff.LoadAllStaffUseCase
import ru.glushko.dnkstockapp.domain.usecases.staff.UpdateStaffUseCase
import ru.glushko.dnkstockapp.domain.usecases.stockitem.*

val domainModule = module {

    //------------------------Items-----------------------------//
    factory {
        AddItemUseCase(_itemRepository  = get())
    }

    factory {
        DeleteItemWithUpdateStockUseCase(_itemRepository = get())
    }

    factory {
        UpdateItemWithUpdateStockUseCases(_itemRepository = get())
    }

    factory {
        LoadConsumablesItemsUseCase(_itemRepository  = get())
    }

    factory {
        LoadHardwareItemsUseCase(_itemRepository  = get())
    }

    factory {
        AddItemWithUpdateStockUseCase(_itemRepository = get())
    }

    //----------------Stock Items----------------------------//
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

    factory {
        UpdateStockItemBalanceUseCase (_stockItemRepository = get())
    }

    //---------------------Staff-------------------------//
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

    //---------------------Archive Item-------------------------//
    factory {
        DeleteArchiveItemUseCase(_archiveItemRepository = get())
    }

    factory {
        AddArchiveItemUseCase(_archiveItemRepository = get())
    }

    factory {
        LoadAllArchiveItemsUseCase(_archiveItemRepository = get())
    }

}