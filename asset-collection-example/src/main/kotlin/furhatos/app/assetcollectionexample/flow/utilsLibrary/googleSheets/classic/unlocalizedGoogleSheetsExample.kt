package furhatos.app.assetcollectionexample.flow.utilsLibrary.googleSheets.classic

import furhat.libraries.standard.UtilsLib
import furhatos.app.assetcollectionexample.*
import furhatos.flow.kotlin.State
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.onNoResponse
import furhatos.flow.kotlin.state


val unlocalizedGoogleSheetsExample : State = state {

    /**
     * Including automatic intents from two different tabs.
     * You can try other intents by uncommenting the third intents line and commenting the second one
     */
    include(UtilsLib.GoogleSheets.getPartialStateIntents())
    include(UtilsLib.GoogleSheets.getPartialStateIntents(sheetTab = otherIntentsPrimarySheet))
    // include(UtilsLib.GoogleSheets.getPartialStateIntents(sheetLink = otherSheet, sheetTab = intentsSecondarySheet))

    include(UtilsLib.GoogleSheets.getPartialStateButtons())

    onEntry {
        logger.info("See ExampleSheet and OtherExampleSheet in the drive folder")
        furhat.say("You can test the buttons or ask questions defined in the intent sheet (for instance what is the wifi password).")
        furhat.listen()
    }

    onReentry {
        furhat.listen()
    }

    onButton("Greetings") {
        furhat.say(UtilsLib.GoogleSheets.getText("greeting"))
        furhat.say(UtilsLib.GoogleSheets.getText("greetingWithUtterance"))
        furhat.listen()
    }

    onButton("Lines sheet #2") {
        furhat.say(UtilsLib.GoogleSheets.getText("greeting", textsSecondarySheet, otherSheet))
        furhat.ask(UtilsLib.GoogleSheets.getText("otherQuestion", sheetTab = textsSecondarySheet, sheetLink = otherSheet))
    }

    onButton("Fallbacks") {
        furhat.say(UtilsLib.GoogleSheets.getText("fakeLineKey", fallback = "This line doesn't exist"))
        furhat.say(UtilsLib.GoogleSheets.getText(key = "greeting", sheetLink = "This-sheet-does-not-exist", fallback = "This sheet doesn't exist, could not say"))
        furhat.say(UtilsLib.GoogleSheets.getText(key = "greeting", sheetTab = "This-tab-does-not-exist", fallback = "This tab doesn't exist, could not say"))
        furhat.listen()
    }

    onNoResponse { furhat.listen() }
}