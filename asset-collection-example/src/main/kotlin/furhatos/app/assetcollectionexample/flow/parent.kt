package furhatos.app.assetcollectionexample.flow

import furhat.libraries.standard.GesturesLib
import furhat.libraries.standard.NluLib
import furhat.libraries.standard.UtilsLib
import furhatos.app.assetcollectionexample.nlu.UpdateSheets
import furhatos.flow.kotlin.*

val Parent: State = state {

    include(UtilsLib.GoogleSheets.getPartialStateIntents()) /** From Asset Collection**/
    include(UtilsLib.GoogleSheets.getPartialStateIntents(sheetTab = "otherIntents")) /** From Asset Collection**/

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
        furhat.gesture(GesturesLib.PerformFallAsleepPersist, priority = 1) /** From Asset Collection**/
        goto(SleepMode)
    }

    onResponse<UpdateSheets> {
        UtilsLib.GoogleSheets.updateAllFromGoogleSheet() /** From Asset Collection**/
        furhat.say("I am updating my knowledge.")
        delay(1000)
        goto(Start)
    }
}