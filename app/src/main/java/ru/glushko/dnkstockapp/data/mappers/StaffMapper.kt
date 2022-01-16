package ru.glushko.dnkstockapp.data.mappers

import ru.glushko.dnkstockapp.data.model.DBStaff
import ru.glushko.dnkstockapp.domain.entity.Staff

class StaffMapper {

    fun mapEntityToDBStaff(staff: Staff) = DBStaff(
        id = staff.id,
        surname = staff.surname,
        name = staff.name,
        lastname = staff.lastname
    ) //Мап элемента из Entity -> DB

    private fun mapDBStaffToEntity(dbStaff: DBStaff) = Staff(
        id = dbStaff.id,
        surname = dbStaff.surname,
        name = dbStaff.name,
        lastname = dbStaff.lastname
    ) //Мап элемента из DB -> Entity

    fun mapListDBStaffToListEntity(list: List<DBStaff>) = list.map{
        mapDBStaffToEntity(it)
    } //Мап каждого элемента списка из DB -> Entity и получение нового списка.

}