package ru.glushko.dnkstockapp.domain.model

data class Item (val id: Int = 0,
                 val name: String,
                 val count: Int,
                 val date: String,
                 val user: String,
                 val type:String)
