package ru.glushko.dnkstockapp.domain.usecases.staff

import ru.glushko.dnkstockapp.domain.model.Staff
import ru.glushko.dnkstockapp.domain.repositories.StaffRepository

class AddStaffUseCase(private val _staffRepository: StaffRepository) {
    suspend fun addStaff(staff: Staff) = _staffRepository.addStaff(staff)
}