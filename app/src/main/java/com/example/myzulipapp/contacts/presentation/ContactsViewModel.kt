package com.example.myzulipapp.contacts.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myzulipapp.contacts.presentation.state.ContactsActor
import com.example.myzulipapp.contacts.presentation.state.ContactsEvents
import com.example.myzulipapp.contacts.presentation.state.ContactsReducer
import com.example.myzulipapp.contacts.presentation.state.ContactsState
import com.example.myzulipapp.core.BaseViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ContactsViewModel(
    private val reducer: ContactsReducer,
    private val actor: ContactsActor,
) : BaseViewModel<ContactsEvents, ContactsState>() {

    override val state: StateFlow<ContactsState>
        get() = reducer.state

    init {
        obtainEvent(ContactsEvents.Ui.LoadContacts)
    }

    fun obtainEvent(event: ContactsEvents.Ui) {
        when (event) {
            is ContactsEvents.Ui.LoadContacts -> {
                handleEvent(event)
            }

            is ContactsEvents.Ui.SearchContact -> {
                handleEvent(event)
            }

            is ContactsEvents.Ui.ClearSearchResult -> {
                handleEvent(event)
            }

            ContactsEvents.Ui.OpenSearch -> {
                openSearch(event)
            }
        }
    }

    private fun handleEvent(event: ContactsEvents) {
        viewModelScope.launch {
            val result = reducer.sendEvent(event)
            result.command?.let { actor.execute(it, ::handleEvent) }
        }
    }

    private fun openSearch(event: ContactsEvents.Ui) {
        reducer.sendEvent(event)
    }

    class Factory(
        private val reducer: ContactsReducer,
        private val actor: ContactsActor,
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == ContactsViewModel::class.java)
            return ContactsViewModel(
                reducer,
                actor
            ) as T
        }
    }
}
