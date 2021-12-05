package ru.glushko.dnkstockapp.app

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