package ru.glushko.dnkstockapp.domain.usecases.archive

import ru.glushko.dnkstockapp.domain.model.ArchiveItem
import ru.glushko.dnkstockapp.domain.repositories.ArchiveItemRepository

class AddArchiveItemUseCase(private val _archiveItemRepository: ArchiveItemRepository) {
    suspend fun addArchiveItem(archiveItem: ArchiveItem) = _archiveItemRepository.addArchiveItem(archiveItem)
}