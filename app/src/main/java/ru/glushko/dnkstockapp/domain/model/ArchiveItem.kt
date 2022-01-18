package ru.glushko.dnkstockapp.domain.model

data class ArchiveItem(val id: Int = 0,
                       val name: String,
                       val count: String,
                       val date: String,
                       val user: String,
                       val type:String)
