package furhatos.app.assetcollectionexample.flow.buttonStates

import furhatos.app.assetcollectionexample.flow.Idle
import furhatos.flow.kotlin.Color
import furhatos.flow.kotlin.state

val backToIdle = state {
    onButton("Back to Idle", color = Color.Red) {
        goto(Idle)
    }
}