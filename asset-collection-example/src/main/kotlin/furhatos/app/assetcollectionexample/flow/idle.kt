package furhatos.app.assetcollectionexample.flow

import furhatos.app.assetcollectionexample.flow.classic.Start
import furhatos.app.assetcollectionexample.flow.localized.MultiLangState
import furhatos.flow.kotlin.*

val Idle: State = state {

    onEntry {
        furhat.attendNobody()
    }

    onUserEnter {
        furhat.attend(it)
        goto(Start)
    }

    onButton("Regular flow") {
        goto(Start)
    }

    onButton("Localized flow") {
        goto(MultiLangState)
    }
}