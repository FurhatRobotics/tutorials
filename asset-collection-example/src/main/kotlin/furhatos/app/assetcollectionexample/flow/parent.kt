package furhatos.app.assetcollectionexample.flow

import furhat.libraries.standard.AutomaticHeadMovements
import furhat.libraries.standard.GesturesLib
import furhat.libraries.standard.NluLib
import furhat.libraries.standard.UtilsLib
import furhatos.app.assetcollectionexample.flow.buttonStates.exploreTagGestures
import furhatos.app.assetcollectionexample.flow.buttonStates.exploreTags
import furhatos.app.assetcollectionexample.flow.classic.SleepMode
import furhatos.app.assetcollectionexample.flow.classic.Start
import furhatos.app.assetcollectionexample.flow.localized.MultiLangState
import furhatos.app.assetcollectionexample.nlu.UpdateSheets
import furhatos.flow.kotlin.*
import furhatos.nlu.NullIntent

val WizardParentButtons = state {
    onButton("Explore supported tags") {
        goto(exploreTags)
    }

    onButton("Explore supported tag gestures") {
        goto(exploreTagGestures)
    }

    onButton("Regular flow") {
        goto(Start)
    }

    onButton("Localized flow") {
        goto(MultiLangState)
    }
}

val Parent: State = state(WizardParentButtons) {

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

    /** From Asset Collection
     *  Here we create a onResponse without languages.
     */
    onResponse(UtilsLib.GoogleSheets.localizedIntent("goToSleep", intentLanguages = null)) {
        gotoSleep()
    }

    onResponse<NluLib.IAmDone> { /** From Asset Collection**/
        gotoSleep()
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

fun FlowControlRunner.gotoSleep() {
    furhat.say("Very well, I will then go to sleep.")
    goto(SleepMode)
}