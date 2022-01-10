package ru.glushko.dnkstockapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stock_items")
data class DBStockItem(@PrimaryKey(autoGenerate = true) val id: Int,
                       @ColumnInfo(name = "item_name") val name: String,
                       @ColumnInfo(name = "item_count") val count: String)
