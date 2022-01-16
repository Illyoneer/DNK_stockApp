package ru.glushko.dnkstockapp.domain.usecases.staff

import ru.glushko.dnkstockapp.domain.entity.Staff
import ru.glushko.dnkstockapp.domain.repositories.StaffRepository

class UpdateStaffUseCase (private val _staffRepository: StaffRepository){
    suspend fun updateStaff(staff: Staff) = _staffRepository.updateStaff(staff)
}