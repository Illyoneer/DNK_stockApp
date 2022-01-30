package ru.glushko.dnkstockapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "split_items")
data class DBItem (@PrimaryKey(autoGenerate = true) val id: Int,
                   @ColumnInfo(name = "item_name") val name: String,
                   @ColumnInfo(name = "item_count") val count: Int,
                   @ColumnInfo(name = "item_date") val date: String,
                   @ColumnInfo(name = "item_user") val user: String,
                   @ColumnInfo(name = "item_type") val type:String)
