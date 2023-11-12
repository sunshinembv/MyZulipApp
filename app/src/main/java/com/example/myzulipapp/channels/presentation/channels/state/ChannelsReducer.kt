package com.example.myzulipapp.channels.presentation.channels.state

import com.example.myzulipapp.core.Reducer
import com.example.myzulipapp.core.Result
import com.example.myzulipapp.utils.ImmutableList
import javax.inject.Inject

class ChannelsReducer @Inject constructor(state: ChannelsState) :
    Reducer<ChannelsEvents, ChannelsState, ChannelsCommand>(state) {

    override fun reduce(event: ChannelsEvents, state: ChannelsState): Result<ChannelsCommand> {
        return when (event) {
            is ChannelsEvents.Internal.ErrorLoading -> {
                setState(
                    state.copy(
                        error = event.error.message,
                        isLoading = false
                    )
                )
                Result(null)
            }

            is ChannelsEvents.Internal.SearchSubscribedStreamsLoaded -> {
                setState(
                    state.copy(
                        subscribedStreams = ImmutableList(event.streamsUIModel),
                        isLoading = false
                    )
                )
                Result(null)
            }

            is ChannelsEvents.Internal.StreamsUpdated -> {
                setState(
                    state.copy(
                        subscribedStreams = ImmutableList(
                            state.subscribedStreams?.list?.plus(event.subscribedStreamUiModel)
                                ?: emptyList()
                        )
                    )
                )
                Result(null)
            }

            is ChannelsEvents.Internal.SubscribedStreamsLoaded -> {
                setState(
                    state.copy(
                        subscribedStreams = ImmutableList(event.streamsUIModel),
                        isLoading = event.streamsUIModel.isEmpty()
                    )
                )
                val command = when (event.isFromCache) {
                    true -> {
                        ChannelsCommand.LoadSubscribedStreams(
                            isFromCache = false
                        )
                    }

                    false -> {
                        null
                    }
                }
                Result(command)
            }

            is ChannelsEvents.Ui.CreateStream -> {
                setState(
                    state.copy(
                        createStreamName = "",
                        createStreamDescription = ""
                    )
                )
                val command =
                    ChannelsCommand.CreateStream(event.streamName, event.streamDescription)
                Result(command)
            }

            is ChannelsEvents.Ui.LoadSubscribedStreams -> {
                setState(
                    state.copy(
                        isLoading = state.subscribedStreams?.list?.isEmpty() ?: false,
                        isEmptyState = false
                    )
                )
                val command = ChannelsCommand.LoadSubscribedStreams(event.isFromCache)
                Result(command)
            }

            is ChannelsEvents.Ui.OpenSearch -> {
                setState(
                    state.copy(isSearchOpen = true)
                )
                Result(null)
            }

            is ChannelsEvents.Ui.SearchSubscribedStream -> {
                setState(
                    state.copy(isLoading = true, querySearch = event.query)
                )
                val command = ChannelsCommand.SearchSubscribedStream(event.query)
                Result(command)
            }

            is ChannelsEvents.Ui.ClearSearchResult -> {
                setState(
                    state.copy(querySearch = "", isSearchOpen = event.isSearchOpen)
                )
                val command = ChannelsCommand.ClearSearchedSubscribedStreamResult
                Result(command)
            }

            is ChannelsEvents.Ui.OpenSubscribedStreamTopics -> {
                Result(null)
            }

            is ChannelsEvents.Ui.CreateStreamDescription -> {
                setState(state.copy(createStreamDescription = event.description))
                Result(null)
            }

            is ChannelsEvents.Ui.CreateStreamName -> {
                setState(state.copy(createStreamName = event.name))
                Result(null)
            }
        }
    }
}
