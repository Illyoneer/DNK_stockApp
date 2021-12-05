package ru.glushko.dnkstockapp.domain

data class Item (val id: Int = 0,
                 val name: String,
                 val count: String,
                 val date: String,
                 val user: String)