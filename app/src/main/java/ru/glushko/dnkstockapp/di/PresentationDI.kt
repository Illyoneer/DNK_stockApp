package ru.glushko.dnkstockapp.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.glushko.dnkstockapp.presentation.viewmodels.MainViewModel


val presentationModule = module {
    viewModel {
        MainViewModel(
            _addItemUseCase = get(),
            _deleteItemUseCase = get(),
            _updateItemUseCase = get(),
            _getConsumablesItemsUseCase = get(),
            _getHardwareItemsUseCase = get()
        )
    }
}