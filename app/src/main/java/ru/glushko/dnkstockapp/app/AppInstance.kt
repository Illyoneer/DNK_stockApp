package ru.glushko.dnkstockapp.app

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.glushko.dnkstockapp.di.dataModule
import ru.glushko.dnkstockapp.di.domainModule
import ru.glushko.dnkstockapp.di.presentationModule

class AppInstance : Application() {
    companion object {
        var instance = AppInstance()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        startKoin {
            androidContext(this@AppInstance)
            modules(listOf(presentationModule, domainModule, dataModule))
        }
    }
}