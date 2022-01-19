package ru.glushko.dnkstockapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stock_items2")
data class DBStockItem(@PrimaryKey(autoGenerate = true) val id: Int,
                       @ColumnInfo(name = "stock_item_name") val name: String,
                       @ColumnInfo(name = "stock_item_count") val count: String,
                       @ColumnInfo(name = "stock_item_balance") val balance: String)
