package ru.glushko.dnkstockapp.domain.repositories

import androidx.lifecycle.LiveData
import ru.glushko.dnkstockapp.domain.entity.Staff

interface StaffRepository {
    fun loadAllStaff(): LiveData<List<Staff>>
    suspend fun addStaff(staff: Staff)
    suspend fun deleteStaff(staff: Staff)
    suspend fun updateStaff(staff: Staff)
}