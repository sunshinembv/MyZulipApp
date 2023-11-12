package com.example.myzulipapp.network

import com.example.myzulipapp.channels.data.model.StreamIdData
import com.example.myzulipapp.channels.data.model.StreamRootData
import com.example.myzulipapp.channels.data.model.StreamTopicRootData
import com.example.myzulipapp.channels.data.model.SubscribedStreamRootData
import com.example.myzulipapp.chat.data.model.MessageResponseData
import com.example.myzulipapp.chat.data.model.MessagesRootData
import com.example.myzulipapp.chat.data.model.SingleMessageRootData
import com.example.myzulipapp.chat.data.model.UploadFileResponseData
import com.example.myzulipapp.contacts.data.model.PresenceRootData
import com.example.myzulipapp.contacts.data.model.PresencesData
import com.example.myzulipapp.contacts.data.model.UserData
import com.example.myzulipapp.contacts.data.model.UserListRootData
import com.example.myzulipapp.event_queue.data.model.EventQueueData
import com.example.myzulipapp.event_queue.data.model.EventRootData
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ZulipApi {

    //User
    @GET("api/v1/users")
    suspend fun getAllUsers(): Response<UserListRootData>

    @GET("api/v1/users/me")
    suspend fun getOwnUser(): Response<UserData>

    @GET("api/v1/users/{user_id}/presence")
    suspend fun getUserPresenceById(@Path("user_id") id: Int): Response<PresenceRootData>

    @GET("api/v1/realm/presence")
    suspend fun getPresenceAllUsers(): Response<PresencesData>

    @GET("api/v1/users/me/subscriptions")
    suspend fun getSubscribedStreams(): Response<SubscribedStreamRootData>

    @GET("api/v1/users/me/{stream_id}/topics")
    suspend fun getStreamTopicsById(@Path("stream_id") id: Int): Response<StreamTopicRootData>

    @POST("api/v1/users/me/subscriptions")
    suspend fun createStream(
        @Query("subscriptions") subscriptions: String,
    ): Response<Unit>

    @GET("api/v1/get_stream_id")
    suspend fun getStreamIdByName(
        @Query("stream") streamName: String
    ): Response<StreamIdData>

    @GET("api/v1/streams/{stream_id}")
    suspend fun getStreamById(
        @Path("stream_id") id: Int
    ): Response<StreamRootData>

    //Message
    @GET("api/v1/messages")
    suspend fun getMessages(
        @Query("anchor") anchor: Long,
        @Query("num_before") numBefore: Int,
        @Query("num_after") numAfter: Int,
        @Query("narrow") narrow: String,
        @Query("apply_markdown") applyMarkdown: Boolean,
        @Query("include_anchor") includeAnchor: Boolean = false
    ): Response<MessagesRootData>

    @GET("api/v1/messages/{message_id}")
    suspend fun getSingleMessageById(
        @Path("message_id") messageId: Int,
        @Query("apply_markdown") applyMarkdown: Boolean,
    ): Response<SingleMessageRootData>

    @FormUrlEncoded
    @POST("api/v1/messages")
    suspend fun sendMessage(
        @Field("type") type: String,
        @Field("to") streamId: Int,
        @Field("topic") topicName: String,
        @Field("content") message: String
    ): Response<MessageResponseData>

    @FormUrlEncoded
    @POST("api/v1/messages/{message_id}/reactions")
    suspend fun addReactionByMessageId(
        @Path("message_id") messageId: Int,
        @Field("emoji_name") emojiName: String,
        @Field("emoji_code") emojiCode: String,
    ): Response<Unit>

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "api/v1/messages/{message_id}/reactions", hasBody = true)
    suspend fun removeReactionByMessageId(
        @Path("message_id") messageId: Int,
        @Field("emoji_name") emojiName: String,
        @Field("emoji_code") emojiCode: String,
    ): Response<Unit>

    @Multipart
    @POST("api/v1/user_uploads")
    suspend fun uploadImage(@Part image: MultipartBody.Part): Response<UploadFileResponseData>

    //Real - time events
    @POST("api/v1/register")
    suspend fun registerEventQueue(
        @Query("event_types") eventTypes: String
    ): Response<EventQueueData>

    @GET("api/v1/events")
    suspend fun getEventsFromQueue(
        @Query("queue_id") queueId: String,
        @Query("last_event_id") lastEventId: Int
    ): Response<EventRootData>

    @DELETE("api/v1/events")
    suspend fun deleteEventQueue(
        @Query("queue_id") queueId: String,
    ): Response<Unit>
}
