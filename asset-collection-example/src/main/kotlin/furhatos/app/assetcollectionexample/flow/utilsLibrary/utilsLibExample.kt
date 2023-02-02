package furhatos.app.assetcollectionexample.flow.utilsLibrary

import furhatos.app.assetcollectionexample.flow.MenuParent
import furhatos.app.assetcollectionexample.flow.utilsLibrary.googleSheets.googleSheetsExample
import furhatos.app.assetcollectionexample.flow.utilsLibrary.leds.ledCommandsExample
import furhatos.flow.kotlin.Color
import furhatos.flow.kotlin.State
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.state

val utilsLibExample = state(MenuParent) {

    onEntry {
        furhat.say("In this state you can test different utility skill functions.")
        furhat.say("You can also try LED commands and google sheets integration.")
    }

    onButton("LED commands", color = Color.Yellow) {
        goto(ledCommandsExample)
    }

    onButton("Google sheets") {
        goto(googleSheetsExample)
    }

    /**
     * The safeGoto() function can be used from the Utils library as well, but needs then to replace every single normal `goto()`
     */
}

val UtilsLibParent: State = state (MenuParent) {
    
    onButton("UtilsLib", color = Color.Red) {
        goto(utilsLibExample)
    }
}
