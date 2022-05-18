package furhatos.app.exampleassetcollection.flow

import furhat.libraries.standard.GesturesLib
import furhat.libraries.standard.NluLib
import furhat.libraries.standard.UtilsLib
import furhatos.app.exampleassetcollection.settings.otherSheet
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.onNoResponse
import furhatos.flow.kotlin.onResponse
import furhatos.flow.kotlin.state

var SleepMode = state {

    include(UtilsLib.GoogleSheets.getPartialStateIntents("anotherIntentTab", otherSheet)) /** From Asset Collection**/

    onEntry {
        furhat.listen()
    }

    onResponse<NluLib.WakeUp> {
        furhat.gesture(GesturesLib.PerformWakeUpWithHeadShake, priority = 1) /** From Asset Collection**/
        goto(Idle)
    }

    onNoResponse {
        furhat.listen()
    }

    onResponse {
        random(
            { furhat.gesture(GesturesLib.ExpressConfusion1()) }, /** From Asset Collection**/
            { furhat.gesture(GesturesLib.ExpressPleased1()) } /** From Asset Collection**/
        )
        furhat.listen()
    }
}