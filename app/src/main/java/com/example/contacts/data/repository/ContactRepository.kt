package com.example.contacts.data.repository

import com.example.contacts.common.UIState
import com.example.contacts.data.model.ContactListResponse
import com.example.contacts.data.network.ApiInterface

class ContactRepository(private val api: ApiInterface) {

    suspend fun fetchContacts(): UIState<ContactListResponse> {
        return try {
            val response = api.getContacts()
            UIState.Success(response)
        } catch (e: Exception) {
            UIState.Failure(e)
        }
    }
}