package ru.glushko.dnkstockapp.domain.usecases.archive

import ru.glushko.dnkstockapp.domain.model.ArchiveItem
import ru.glushko.dnkstockapp.domain.repositories.ArchiveItemRepository

class DeleteArchiveItemUseCase(private val _archiveItemRepository: ArchiveItemRepository) {
    suspend fun deleteArchiveItem(archiveItem: ArchiveItem) = _archiveItemRepository.deleteArchiveItem(archiveItem)
}