package com.example.myzulipapp.contacts.presentation.state

import com.example.myzulipapp.contacts.presentation.ui_model.ContactUIModel
import com.example.myzulipapp.core.Command
import com.example.myzulipapp.core.Event
import com.example.myzulipapp.core.State
import com.example.myzulipapp.utils.ImmutableList

data class ContactsState(
    val contacts: ImmutableList<ContactUIModel>? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isEmptyState: Boolean = true,
    val isSearchOpen: Boolean = false,
    val querySearch: String = "",
) : State

sealed class ContactsEvents : Event {
    sealed class Ui : ContactsEvents() {
        data object LoadContacts : Ui()
        data object OpenSearch : Ui()
        data class SearchContact(val query: String) : Ui()
        data class ClearSearchResult(val isSearchOpen: Boolean) : Ui()
    }

    sealed class Internal : ContactsEvents() {
        data class ContactListLoaded(val contactListUIModel: List<ContactUIModel>) : Internal()
        data class ErrorLoading(val error: Throwable) : Internal()
    }
}

sealed class ContactsCommand : Command {
    data object LoadContacts : ContactsCommand()
    data class SearchContact(val query: String) : ContactsCommand()
    data object ClearSearchContactResult : ContactsCommand()
}
