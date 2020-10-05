package com.arkivanov.konf.shared.root

import com.arkivanov.konf.shared.sessiondetails.SessionDetailsComponent
import com.arkivanov.konf.shared.sessionlist.SessionListComponent

sealed class RootChild {

    data class List(val model: SessionListComponent.Model) : RootChild()
    data class Details(val model: SessionDetailsComponent.Model) : RootChild()
}
