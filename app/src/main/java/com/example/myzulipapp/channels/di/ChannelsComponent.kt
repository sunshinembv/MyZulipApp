package com.example.myzulipapp.channels.di

import com.example.myzulipapp.channels.data.db.dao.StreamDao
import com.example.myzulipapp.channels.data.db.dao.StreamTopicDao
import com.example.myzulipapp.channels.presentation.channels.ChannelsViewModel
import com.example.myzulipapp.channels.presentation.topics.StreamTopicsViewModel
import com.example.myzulipapp.network.ZulipApi
import dagger.Component

@Component(
    modules = [ChannelsModule::class, StreamRepositoryModule::class],
    dependencies = [ChannelsDeps::class]
)
@ChannelsScope
interface ChannelsComponent {

    @Component.Factory
    interface Factory {
        fun create(
            channelsDeps: ChannelsDeps
        ): ChannelsComponent
    }

    fun getChannelsViewModel(): ChannelsViewModel
    fun getStreamTopicsViewModelAssistedFactory(): StreamTopicsViewModel.AssistedFactory
}

interface ChannelsDeps {
    fun zulipApi(): ZulipApi
    fun streamDao(): StreamDao
    fun streamTopicDao(): StreamTopicDao
}
