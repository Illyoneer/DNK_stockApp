package ru.glushko.dnkstockapp.domain.usecases.staff

import ru.glushko.dnkstockapp.domain.entity.Staff
import ru.glushko.dnkstockapp.domain.repositories.StaffRepository

class DeleteStaffUseCase (private val _staffRepository: StaffRepository){
    suspend fun deleteStaff(staff: Staff) = _staffRepository.deleteStaff(staff)
}