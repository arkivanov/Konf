package com.arkivanov.konf.shared.sync.integration.mappings

import com.arkivanov.konf.database.CompanyEntity
import com.arkivanov.konf.database.EventEntity
import com.arkivanov.konf.database.RoomEntity
import com.arkivanov.konf.database.SessionEntity
import com.arkivanov.konf.database.SessionLevel
import com.arkivanov.konf.database.SpeakerEntity
import com.arkivanov.konf.shared.sync.store.SyncData
import kotlinx.serialization.json.JsonObject
import kotlin.random.Random

/*
 * By default generates random data, customize with your own mapping
 */
internal val jsonObjectToSyncData: JsonObject.() -> SyncData =
    {
        val sessions = ObjectGenerator<SessionEntity>()
        val speakers = ObjectGenerator<SpeakerEntity>()
        val companies = ObjectGenerator<CompanyEntity>()
        val rooms = ObjectGenerator<RoomEntity>()

        repeat(ROOM_COUNT) { rooms.generate(::room) }

        repeat(SESSION_COUNT) {
            sessions.generate {
                session(index = it, speakers = speakers, companies = companies, rooms = rooms)
            }
        }

        SyncData(
            event = EventEntity(
                title = "Awesome Conference",
                description = "My awesome conference with logs of interesting sessions",
                imageUrl = IMAGE_URL,
                startDate = EVENT_START_DATE,
                endDate = EVENT_START_DATE + (EVENT_DAY_COUNT - 1).toLong() * DAY_IN_MILLIS,
                timeZone = EVENT_TIME_ZONE,
                locationLatitude = 30.0,
                locationLongitude = 50.0,
                locationDescription = "Some location",
                websiteUrl = "https://github.com/arkivanov/Konf"
            ),
            companies = companies.list,
            speakers = speakers.list,
            rooms = rooms.list,
            sessions = sessions.list
        )
    }

private const val EVENT_START_DATE = 1626220800000L // 14/07/2021
private const val EVENT_DAY_COUNT = 2
private const val ROOM_COUNT = 5
private const val SESSION_COUNT = 100
private const val HOURS_PER_SESSION_DAY = 9
private const val SESSION_DAY_START_HOUR = 9
private const val EVENT_TIME_ZONE_OFFSET = +3
private const val EVENT_TIME_ZONE = "GMT+3"
private const val HOUR_IN_MILLIS = 60L * 60L * 1000L
private const val DAY_IN_MILLIS = HOUR_IN_MILLIS * 24L

private fun getRandomDateTimeDuringEvent(index: Int): Long {
    val hourIndex = (EVENT_DAY_COUNT * HOURS_PER_SESSION_DAY * index) / SESSION_COUNT
    val dayIndex = hourIndex / HOURS_PER_SESSION_DAY
    val hour = SESSION_DAY_START_HOUR + hourIndex - dayIndex * HOURS_PER_SESSION_DAY

    return EVENT_START_DATE + dayIndex * DAY_IN_MILLIS + (hour - EVENT_TIME_ZONE_OFFSET) * HOUR_IN_MILLIS
}

private fun getId(index: Int): String = (index + 1).toString()

private fun session(
    index: Int,
    speakers: ObjectGenerator<SpeakerEntity>,
    companies: ObjectGenerator<CompanyEntity>,
    rooms: ObjectGenerator<RoomEntity>
): SessionEntity =
    SessionEntity(
        id = getId(index),
        speakerId = speakers.generate { speaker(index = it, companies = companies) }.id,
        roomId = rooms.list.random().id,
        title = sentence(wordRange = 2..6, capitalizeWords = true),
        description = sentences(sentenceRange = 5..30, wordRange = 3..10),
        imageUrl = IMAGE_URL,
        startDate = getRandomDateTimeDuringEvent(index = index),
        endDate = getRandomDateTimeDuringEvent(index = index) + 40L * 60L * 1000L,
        level = SessionLevel.VALUES.random()
    )

private fun speaker(index: Int, companies: ObjectGenerator<CompanyEntity>): SpeakerEntity =
    SpeakerEntity(
        id = getId(index),
        companyId = companies.generate(::company).id,
        name = sentence(wordRange = 2..3, capitalizeWords = true),
        avatarUrl = IMAGE_URL.takeIf { Random.nextBoolean() },
        job = sentence(wordRange = 1..3, capitalizeWords = true),
        location = sentence(wordRange = 1..2, capitalizeWords = true),
        biography = sentences(sentenceRange = 1..5, wordRange = 3..8),
        twitterAccount = word(),
        githubAccount = word(),
        facebookAccount = word(),
        linkedInAccount = word(),
        mediumAccount = word()
    )

