package com.example.myzulipapp.chat.di

import com.example.myzulipapp.chat.data.db.dao.MessageDao
import com.example.myzulipapp.chat.data.db.dao.ReactionDao
import com.example.myzulipapp.chat.presentation.ChatViewModel
import com.example.myzulipapp.event_queue.domain.repository.EventQueueRepository
import com.example.myzulipapp.network.ZulipApi
import com.example.myzulipapp.utils.ResourceProvider
import dagger.Component

@Component(
    modules = [ChatModule::class, MessageRepositoryModule::class], dependencies = [ChatDeps::class]
)
@ChatScope
interface ChatComponent {

    @Component.Factory
    interface Factory {
        fun create(
            chatDeps: ChatDeps
        ): ChatComponent
    }

    fun getChatViewModelAssistedFactory(): ChatViewModel.AssistedFactory
}

interface ChatDeps {
    fun zulipApi(): ZulipApi
    fun messageDao(): MessageDao
    fun reactionDao(): ReactionDao
    fun eventQueueRepository(): EventQueueRepository
    fun resourceProvider(): ResourceProvider
}
