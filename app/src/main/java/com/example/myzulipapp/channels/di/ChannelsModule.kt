package com.example.myzulipapp.channels.di

import com.example.myzulipapp.channels.presentation.channels.ChannelsViewModel
import com.example.myzulipapp.channels.presentation.channels.state.ChannelsActor
import com.example.myzulipapp.channels.presentation.channels.state.ChannelsReducer
import com.example.myzulipapp.channels.presentation.channels.state.ChannelsState
import com.example.myzulipapp.channels.presentation.topics.StreamTopicsViewModel
import com.example.myzulipapp.channels.presentation.topics.state.StreamTopicsActor
import com.example.myzulipapp.channels.presentation.topics.state.StreamTopicsReducer
import com.example.myzulipapp.channels.presentation.topics.state.StreamTopicsState
import dagger.Module
import dagger.Provides

@Module
class ChannelsModule {

    @Provides
    @ChannelsScope
    fun provideChannelsViewModel(
        reducer: ChannelsReducer,
        actor: ChannelsActor
    ): ChannelsViewModel {
        return ChannelsViewModel.Factory(reducer, actor).create(ChannelsViewModel::class.java)
    }

    @Provides
    @ChannelsScope
    fun provideStreamTopicsViewModelAssistedFactory(
        reducer: StreamTopicsReducer,
        actor: StreamTopicsActor
    ): StreamTopicsViewModel.AssistedFactory {
        return StreamTopicsViewModel.AssistedFactory(reducer, actor)
    }

    @Provides
    @ChannelsScope
    fun provideChannelsReducer(): ChannelsReducer {
        return ChannelsReducer(ChannelsState())
    }

    @Provides
    @ChannelsScope
    fun provideStreamTopicsReducer(): StreamTopicsReducer {
        return StreamTopicsReducer(StreamTopicsState())
    }
}
