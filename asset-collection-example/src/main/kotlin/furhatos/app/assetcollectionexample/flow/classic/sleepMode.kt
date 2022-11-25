package furhatos.app.assetcollectionexample.flow.classic

import furhat.libraries.standard.GesturesLib
import furhat.libraries.standard.NluLib
import furhat.libraries.standard.UtilsLib
import furhatos.app.assetcollectionexample.flow.Idle
import furhatos.app.assetcollectionexample.flow.WizardParentButtons
import furhatos.app.assetcollectionexample.intentsSecondarySheet
import furhatos.app.assetcollectionexample.otherSheet
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.onNoResponse
import furhatos.flow.kotlin.onResponse
import furhatos.flow.kotlin.state

var SleepMode = state(WizardParentButtons) {

    include(UtilsLib.GoogleSheets.getPartialStateIntents(sheetLink = otherSheet, sheetTab = intentsSecondarySheet)) /** From Asset Collection**/

    onEntry {
        furhat.gesture(GesturesLib.PerformFallAsleepPersist, priority = 1) /** From Asset Collection**/
        send(UtilsLib.LEDs.pulseLEDStart()) /** From Asset Collection**/
        furhat.listen()
    }

    onReentry {
        furhat.listen()
    }

    onResponse<NluLib.WakeUp> {
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

    onExit {
        furhat.gesture(GesturesLib.PerformWakeUpWithHeadShake, priority = 1) /** From Asset Collection**/
        send(UtilsLib.LEDs.pulseLEDStop) /** From Asset Collection**/
    }
}