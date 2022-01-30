package ru.glushko.dnkstockapp.domain.model

data class StockItem(val id: Int = 0,
                     val name: String,
                     val count: Int,
                     val balance: Int)
