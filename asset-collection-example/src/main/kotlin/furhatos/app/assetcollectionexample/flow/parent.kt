package furhatos.app.assetcollectionexample.flow

import furhat.libraries.standard.AutomaticHeadMovements
import furhat.libraries.standard.GesturesLib
import furhat.libraries.standard.NluLib
import furhat.libraries.standard.UtilsLib
import furhatos.app.assetcollectionexample.flow.classic.SleepMode
import furhatos.app.assetcollectionexample.flow.classic.Start
import furhatos.app.assetcollectionexample.nlu.UpdateSheets
import furhatos.flow.kotlin.*
import furhatos.nlu.NullIntent

val Parent: State = state {

    /** From Asset Collection
     * RandomHeadMovements is a partial state with OnTime triggers for making random head movements at specified intervals.
     * Triggers of a partial state can be included in a state with "include()"
     **/
    include(AutomaticHeadMovements.RandomHeadMovements(repetitionPeriod = 5000..10000))

    onUserLeave(instant = true) {
        furhat.gesture(GesturesLib.ExpressWhatThe1()) /** From Asset Collection**/
        when {
            users.count == 0 -> goto(Idle)
            it == users.current -> furhat.attend(users.other)
        }
    }

    onUserEnter(instant = true) {
        furhat.glance(it)
    }

    onResponse<NluLib.IAmDone> { /** From Asset Collection**/
        furhat.say("Very well, I will then go to sleep.")
        goto(SleepMode)
    }

    onResponse<UpdateSheets> {
        UtilsLib.GoogleSheets.updateAllFromGoogleSheet() /** From Asset Collection**/
        furhat.say("I am updating my knowledge.")
        delay(1000)
        goto(Start)
    }

    onButton("Stop") {
        goto(Idle)
    }

    onResponse(NullIntent) {
        reentry()
    }
}