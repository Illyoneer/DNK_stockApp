package ru.glushko.dnkstockapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items_test")
data class DBItem (@PrimaryKey(autoGenerate = true) val id: Int,
                   @ColumnInfo(name = "item_name") val name: String,
                   @ColumnInfo(name = "item_count") val count: String,
                   @ColumnInfo(name = "item_date") val date: String,
                   @ColumnInfo(name = "item_user") val user: String)
