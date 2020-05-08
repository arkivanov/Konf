package com.arkivanov.konf.shared.sessionlist.model

internal data class SessionInfo(
    val id: String,
    val title: String?,
    val startDate: Long?,
    val endDate: Long?,
    val roomName: String?,
    val speakerName: String?,
    val companyName: String?
)
