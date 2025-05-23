package com.example.contacts.di.modules

import com.example.contacts.data.repository.ContactRepository
import org.koin.dsl.module

val repositories = module {
    single { ContactRepository(get()) }
}