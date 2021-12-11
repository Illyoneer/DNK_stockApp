package ru.glushko.dnkstockapp.di

import org.koin.dsl.module
import ru.glushko.dnkstockapp.domain.usecases.AddItemUseCase
import ru.glushko.dnkstockapp.domain.usecases.DeleteItemUseCase
import ru.glushko.dnkstockapp.domain.usecases.GetItemsListUseCase
import ru.glushko.dnkstockapp.domain.usecases.UpdateItemUseCase

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
}