package ru.glushko.dnkstockapp.domain.usecases.staff

import androidx.lifecycle.LiveData
import ru.glushko.dnkstockapp.domain.entity.Staff
import ru.glushko.dnkstockapp.domain.repositories.StaffRepository

class LoadAllStaffUseCase (private val _staffRepository: StaffRepository) {
    fun loadAllStaff(): LiveData<List<Staff>> = _staffRepository.loadAllStaff()
}