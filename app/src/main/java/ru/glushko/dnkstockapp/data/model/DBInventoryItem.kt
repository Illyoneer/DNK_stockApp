package ru.glushko.dnkstockapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "inventory_items")
data class DBInventoryItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "inventory_name") val name: String,
    @ColumnInfo(name = "inventory_count")val count: String,
    @ColumnInfo(name = "inventory_date")val date: String,
    @ColumnInfo(name = "inventory_reason")val reason: String
) //TODO: Переделать.
