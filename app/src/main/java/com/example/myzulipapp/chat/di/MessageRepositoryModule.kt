package com.example.myzulipapp.chat.di

import com.example.myzulipapp.chat.data.repository.MessageRepositoryImpl
import com.example.myzulipapp.chat.domain.repository.MessageRepository
import dagger.Binds
import dagger.Module

@Module
interface MessageRepositoryModule {

    @Binds
    @ChatScope
    fun provideMessageRepository(impl: MessageRepositoryImpl): MessageRepository
}
