package com.example.myzulipapp.contacts.presentation.preview_data

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.myzulipapp.R
import com.example.myzulipapp.contacts.presentation.state.ContactsState
import com.example.myzulipapp.contacts.presentation.ui_model.ContactUIModel
import com.example.myzulipapp.utils.ImmutableList

private val contacts = listOf(
    ContactUIModel(
        0,
        "Name1",
        "Email1",
        R.string.online,
        "Icon1",
    ),
    ContactUIModel(
        1,
        "Name2",
        "Email2",
        R.string.online,
        "Icon2",
    ),
    ContactUIModel(
        2,
        "Name3",
        "Email3",
        R.string.online,
        "Icon3",
    )
)

private val contactsState = ContactsState(
    contacts = ImmutableList(contacts)
)

class ContactsStatePreviewParameterProvider : PreviewParameterProvider<ContactsState> {
    override val values: Sequence<ContactsState>
        get() = sequenceOf(contactsState)
}
