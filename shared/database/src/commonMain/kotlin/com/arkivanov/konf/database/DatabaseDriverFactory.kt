package com.arkivanov.konf.database

import com.squareup.sqldelight.db.SqlDriver

interface DatabaseDriverFactory {

    operator fun invoke(): SqlDriver
}