private fun company(index: Int): CompanyEntity =
    CompanyEntity(
        id = getId(index),
        name = sentence(wordRange = 1..2, capitalizeWords = true),
        logoUrl = IMAGE_URL,
        websiteUrl = "https://github.com/arkivanov/Konf"
    )

private fun room(index: Int): RoomEntity =
    RoomEntity(
        id = getId(index),
        name = word(capitalize = true)
    )

private fun word(capitalize: Boolean = false): String =
    WORDS
        .random()
        .let { if (capitalize) it.capitalize() else it }

private fun sentence(wordRange: IntRange, capitalizeWords: Boolean = false): String =
    (0 until wordRange.random())
        .asSequence()
        .map { index -> word(capitalize = (index == 0) || capitalizeWords) }
        .joinToString(separator = " ")

private fun sentences(sentenceRange: IntRange, wordRange: IntRange): String =
    (0 until sentenceRange.random())
        .asSequence()
        .map { sentence(wordRange = wordRange) }
        .joinToString(separator = ". ", postfix = ".")

private class ObjectGenerator<T> {
    private var index = 0
    val list = ArrayList<T>()

    fun generate(block: (index: Int) -> T): T {
        val obj = block(index++)
        list += obj

        return obj
    }
}

private const val IMAGE_URL = "https://upload.wikimedia.org/wikipedia/commons/thumb/7/74/Kotlin-logo.svg/1200px-Kotlin-logo.svg.png"

private val WORDS =
    listOf(
        "choice",
        "impress",
        "coat",
        "thought",
        "display",
        "cousin",
        "resolution",
        "document",
        "mine",
        "activity",
        "paint",
        "horseshoe",
        "charity",
        "heat",
        "training",
        "tone",
        "multiply",
        "favourite",
        "baby",
        "like",
        "harvest",
        "delay",
        "tell",
        "video",
        "tourist",
        "offer",
        "emphasis",
        "royalty",
        "velvet",
        "clothes",
        "presidential",
        "kinship",
        "accompany",
        "nonremittal",
        "diet",
        "incredible",
        "cherry",
        "gold",
        "green",
        "throw",
        "separation",
        "union",
        "inject",
        "pause",
        "oven",
        "bird",
        "desert",
        "even",
        "collect",
        "smile",
        "tumour",
        "rest",
        "characteristic",
        "relative",
        "cigarette",
        "log",
        "beef",
        "magnitude",
        "ensure",
        "frighten",
        "suntan",
        "quarter",
        "school",
        "credit card",
        "raise",
        "dimension",
        "coat",
        "net",
        "helmet",
        "precede",
        "elaborate",
        "toss",
        "cabin",
        "weigh",
        "investment",
        "admiration",
        "ant",
        "truth",
        "reception",
        "carrot",
        "theater",
        "calf",
        "inn",
        "pension",
        "sketch",
        "flawed",
        "housewife",
        "personality",
        "invasion",
        "invite",
        "entitlement",
        "criminal",
        "coast",
        "design",
        "admire",
        "majority",
        "reproduce",
        "role",
        "owl",
        "cellar",
        "acquit",
        "lid",
        "lonely",
        "slip",
        "slide",
        "giant",
        "cross",
        "Bible",
        "sense",
        "attention",
        "introduce",
        "canvas",
        "injection",
        "monarch",
        "defendant",
        "blank",
        "headquarters",
        "storm",
        "ensure",
        "purpose",
        "exemption",
        "thought",
        "acid",
        "prevent",
        "crown",
        "council",
        "ridge",
        "spray",
        "equip",
        "Mars",
        "percent",
        "digital",
        "rebel",
        "rest",
        "cassette",
        "discount",
        "sleeve",
        "established",
        "tooth",
        "pitch",
        "accurate",
        "moment",
        "mention",
        "grind",
        "governor",
        "veil",
        "fibre",
        "migration",
        "publicity",
        "chaos",
        "card",
        "rotten",
        "limit",
        "satisfaction",
        "mixture",
        "perfect",
        "pattern",
        "easy",
        "press",
        "live",
        "gas pedal",
        "horse",
        "incident",
        "execute",
        "south",
        "berry",
        "favorable",
        "feel",
        "spite",
        "win",
        "like",
        "block",
        "gesture",
        "penalty",
        "falsify",
        "descent",
        "catalogue",
        "gate",
        "cane",
        "land",
        "enlarge",
        "twitch",
        "production",
        "gene",
        "technology",
        "agony",
        "role",
        "row",
        "board",
        "nerve",
        "grind",
        "courtship",
        "speed",
        "district",
        "separation",
        "forge",
        "murder",
        "graze",
        "school",
        "location"
    )
