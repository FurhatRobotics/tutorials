package furhatos.app.assetcollectionexample.flow.utilsLibrary.googleSheets.classic

import furhat.libraries.standard.UtilsLib
import furhatos.app.assetcollectionexample.logger
import furhatos.app.assetcollectionexample.otherIntentsPrimarySheet
import furhatos.app.assetcollectionexample.otherSheet
import furhatos.app.assetcollectionexample.textsSecondarySheet
import furhatos.flow.kotlin.*

val unlocalizedGoogleSheetsExample : State = state {

    /**
     * Including automatic intents from two different tabs.
     * You can try other intents by uncommenting the third intents line and commenting the second one
     */
    include(UtilsLib.GoogleSheets.getPartialStateIntents())
    include(UtilsLib.GoogleSheets.getPartialStateIntents(sheetTab = otherIntentsPrimarySheet))
    // include(UtilsLib.GoogleSheets.getPartialStateIntents(sheetLink = otherSheet, sheetTab = intentsSecondarySheet))

    /** Includes wizard buttons that say text */
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
        /**
         * getText returns a random value from the same line of the key "greeting"
         */
        furhat.say(UtilsLib.GoogleSheets.getText("greeting"))
        /**
         * NB: To remove some verbose you can define your own function
         */
        furhat.googleSay("greetingWithUtterance")
        furhat.listen()
    }

    onButton("Lines sheet #2") {
        furhat.say(UtilsLib.GoogleSheets.getText("greeting", sheetTab = textsSecondarySheet, sheetLink = otherSheet))
        furhat.googleAskOtherSheet("otherQuestion")
    }

    onButton("Fallbacks") {
        furhat.say(UtilsLib.GoogleSheets.getText("fakeLineKey", fallback = "This line doesn't exist"))
        furhat.say(UtilsLib.GoogleSheets.getText(key = "greeting", sheetLink = "This-sheet-does-not-exist", fallback = "This sheet doesn't exist, could not say"))
        furhat.say(UtilsLib.GoogleSheets.getText(key = "greeting", sheetTab = "This-tab-does-not-exist", fallback = "This tab doesn't exist, could not say"))
        furhat.listen()
    }

    onNoResponse { furhat.listen() }
}


/**
 * Furhat utility functions to remove verbose
 */
fun Furhat.googleSay(key: String) {
    say(UtilsLib.GoogleSheets.getText(key))
}
fun Furhat.googleAskOtherSheet(key: String) {
    ask(UtilsLib.GoogleSheets.getText(key, sheetTab = textsSecondarySheet, sheetLink = otherSheet))
}