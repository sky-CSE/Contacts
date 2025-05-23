package com.example.contacts.di.modules

import com.example.contacts.ui.viewmodel.ContactViewModel
import org.koin.dsl.module

val viewModels = module {
    single { ContactViewModel(get()) }
}