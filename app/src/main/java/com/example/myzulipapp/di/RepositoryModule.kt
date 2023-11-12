package com.example.myzulipapp.di

import com.example.myzulipapp.contacts.data.repository.UserRepositoryImpl
import com.example.myzulipapp.contacts.domain.repository.UserRepository
import com.example.myzulipapp.event_queue.data.repository.EventQueueRepositoryImpl
import com.example.myzulipapp.event_queue.domain.repository.EventQueueRepository
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

    @Binds
    fun provideUserRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    fun provideEventQueueRepository(impl: EventQueueRepositoryImpl): EventQueueRepository
}
