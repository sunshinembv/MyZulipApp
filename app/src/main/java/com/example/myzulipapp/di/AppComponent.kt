package com.example.myzulipapp.di

import android.content.Context
import com.example.myzulipapp.channels.data.db.dao.StreamDao
import com.example.myzulipapp.channels.data.db.dao.StreamTopicDao
import com.example.myzulipapp.channels.di.ChannelsDeps
import com.example.myzulipapp.chat.data.db.dao.MessageDao
import com.example.myzulipapp.chat.data.db.dao.ReactionDao
import com.example.myzulipapp.chat.di.ChatDeps
import com.example.myzulipapp.contacts.di.ContactsDeps
import com.example.myzulipapp.contacts.domain.repository.UserRepository
import com.example.myzulipapp.event_queue.domain.repository.EventQueueRepository
import com.example.myzulipapp.network.ZulipApi
import com.example.myzulipapp.splash_screen.di.SplashDeps
import com.example.myzulipapp.utils.ResourceProvider
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        NetworkModule::class,
        RoomModule::class,
        ResourceProviderModule::class,
        RepositoryModule::class
    ]
)
@Singleton
interface AppComponent : ChannelsDeps, ContactsDeps, ChatDeps, SplashDeps {


    override fun zulipApi(): ZulipApi

    override fun streamDao(): StreamDao
    override fun streamTopicDao(): StreamTopicDao
    override fun messageDao(): MessageDao
    override fun reactionDao(): ReactionDao

    override fun resourceProvider(): ResourceProvider

    override fun userRepository(): UserRepository
    override fun eventQueueRepository(): EventQueueRepository


    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context
        ): AppComponent
    }
}
