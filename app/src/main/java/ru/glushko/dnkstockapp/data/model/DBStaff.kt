package ru.glushko.dnkstockapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "staff")
data class DBStaff(@PrimaryKey(autoGenerate = true) val id: Int,
                   @ColumnInfo(name = "surname") val surname: String,
                   @ColumnInfo(name = "name") val name: String,
                   @ColumnInfo(name = "lastname") val lastname: String)
