package com.example.myzulipapp.contacts.di

import com.example.myzulipapp.contacts.presentation.ContactsViewModel
import com.example.myzulipapp.contacts.presentation.state.ContactsActor
import com.example.myzulipapp.contacts.presentation.state.ContactsReducer
import com.example.myzulipapp.contacts.presentation.state.ContactsState
import dagger.Module
import dagger.Provides

@Module
class ContactsModule {

    @Provides
    @ContactsScope
    fun provideViewModel(reducer: ContactsReducer, actor: ContactsActor): ContactsViewModel {
        return ContactsViewModel.Factory(reducer, actor).create(ContactsViewModel::class.java)
    }

    @Provides
    @ContactsScope
    fun provideContactsReducer(): ContactsReducer {
        return ContactsReducer(ContactsState())
    }
}
