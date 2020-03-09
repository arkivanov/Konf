package com.arkivanov.konf.shared.sync.integration

import com.arkivanov.konf.database.CompanyEntity
import com.arkivanov.konf.database.EventEntity
import com.arkivanov.konf.database.SessionEntity
import com.arkivanov.konf.database.SessionLevel
import com.arkivanov.konf.database.SpeakerEntity
import com.arkivanov.konf.shared.sync.store.SyncData
import kotlinx.serialization.json.JsonObject

/*
 * By default generates random data, customize with your own data
 */
internal fun JsonObject.toSyncData(): SyncData {
    val sessions = ObjectGenerator<SessionEntity>()
    val speakers = ObjectGenerator<SpeakerEntity>()
    val companies = ObjectGenerator<CompanyEntity>()

    repeat(100) {
        sessions.generate {
            session(id = it, speakers = speakers, companies = companies)
        }
    }

    return SyncData(
        event = EventEntity.Impl(
            title = "Awesome Conference",
            description = "My awesome conference with logs of interesting sessions",
            startDate = 1767052800000, // 30/12/2025
            endDate = 1767052801000, // 31/12/2025
            locationLatitude = 30.0,
            locationLongitude = 50.0,
            locationDescription = "Some location",
            websiteUrl = "https://github.com/arkivanov/Konf"
        ),
        companies = companies.list,
        speakers = speakers.list,
        sessions = sessions.list
    )
}

private fun session(id: String, speakers: ObjectGenerator<SpeakerEntity>, companies: ObjectGenerator<CompanyEntity>): SessionEntity =
    SessionEntity.Impl(
        id = id,
        speakerId = speakers.generate { speaker(id = it, companies = companies) }.id,
        title = sentence(wordRange = 2..4, capitalizeWords = true),
        description = sentences(sentenceRange = 3..10, wordRange = 3..10),
        level = SessionLevel.VALUES.random()
    )

private fun speaker(id: String, companies: ObjectGenerator<CompanyEntity>): SpeakerEntity =
    SpeakerEntity.Impl(
        id = id,
        companyId = companies.generate(::company).id,
        name = sentence(wordRange = 2..3, capitalizeWords = true),
        avatarUrl = IMAGE_URL,
        imageUrl = IMAGE_URL,
        job = sentence(wordRange = 1..3, capitalizeWords = true),
        location = sentence(wordRange = 1..2, capitalizeWords = true),
        biography = sentences(sentenceRange = 1..5, wordRange = 3..8),
        twitterAccount = "@${word()}",
        githubAccount = "@${word()}",
        facebookAccount = "@${word()}",
        linkedInAccount = "@${word()}",
        mediumAccount = "@${word()}"
    )

private fun company(id: String): CompanyEntity =
    CompanyEntity.Impl(
        id = id,
        name = sentence(wordRange = 1..2, capitalizeWords = true),
        logoUrl = IMAGE_URL,
        websiteUrl = "https://github.com/arkivanov/Konf"
    )

private fun word(capitalize: Boolean = false): String =
    WORDS
        .random()
        .let { if (capitalize) it.capitalize() else it }

private fun sentence(wordRange: IntRange, capitalizeWords: Boolean = false): String =
    0.rangeTo(wordRange.random())
        .asSequence()
        .map { index -> word(capitalize = (index == 0) || capitalizeWords) }
        .joinToString(separator = " ")

private fun sentences(sentenceRange: IntRange, wordRange: IntRange): String =
    0.rangeTo(sentenceRange.random())
        .asSequence()
        .map { sentence(wordRange = wordRange) }
        .joinToString(separator = ". ")

private class ObjectGenerator<T> {
    private var index = 1
    val list = ArrayList<T>()

    fun generate(block: (id: String) -> T): T {
        val obj = block((index++).toString())
        list += obj

        return obj
    }
}

private val WORDS =
    listOf(
        "popcorn",
        "milky",
        "kneel",
        "righteous",
        "overt",
        "flag",
        "black",
        "tense",
        "sisters",
        "optimal",
        "throat",
        "achiever",
        "silent",
        "shelter",
        "tow",
        "woebegone",
        "broken",
        "record",
        "pretend",
        "alert",
        "acrid",
        "umbrella",
        "crooked",
        "memorise",
        "attraction",
        "refuse",
        "cough",
        "cautious",
        "wish",
        "comparison",
        "quill",
        "guarantee",
        "questionable",
        "development",
        "anxious",
        "plants",
        "hang",
        "develop",
        "dolls",
        "measure",
        "kill",
        "spiteful",
        "insidious",
        "lunchroom",
        "pass",
        "reward",
        "cent",
        "island",
        "regret",
        "ahead"
    )

private const val IMAGE_URL = "https://upload.wikimedia.org/wikipedia/commons/thumb/7/74/Kotlin-logo.svg/1200px-Kotlin-logo.svg.png"
