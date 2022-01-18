package ru.glushko.dnkstockapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.glushko.dnkstockapp.data.model.DBStaff

@Dao
interface StaffDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addStaff(dbStaff: DBStaff)

    @Query("SELECT * FROM staff")
    fun loadAllStaff(): LiveData<List<DBStaff>>

    @Delete
    suspend fun deleteStaff(dbStaff: DBStaff)

    @Update
    suspend fun updateStaff(dbStaff: DBStaff)
}