package ru.glushko.dnkstockapp.domain.repositories

import androidx.lifecycle.LiveData
import ru.glushko.dnkstockapp.domain.model.ArchiveItem

interface ArchiveItemRepository {
    fun loadAllArchiveItems(): LiveData<List<ArchiveItem>>
    suspend fun addArchiveItem(archiveItem: ArchiveItem)
    suspend fun deleteArchiveItem(archiveItem: ArchiveItem)
}