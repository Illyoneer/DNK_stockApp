package ru.glushko.dnkstockapp.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import ru.glushko.dnkstockapp.data.mappers.StaffMapper
import ru.glushko.dnkstockapp.data.source.StaffDao
import ru.glushko.dnkstockapp.domain.entity.Staff
import ru.glushko.dnkstockapp.domain.repositories.StaffRepository

class StaffRepositoryImpl(
    private val _staffItemDao: StaffDao,
    private val _mapper: StaffMapper
) : StaffRepository {

    override fun loadAllStaff(): LiveData<List<Staff>> =
        Transformations.map(_staffItemDao.loadAllStaff()) {
            _mapper.mapListDBStaffToListEntity(it)
        }

    override suspend fun addStaff(staff: Staff) =
        _staffItemDao.addStaff(_mapper.mapEntityToDBStaff(staff))

    override suspend fun deleteStaff(staff: Staff) =
        _staffItemDao.deleteStaff(_mapper.mapEntityToDBStaff(staff))

    override suspend fun updateStaff(staff: Staff) =
        _staffItemDao.updateStaff(_mapper.mapEntityToDBStaff(staff))

}