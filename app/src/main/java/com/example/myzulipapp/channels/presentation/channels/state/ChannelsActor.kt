package com.example.myzulipapp.channels.presentation.channels.state

import com.example.myzulipapp.channels.domain.usecases.CreateStreamUseCase
import com.example.myzulipapp.channels.domain.usecases.GetSubscribedStreamsUseCase
import com.example.myzulipapp.channels.presentation.channels.mapper.StreamUiMapper
import com.example.myzulipapp.channels.presentation.channels.ui_model.StreamUiModel
import com.example.myzulipapp.core.Actor
import com.example.myzulipapp.utils.search
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class ChannelsActor @Inject constructor(
    private val getSubscribedStreamsUseCase: GetSubscribedStreamsUseCase,
    private val createStreamUseCase: CreateStreamUseCase,
    private val mapper: StreamUiMapper,
) : Actor<ChannelsCommand, ChannelsEvents.Internal> {

    private var streamsUIModelCache = listOf<StreamUiModel>()

    override suspend fun execute(
        command: ChannelsCommand,
        onEvent: (ChannelsEvents.Internal) -> Unit
    ) {
        try {
            when (command) {
                is ChannelsCommand.CreateStream -> {
                    val stream =
                        createStreamUseCase.execute(command.streamName, command.streamDescription)
                    val streamUIModel = mapper.toStreamUiModel(stream)
                    streamsUIModelCache.plus(streamUIModel)
                    onEvent(ChannelsEvents.Internal.StreamsUpdated(streamUIModel))
                }

                is ChannelsCommand.LoadSubscribedStreams -> {
                    val streamsUiModel = mapper.toStreamsUiModel(
                        getSubscribedStreamsUseCase.execute(
                            command.isFromCache
                        )
                    )
                    streamsUIModelCache = streamsUiModel
                    onEvent(
                        ChannelsEvents.Internal.SubscribedStreamsLoaded(
                            streamsUiModel,
                            command.isFromCache
                        )
                    )
                }

                is ChannelsCommand.SearchSubscribedStream -> {
                    val streamsUiModel = search(
                        query = command.query,
                        searchCallback = { getSearchedSubscribedStreams(command.query) }
                    ).firstOrNull() ?: streamsUIModelCache
                    onEvent(ChannelsEvents.Internal.SearchSubscribedStreamsLoaded(streamsUiModel))
                }

                ChannelsCommand.ClearSearchedSubscribedStreamResult -> {
                    onEvent(
                        ChannelsEvents.Internal.SearchSubscribedStreamsLoaded(streamsUIModelCache)
                    )
                }
            }
        } catch (t: Throwable) {
            onEvent(ChannelsEvents.Internal.ErrorLoading(t))
        }
    }

    private fun getSearchedSubscribedStreams(query: String): List<StreamUiModel> {
        return streamsUIModelCache.filter {
            it.title.contains(query, ignoreCase = true)
        }
    }
}
