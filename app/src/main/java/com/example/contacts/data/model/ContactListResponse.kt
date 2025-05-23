package com.example.contacts.data.model

data class ContactListResponse(
    val data: Data,
    val success: Boolean
) {
    data class Data(
        val date: String,
        val totalUsers: Int,
        val users: List<Contact>
    )
}




