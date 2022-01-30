package ru.glushko.dnkstockapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "archive_items")
data class DBArchiveItem (@PrimaryKey(autoGenerate = true) val id: Int,
                          @ColumnInfo(name = "archive_item_name") val name: String,
                          @ColumnInfo(name = "archive_item_count") val count: Int,
                          @ColumnInfo(name = "archive_item_date") val date: String,
                          @ColumnInfo(name = "archive_item_user") val user: String,
                          @ColumnInfo(name = "archive_item_type") val type:String)
