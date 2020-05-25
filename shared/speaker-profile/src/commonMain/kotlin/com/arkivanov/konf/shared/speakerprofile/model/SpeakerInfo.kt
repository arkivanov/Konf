package com.arkivanov.konf.shared.speakerprofile.model

internal data class SpeakerInfo(
    val name: String?,
    val imageUrl: String?,
    val job: String?,
    val location: String?,
    val biography: String?,
    val twitterAccount: String?,
    val githubAccount: String?,
    val facebookAccount: String?,
    val linkedInAccount: String?,
    val mediumAccount: String?,
    val companyName: String?,
    val companyLogoUrl: String?
)
