package com.arkivanov.konf.shared.sessiondetails

data class SessionDetailsViewModel(
    val isLoading: Boolean,
    val isError: Boolean,
    val title: String,
    val description: String,
    val imageUrl: String?,
    val level: Level,
    val info: String,
    val speakerName: String,
    val speakerAvatarUrl: String?,
    val speakerJobInfo: String,
    val speakerBiography: String,
    val speakerTwitterAccount: String?,
    val speakerGitHubAccount: String?,
    val speakerFacebookAccount: String?,
    val speakerLinkedInAccount: String?,
    val speakerMediumAccount: String?
) {
        enum class Level {
            NONE, BEGINNER, INTERMEDIATE, ADVANCED, EXPERT
        }
    }
