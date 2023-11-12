package com.example.myzulipapp.di

import android.content.Context
import androidx.room.Room
import com.example.myzulipapp.channels.data.db.dao.StreamDao
import com.example.myzulipapp.channels.data.db.dao.StreamTopicDao
import com.example.myzulipapp.chat.data.db.dao.MessageDao
import com.example.myzulipapp.chat.data.db.dao.ReactionDao
import com.example.myzulipapp.db.ZulipDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): ZulipDatabase {
        return Room.databaseBuilder(context, ZulipDatabase::class.java, ZulipDatabase.DB_NAME)
            .build()
    }

    @Provides
    @Singleton
    fun provideStreamDao(zulipDatabase: ZulipDatabase): StreamDao {
        return zulipDatabase.streamDao()
    }

    @Provides
    @Singleton
    fun provideStreamTopicDao(zulipDatabase: ZulipDatabase): StreamTopicDao {
        return zulipDatabase.streamTopicDao()
    }

    @Provides
    @Singleton
    fun provideMessageDao(zulipDatabase: ZulipDatabase): MessageDao {
        return zulipDatabase.messageDao()
    }

    @Provides
    @Singleton
    fun provideReactionsDao(zulipDatabase: ZulipDatabase): ReactionDao {
        return zulipDatabase.reactionDao()
    }
}
