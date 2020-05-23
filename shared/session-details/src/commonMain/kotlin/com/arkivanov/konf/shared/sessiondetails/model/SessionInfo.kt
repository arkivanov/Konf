package com.arkivanov.konf.shared.sessiondetails.model

import com.arkivanov.konf.database.SessionLevel

internal data class SessionInfo(
    val title: String?,
    val description: String?,
    val imageUrl: String?,
    val level: SessionLevel?,
    val startDate: Long?,
    val endDate: Long?,
    val roomName: String?,
    val speakerId: String?,
    val speakerName: String?,
    val speakerAvatarUrl: String?,
    val speakerJob: String?,
    val speakerCompanyName: String?,
    val eventTimeZone: String?
)
