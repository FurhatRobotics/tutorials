package furhatos.app.assetcollectionexample.flow

import furhatos.app.assetcollectionexample.flow.classic.StartUnlocalized
import furhatos.flow.kotlin.State
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.onUserEnter
import furhatos.flow.kotlin.state

val Idle: State = state(WizardParentButtons) {

    onEntry {
        furhat.attendNobody()
    }

    onUserEnter {
        furhat.attend(it)
        goto(StartUnlocalized)
    }
}