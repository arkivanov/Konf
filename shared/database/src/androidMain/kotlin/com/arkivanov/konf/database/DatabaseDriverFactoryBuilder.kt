package com.arkivanov.konf.database

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

@Suppress("FunctionName") // Factory function
fun DatabaseDriverFactory(context: Context): DatabaseDriverFactory =
    object : DatabaseDriverFactory {
        override fun invoke(): SqlDriver = AndroidSqliteDriver(KonfDatabase.Schema, context, "KonfDatabase.db")
    }
