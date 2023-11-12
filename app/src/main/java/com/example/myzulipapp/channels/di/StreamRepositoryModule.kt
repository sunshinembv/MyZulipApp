package com.example.myzulipapp.channels.di

import com.example.myzulipapp.channels.data.repository.StreamRepositoryImpl
import com.example.myzulipapp.channels.domain.repository.StreamRepository
import dagger.Binds
import dagger.Module

@Module
interface StreamRepositoryModule {

    @Binds
    @ChannelsScope
    fun bindStreamRepository(impl: StreamRepositoryImpl): StreamRepository
}
