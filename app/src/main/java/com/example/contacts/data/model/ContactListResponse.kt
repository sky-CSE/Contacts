package com.example.contacts.data.model

import com.google.gson.annotations.SerializedName

data class ContactListResponse(
    @SerializedName("Data")
    val data: Data,

    @SerializedName("success")
    val success: Boolean
) {
    data class Data(
        val date: String,
        val totalUsers: Int,
        val users: List<Contact>
    )
}




