package com.example.contacts

import android.app.Application
import android.content.Context
import com.example.contacts.di.modules.apis
import com.example.contacts.di.modules.localModule
import com.example.contacts.di.modules.repositories
import com.example.contacts.di.modules.viewModels
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication: Application() {
    companion object {
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)
            modules(listOf(apis, repositories, viewModels, localModule))
        }

        appContext = applicationContext
    }
}