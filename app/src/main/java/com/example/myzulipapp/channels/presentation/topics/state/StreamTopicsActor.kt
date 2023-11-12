package com.example.myzulipapp.channels.presentation.topics.state

import com.example.myzulipapp.channels.domain.usecases.GetStreamTopicsUseCase
import com.example.myzulipapp.channels.presentation.topics.mapper.StreamTopicsUiMapper
import com.example.myzulipapp.channels.presentation.topics.ui_model.StreamTopicUiModel
import com.example.myzulipapp.core.Actor
import com.example.myzulipapp.utils.search
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class StreamTopicsActor @Inject constructor(
    private val getStreamTopicsUseCase: GetStreamTopicsUseCase,
    private val mapper: StreamTopicsUiMapper,
) : Actor<StreamTopicsCommand, StreamTopicsEvents.Internal> {

    private var streamTopicsUIModelCache = listOf<StreamTopicUiModel>()

    override suspend fun execute(
        command: StreamTopicsCommand,
        onEvent: (StreamTopicsEvents.Internal) -> Unit
    ) {
        try {
            when (command) {
                is StreamTopicsCommand.LoadStreamTopics -> {
                    val streamTopics =
                        getStreamTopicsUseCase.execute(command.streamId, command.isFromCache)
                    val streamTopicsUIModel = mapper.toStreamTopicsUiModel(
                        command.streamId,
                        streamTopics
                    )
                    streamTopicsUIModelCache = streamTopicsUIModel
                    onEvent(
                        StreamTopicsEvents.Internal.StreamTopicsLoaded(
                            command.streamId,
                            streamTopicsUIModel,
                            command.isFromCache
                        )
                    )
                }

                StreamTopicsCommand.ClearSearchedStreamTopicsResult -> {
                    onEvent(
                        StreamTopicsEvents.Internal.SearchStreamTopicsLoaded(
                            streamTopicsUIModelCache
                        )
                    )
                }

                is StreamTopicsCommand.SearchStreamTopics -> {
                    val streamTopicsUIModel = search(
                        query = command.query,
                        searchCallback = { getSearchedStreamTopics(command.query) }
                    ).firstOrNull() ?: streamTopicsUIModelCache
                    onEvent(StreamTopicsEvents.Internal.SearchStreamTopicsLoaded(streamTopicsUIModel))
                }
            }
        } catch (t: Throwable) {
            onEvent(StreamTopicsEvents.Internal.ErrorLoading(t))
        }
    }

    private fun getSearchedStreamTopics(query: String): List<StreamTopicUiModel> {
        return streamTopicsUIModelCache.filter {
            it.topicName.contains(query, ignoreCase = true)
        }
    }
}
