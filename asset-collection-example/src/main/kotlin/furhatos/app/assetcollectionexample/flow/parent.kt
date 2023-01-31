package furhatos.app.assetcollectionexample.flow

import furhat.libraries.standard.GesturesLib
import furhat.libraries.standard.NluLib
import furhat.libraries.standard.UtilsLib
import furhatos.app.assetcollectionexample.flow.behaviorlibrary.behaviorLibExample
import furhatos.app.assetcollectionexample.flow.buttonStates.exploreTagGestures
import furhatos.app.assetcollectionexample.flow.buttonStates.exploreTags
import furhatos.app.assetcollectionexample.flow.classic.SleepMode
import furhatos.app.assetcollectionexample.flow.classic.StartUnlocalized
import furhatos.app.assetcollectionexample.flow.gestureslibrary.gesturesLibExample
import furhatos.app.assetcollectionexample.flow.localized.MultiLangState
import furhatos.app.assetcollectionexample.nlu.UpdateSheets
import furhatos.flow.kotlin.*
import furhatos.nlu.NullIntent

val WizardParentButtons: State = state {

    onButton("BehaviorLib") {
        goto(behaviorLibExample)
    }

    onButton("GesturesLib") {
        goto(gesturesLibExample)
    }

    //TODO : add voice commands



    // UtilsLib && GoogleSheet integration
    onButton("Explore Google Sheet supported azure voice tags") {
        goto(exploreTags)
    }

    onButton("Explore Google Sheet supported azure gesture tags") {
        goto(exploreTagGestures)
    }

    onButton("Regular flow") {
        goto(StartUnlocalized)
    }

    onButton("Localized flow") {
        goto(MultiLangState)
    }
}

val Parent: State = state(WizardParentButtons) {

    onButton("Menu") {
        goto(Idle)
    }

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
        goto(StartUnlocalized)
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