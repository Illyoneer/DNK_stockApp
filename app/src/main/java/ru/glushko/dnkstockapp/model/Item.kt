package ru.glushko.dnkstockapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items_new")
data class Item (@PrimaryKey(autoGenerate = true) val id: Int,
                 @ColumnInfo(name = "item_name") val name: String,
                 @ColumnInfo(name = "item_count") val count: String,
                 @ColumnInfo(name = "issue_date") val date: String,
                 @ColumnInfo(name = "issued_name") val user: String)
