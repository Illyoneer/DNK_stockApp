package ru.glushko.dnkstockapp.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import ru.glushko.dnkstockapp.data.dao.ArchiveItemDao
import ru.glushko.dnkstockapp.data.mappers.ArchiveItemMapper
import ru.glushko.dnkstockapp.domain.model.ArchiveItem
import ru.glushko.dnkstockapp.domain.repositories.ArchiveItemRepository

class ArchiveItemRepositoryImpl(
    private val _archiveItemDao: ArchiveItemDao,
    private val _mapper: ArchiveItemMapper
) : ArchiveItemRepository {
    override fun loadAllArchiveItems(): LiveData<List<ArchiveItem>> =
        Transformations.map(_archiveItemDao.loadAllArchiveItems()) {
            _mapper.mapListDBArchiveItemToListEntity(it)
        }

    override suspend fun addArchiveItem(archiveItem: ArchiveItem) =
        _archiveItemDao.addArchiveItem(_mapper.mapEntityToDBArchiveItem(archiveItem))

    override suspend fun deleteArchiveItem(archiveItem: ArchiveItem) =
        _archiveItemDao.deleteArchiveItem(_mapper.mapEntityToDBArchiveItem(archiveItem))

}