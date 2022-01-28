package ru.glushko.dnkstockapp.domain.model

data class InventoryItem(val id: Int = 0,
                         val name: String,
                         val count: Int,
                         val date: String,
                         val reason:String) //TODO: Переделать.
