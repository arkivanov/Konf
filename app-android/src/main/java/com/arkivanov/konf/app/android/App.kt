package com.arkivanov.konf.app.android

import android.app.Application
import com.arkivanov.konf.database.KonfDatabase
import com.arkivanov.konf.database.SessionEntity
import com.squareup.sqldelight.EnumColumnAdapter
import com.squareup.sqldelight.android.AndroidSqliteDriver

class App : Application() {

    val database: KonfDatabase
        by lazy {
            KonfDatabase(
                AndroidSqliteDriver(KonfDatabase.Schema, this, "KonfDatabase"),
                SessionEntity.Adapter(levelAdapter = EnumColumnAdapter())
            )
        }
}
