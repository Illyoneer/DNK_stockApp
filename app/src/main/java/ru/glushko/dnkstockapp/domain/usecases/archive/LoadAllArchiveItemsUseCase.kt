package ru.glushko.dnkstockapp.domain.usecases.archive

import androidx.lifecycle.LiveData
import ru.glushko.dnkstockapp.domain.model.ArchiveItem
import ru.glushko.dnkstockapp.domain.repositories.ArchiveItemRepository

class LoadAllArchiveItemsUseCase(private val _archiveItemRepository: ArchiveItemRepository) {
    fun loadAllArchiveItems(): LiveData<List<ArchiveItem>> = _archiveItemRepository.loadAllArchiveItems()
}