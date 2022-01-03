package ru.glushko.dnkstockapp.di

import org.koin.dsl.module
import ru.glushko.dnkstockapp.domain.usecases.*

val domainModule = module {

    factory {
        GetItemsListUseCase(itemRepository = get())
    }

    factory {
        AddItemUseCase(itemRepository = get())
    }

    factory {
        DeleteItemUseCase(itemRepository = get())
    }

    factory {
        UpdateItemUseCase(itemRepository = get())
    }

    factory {
        GetConsumablesItemsUseCase(itemRepository = get())
    }

    factory {
        GetHardwareItemsUseCase(itemRepository = get())
    }
}