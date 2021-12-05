package ru.glushko.dnkstockapp.domain.app

import android.app.Application

class AppInstance : Application() {

    companion object {
        var instance = AppInstance()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}