package com.example.myzulipapp.contacts.di

import com.example.myzulipapp.contacts.domain.repository.UserRepository
import com.example.myzulipapp.contacts.presentation.ContactsViewModel
import dagger.Component

@Component(
    modules = [ContactsModule::class], dependencies = [ContactsDeps::class]
)
@ContactsScope
interface ContactsComponent {

    @Component.Factory
    interface Factory {
        fun create(
            contactsDeps: ContactsDeps
        ): ContactsComponent
    }

    fun getViewModel(): ContactsViewModel
}

interface ContactsDeps {
    fun userRepository(): UserRepository
}
