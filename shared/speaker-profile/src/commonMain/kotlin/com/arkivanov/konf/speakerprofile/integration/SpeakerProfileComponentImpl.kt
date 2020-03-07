package com.arkivanov.konf.speakerprofile.integration

import com.arkivanov.konf.speakerprofile.SpeakerProfileComponent
import com.arkivanov.konf.speakerprofile.SpeakerProfileComponent.Dependencies
import com.arkivanov.konf.speakerprofile.SpeakerProfileView
import com.arkivanov.konf.speakerprofile.store.SpeakerProfileStoreFactory
import com.arkivanov.mvikotlin.core.binder.Binder
import com.arkivanov.mvikotlin.extensions.reaktive.bind
import com.arkivanov.mvikotlin.extensions.reaktive.states
import com.badoo.reaktive.annotations.ExperimentalReaktiveApi
import com.badoo.reaktive.disposable.scope.DisposableScope
import com.badoo.reaktive.observable.map

@UseExperimental(ExperimentalReaktiveApi::class)
internal class SpeakerProfileComponentImpl(
    dependencies: Dependencies
) : SpeakerProfileComponent, DisposableScope by DisposableScope() {

    private val store =
        SpeakerProfileStoreFactory(
            speakerId = dependencies.speakerId,
            factory = dependencies.storeFactory,
            databaseQueries = dependencies.databaseQueries
        ).create().scope()

    private var binder: Binder? = null

    override fun onViewCreated(view: SpeakerProfileView) {
        binder =
            bind {
                store.states.map { it.toViewModel() } bindTo view::render
            }
    }

    override fun onStart() {
        binder?.start()
    }

    override fun onStop() {
        binder?.stop()
    }

    override fun onViewDestroyed() {
        binder = null
    }

    override fun onDestroy() {
        dispose()
    }
}
