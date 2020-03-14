package com.arkivanov.konf.app.android.mainactivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.arkivanov.konf.app.android.app
import com.arkivanov.konf.database.KonfDatabase
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory

class MainActivity : AppCompatActivity() {

    private val fragmentFactory =
        MainActivityFragmentFactory(
            object : MainActivityFragmentFactory.Dependencies {
                override val storeFactory: StoreFactory = DefaultStoreFactory
                override val database: KonfDatabase get() = app.database
            }
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(android.R.id.content, fragmentFactory.sessionListFragment())
                .commit()
        }
    }
}
