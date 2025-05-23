package com.example.contacts.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contacts.common.UIState
import com.example.contacts.data.model.ContactListResponse
import com.example.contacts.data.repository.ContactRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactViewModel(private val repository: ContactRepository) : ViewModel() {

    private val _newContacts = MutableLiveData<UIState<ContactListResponse>>(UIState.Loading)
    val newContacts: LiveData<UIState<ContactListResponse>> = _newContacts

    fun fetchContacts() {
        _newContacts.postValue(UIState.Loading)

        viewModelScope.launch(Dispatchers.IO) {
            _newContacts.postValue(repository.fetchContacts())
        }
    }
}