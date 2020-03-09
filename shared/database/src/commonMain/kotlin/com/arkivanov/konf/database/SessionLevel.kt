package com.arkivanov.konf.database

enum class SessionLevel {

    BEGINNER, INTERMEDIATE, ADVANCED, EXPERT;

    companion object {
        val VALUES: List<SessionLevel> = values().toList()
    }
}
