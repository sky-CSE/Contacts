package com.example.contacts.common

import com.example.contacts.data.model.Contact

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SharedPrefsManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("contact_app_prefs", Context.MODE_PRIVATE)

    private val gson = Gson()
    private val keyContacts = "contacts_list"

    private fun saveContacts(contacts: List<Contact>) {
        val sorted = contacts.sortedBy { it.fullName.lowercase() }
        val json = gson.toJson(sorted)
        prefs.edit().putString(keyContacts, json).apply()
    }

    fun getContacts(): ArrayList<Contact> {
        val json = prefs.getString(keyContacts, null) ?: return arrayListOf()
        val type = object : TypeToken<ArrayList<Contact>>() {}.type
        return gson.fromJson(json, type)
    }

    fun addContact(contact: Contact) {
        val list = getContacts()
        if (list.none { it.id == contact.id }) {
            list.add(contact)
            saveContacts(list)
        }
    }

    fun addContactsBulk(newContacts: List<Contact>) {
        val existing = getContacts()
        val uniqueNew = newContacts.filter { new ->
            existing.none { it.id == new.id }
        }
        existing.addAll(uniqueNew)
        saveContacts(existing)
    }

}
