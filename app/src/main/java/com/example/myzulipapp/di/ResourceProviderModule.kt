package com.example.myzulipapp.di

import com.example.myzulipapp.utils.ResourceProvider
import com.example.myzulipapp.utils.ResourceProviderImpl
import dagger.Binds
import dagger.Module

@Module
interface ResourceProviderModule {

    @Binds
    fun provideResourceProvider(impl: ResourceProviderImpl): ResourceProvider
}
