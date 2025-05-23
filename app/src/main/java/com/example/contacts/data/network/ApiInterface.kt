package com.example.contacts.data.network

import com.example.contacts.common.Const.BASE_URL
import com.example.contacts.data.model.ContactListResponse
import retrofit2.http.GET

interface ApiInterface {

    @GET(BASE_URL)
    suspend fun getContacts(): ContactListResponse

}