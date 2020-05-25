package com.arkivanov.konf.shared.speakerprofile.integration

import com.arkivanov.konf.shared.speakerprofile.SpeakerProfileComponent
import com.arkivanov.konf.shared.speakerprofile.SpeakerProfileComponent.Dependencies
import com.arkivanov.konf.shared.speakerprofile.SpeakerProfileView
import com.arkivanov.konf.shared.speakerprofile.integration.mappings.eventToOutput
import com.arkivanov.konf.shared.speakerprofile.integration.mappings.stateToModel
import com.arkivanov.konf.shared.speakerprofile.store.SpeakerProfileStoreFactory
import com.arkivanov.mvikotlin.core.binder.BinderLifecycleMode
import com.arkivanov.mvikotlin.core.lifecycle.Lifecycle
import com.arkivanov.mvikotlin.core.lifecycle.doOnDestroy
import com.arkivanov.mvikotlin.extensions.reaktive.bind
import com.arkivanov.mvikotlin.extensions.reaktive.events
import com.arkivanov.mvikotlin.extensions.reaktive.states
import com.badoo.reaktive.observable.map
import com.badoo.reaktive.observable.mapNotNull

internal class SpeakerProfileComponentImpl(
    private val dependencies: Dependencies
) : SpeakerProfileComponent {

    private val store =
        SpeakerProfileStoreFactory(
            factory = dependencies.storeFactory,
            database = SpeakerProfileStoreDatabase(
                speakerId = dependencies.speakerId,
                speakerBundleQueries = dependencies.database.speakerBundleQueries
            )
        ).create()

    init {
        dependencies.lifecycle.doOnDestroy(store::dispose)
    }

    override fun onViewCreated(view: SpeakerProfileView, viewLifecycle: Lifecycle) {
        bind(viewLifecycle, BinderLifecycleMode.CREATE_DESTROY) {
            view.events.mapNotNull(eventToOutput) bindTo dependencies.profileOutput
        }

        bind(viewLifecycle, BinderLifecycleMode.START_STOP) {
            store.states.map(stateToModel) bindTo view
        }
    }
}
