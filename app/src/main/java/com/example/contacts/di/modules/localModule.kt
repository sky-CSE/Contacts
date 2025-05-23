package com.example.contacts.di.modules

import com.example.contacts.common.SharedPrefsManager
import org.koin.dsl.module

val localModule = module {
    single { SharedPrefsManager(get()) }
}