package com.example.myzulipapp.channels.presentation.topics.state

import com.example.myzulipapp.core.Reducer
import com.example.myzulipapp.core.Result
import com.example.myzulipapp.utils.ImmutableList
import javax.inject.Inject

class StreamTopicsReducer @Inject constructor(
    state: StreamTopicsState
) : Reducer<StreamTopicsEvents, StreamTopicsState, StreamTopicsCommand>(state) {

    override fun reduce(
        event: StreamTopicsEvents,
        state: StreamTopicsState
    ): Result<StreamTopicsCommand> {
        return when (event) {
            is StreamTopicsEvents.Internal.StreamTopicsLoaded -> {
                setState(
                    state.copy(
                        streamTopics = ImmutableList(event.streamTopicsUIModel),
                        isLoading = event.streamTopicsUIModel.isEmpty()
                    )
                )
                val command = when (event.isFromCache) {
                    true -> {
                        StreamTopicsCommand.LoadStreamTopics(
                            streamId = event.streamId,
                            isFromCache = false
                        )
                    }

                    false -> {
                        null
                    }
                }
                Result(command)
            }

            is StreamTopicsEvents.Ui.LoadStreamTopics -> {
                setState(
                    state.copy(
                        isLoading = state.streamTopics?.list?.isEmpty() ?: false,
                        isEmptyState = false,
                    )
                )
                val command = StreamTopicsCommand.LoadStreamTopics(event.streamId, true)
                Result(command)
            }

            is StreamTopicsEvents.Internal.ErrorLoading -> {
                setState(
                    state.copy(
                        error = event.error.message,
                        isLoading = false,
                    )
                )
                Result(null)
            }

            is StreamTopicsEvents.Internal.SearchStreamTopicsLoaded -> {
                setState(
                    state.copy(
                        streamTopics = ImmutableList(event.streamTopicsUIModel),
                        isLoading = false
                    )
                )
                Result(null)
            }

            is StreamTopicsEvents.Ui.ClearSearchResult -> {
                setState(
                    state.copy(querySearch = "", isSearchOpen = event.isSearchOpen)
                )
                val command = StreamTopicsCommand.ClearSearchedStreamTopicsResult
                Result(command)
            }

            StreamTopicsEvents.Ui.OpenSearch -> {
                setState(
                    state.copy(isSearchOpen = true)
                )
                Result(null)
            }

            is StreamTopicsEvents.Ui.SearchStreamTopics -> {
                setState(
                    state.copy(isLoading = true, querySearch = event.query)
                )
                val command = StreamTopicsCommand.SearchStreamTopics(event.query)
                Result(command)
            }

            is StreamTopicsEvents.Ui.OpenTopicChat -> {
                Result(null)
            }
        }
    }
}
