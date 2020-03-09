package com.arkivanov.konf.shared.speakerdetails.integration

import com.arkivanov.konf.shared.speakerdetails.SpeakerProfileComponent
import com.arkivanov.konf.shared.speakerdetails.SpeakerProfileComponent.Dependencies
import com.arkivanov.konf.shared.speakerdetails.SpeakerProfileComponent.Output
import com.arkivanov.konf.shared.speakerdetails.SpeakerProfileView
import com.arkivanov.konf.shared.speakerdetails.store.SpeakerProfileStore
import com.arkivanov.konf.shared.speakerdetails.store.SpeakerProfileStoreFactory
import com.arkivanov.mvikotlin.core.binder.Binder
import com.arkivanov.mvikotlin.extensions.reaktive.bind
import com.arkivanov.mvikotlin.extensions.reaktive.events
import com.arkivanov.mvikotlin.extensions.reaktive.states
import com.badoo.reaktive.annotations.ExperimentalReaktiveApi
import com.badoo.reaktive.disposable.scope.DisposableScope
import com.badoo.reaktive.observable.ObservableObserver
import com.badoo.reaktive.observable.map
import com.badoo.reaktive.observable.mapNotNull
import com.badoo.reaktive.subject.publish.PublishSubject

@UseExperimental(ExperimentalReaktiveApi::class)
internal class SpeakerProfileComponentImpl(
    dependencies: Dependencies
) : SpeakerProfileComponent, DisposableScope by DisposableScope() {

    private val output = PublishSubject<Output>()

    private val store =
        SpeakerProfileStoreFactory(
            speakerId = dependencies.speakerId,
            factory = dependencies.storeFactory,
            speakerBundleQueries = dependencies.database.speakerBundleQueries
        ).create().scope()

    private var binder: Binder? = null

    override fun subscribe(observer: ObservableObserver<Output>) {
        output.subscribe(observer)
    }

    override fun onViewCreated(view: SpeakerProfileView) {
        binder =
            bind {
                store.states.map(SpeakerProfileStore.State::toViewModel) bindTo view
                view.events.mapNotNull(SpeakerProfileView.Event::toOutput) bindTo output
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
