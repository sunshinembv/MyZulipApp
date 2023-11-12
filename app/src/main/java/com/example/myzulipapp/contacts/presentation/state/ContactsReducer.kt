package com.example.myzulipapp.contacts.presentation.state

import com.example.myzulipapp.core.Reducer
import com.example.myzulipapp.core.Result
import com.example.myzulipapp.utils.ImmutableList
import javax.inject.Inject

class ContactsReducer @Inject constructor(
    state: ContactsState
) : Reducer<ContactsEvents, ContactsState, ContactsCommand>(state) {

    override fun reduce(event: ContactsEvents, state: ContactsState): Result<ContactsCommand> {
        return when (event) {
            is ContactsEvents.Internal.ContactListLoaded -> {
                setState(
                    state.copy(
                        contacts = ImmutableList(event.contactListUIModel),
                        isLoading = false
                    )
                )
                Result(null)
            }

            is ContactsEvents.Internal.ErrorLoading -> {
                setState(
                    state.copy(
                        error = event.error.message,
                        isLoading = false
                    )
                )
                Result(null)
            }

            ContactsEvents.Ui.LoadContacts -> {
                setState(
                    state.copy(isLoading = true, isEmptyState = false)
                )
                val command = ContactsCommand.LoadContacts
                Result(command)
            }

            is ContactsEvents.Ui.SearchContact -> {
                setState(
                    state.copy(isLoading = true, querySearch = event.query)
                )
                val command = ContactsCommand.SearchContact(event.query)
                Result(command)
            }

            is ContactsEvents.Ui.ClearSearchResult -> {
                setState(
                    state.copy(querySearch = "", isSearchOpen = event.isSearchOpen)
                )
                val command = ContactsCommand.ClearSearchContactResult
                Result(command)
            }

            ContactsEvents.Ui.OpenSearch -> {
                setState(
                    state.copy(isSearchOpen = true)
                )
                Result(null)
            }
        }
    }
}
