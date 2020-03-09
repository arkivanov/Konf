package com.arkivanov.konf.shared.sync.store

import com.arkivanov.konf.database.CompanyEntity
import com.arkivanov.konf.database.EventEntity
import com.arkivanov.konf.database.SessionEntity
import com.arkivanov.konf.database.SpeakerEntity

internal data class SyncData(
    val event: EventEntity,
    val companies: List<CompanyEntity>,
    val speakers: List<SpeakerEntity>,
    val sessions: List<SessionEntity>
)
