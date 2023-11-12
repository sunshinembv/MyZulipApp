package com.example.myzulipapp.contacts.presentation.state

import com.example.myzulipapp.contacts.domain.usecases.GetAllUsersUseCase
import com.example.myzulipapp.contacts.presentation.mapper.ContactUIMapper
import com.example.myzulipapp.contacts.presentation.ui_model.ContactUIModel
import com.example.myzulipapp.core.Actor
import com.example.myzulipapp.utils.search
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class ContactsActor @Inject constructor(
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val mapper: ContactUIMapper,
) : Actor<ContactsCommand, ContactsEvents.Internal> {

    private var channelsUIModelCache = listOf<ContactUIModel>()

    override suspend fun execute(
        command: ContactsCommand,
        onEvent: (ContactsEvents.Internal) -> Unit
    ) {
        try {
            when (command) {
                ContactsCommand.LoadContacts -> {
                    val contacts = mapper.toContactsUIModel(getAllUsersUseCase.execute())
                    channelsUIModelCache = contacts
                    onEvent(ContactsEvents.Internal.ContactListLoaded(contacts))
                }

                is ContactsCommand.SearchContact -> {
                    val contacts = search(
                        query = command.query,
                        searchCallback = { getSearchedContacts(command.query) }
                    ).firstOrNull() ?: channelsUIModelCache
                    onEvent(ContactsEvents.Internal.ContactListLoaded(contacts))
                }

                ContactsCommand.ClearSearchContactResult -> {
                    onEvent(ContactsEvents.Internal.ContactListLoaded(channelsUIModelCache))
                }
            }
        } catch (t: Throwable) {
            onEvent(ContactsEvents.Internal.ErrorLoading(t))
        }
    }

    private fun getSearchedContacts(query: String): List<ContactUIModel> {
        return channelsUIModelCache.filter {
            it.contactName.contains(query, ignoreCase = true)
        }
    }
}
