package com.example.myzulipapp.contacts.data.mapper

import com.example.myzulipapp.contacts.data.model.AggregatedData
import com.example.myzulipapp.contacts.data.model.PresenceData
import com.example.myzulipapp.contacts.data.model.PresencesData
import com.example.myzulipapp.contacts.domain.model.Aggregated
import com.example.myzulipapp.contacts.domain.model.Presence
import com.example.myzulipapp.contacts.domain.model.Presences
import javax.inject.Inject

class PresenceMapper @Inject constructor() {

    fun toUserPresence(userPresenceData: PresenceData): Presence {
        return Presence(
            toAggregated(userPresenceData.aggregated)
        )
    }

    fun toPresences(presencesData: PresencesData): Presences {
        val newMap = presencesData.presences.mapValues { toUserPresence(it.value) }
        return Presences(newMap)
    }

    private fun toAggregated(aggregatedData: AggregatedData): Aggregated {
        return Aggregated(
            aggregatedData.status,
            aggregatedData.timestamp
        )
    }
}
