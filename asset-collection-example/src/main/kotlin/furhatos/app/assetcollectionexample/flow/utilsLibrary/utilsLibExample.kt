package furhatos.app.assetcollectionexample.flow.utilsLibrary

import furhat.libraries.standard.UtilsLib
import furhatos.app.assetcollectionexample.flow.MenuParent
import furhatos.app.assetcollectionexample.flow.utilsLibrary.googleSheets.googleSheetsExample
import furhatos.app.assetcollectionexample.flow.utilsLibrary.leds.ledCommandsExample
import furhatos.app.assetcollectionexample.logger
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

    onButton("Google sheets", color = Color.Yellow) {
        goto(googleSheetsExample)
    }

    onButton("Random no repeat") {
        UtilsLib.randomNoRepeat(
            { furhat.say("This is a function that introduces variance between the options") },
            { furhat.say("I should alternate quite often between the alternatives") },
            { send("SayRandomEvent")})
    }
    onEvent("SayRandomEvent") { furhat.say("This say is from an event") }

    onButton("Print list languages") {
        logger.info("Available languages : \n" + UtilsLib.getListOfLanguages().joinToString(",\n"))
    }

    onButton("Get json resource files") {
        furhat.say("I have ${UtilsLib.getAllFilesInResources(".json").size} dot jayson files in my resource folder")
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
