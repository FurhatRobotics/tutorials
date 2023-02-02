package furhatos.app.assetcollectionexample.flow.utilsLibrary.googleSheets.classic

import furhat.libraries.standard.GesturesLib
import furhat.libraries.standard.NluLib
import furhat.libraries.standard.UsersLib.usersLib
import furhat.libraries.standard.UtilsLib
import furhatos.app.assetcollectionexample.flow.Menu
import furhatos.app.assetcollectionexample.flow.utilsLibrary.UtilsLibParent
import furhatos.app.assetcollectionexample.otherIntentsPrimarySheet
import furhatos.app.assetcollectionexample.otherSheet
import furhatos.app.assetcollectionexample.textsSecondarySheet
import furhatos.flow.kotlin.*
import furhatos.nlu.NullIntent
import furhatos.nlu.common.No


val StartUnlocalized : State = state(UtilsLibParent) {

    include(UtilsLib.GoogleSheets.getPartialStateIntents()) /** From Asset Collection**/
    include(UtilsLib.GoogleSheets.getPartialStateIntents(sheetTab = otherIntentsPrimarySheet)) /** From Asset Collection**/

    onEntry {
        /** From Asset Collection
         * Convenience method to get any short user, e.g. a child.
         */
        if (usersLib.usersIncludeShortUser(-0.2)) {
            for (userToGreet in users.list) { // Will greet every user
                furhat.attend(userToGreet)
                delay(300)
                if (userToGreet == usersLib.getShortestUser()){ /** From Asset Collection**/
                    furhat.gesture(GesturesLib.PerformBigSmileWithDelay()) /**From Asset Collection**/
                    furhat.say("Oh, hello there, little one!")
                } else {
                    furhat.say(UtilsLib.GoogleSheets.getText("greeting")) /**From Asset Collection**/
                }
                delay(500)
            }
        } else {
            for (greetOne in users.list) {
                furhat.attend(greetOne)
                furhat.say(UtilsLib.GoogleSheets.getText("greeting")) /**From Asset Collection**/
                delay(500)
            }
        }
        furhat.say("To see a demonstration of my Google Sheet abilities, say a positive expression.")
        furhat.say("You can also ask questions defined in the intent sheet.")
        furhat.ask("Do you like robots?")
    }

    onReentry {
        furhat.ask(UtilsLib.GoogleSheets.getText("otherQuestion", sheetTab = textsSecondarySheet, sheetLink = otherSheet))
    }

    onResponse<NluLib.PositiveExpression>{ /**From Asset Collection**/
        furhat.say("I'm glad you are positive! Here are some lines from Google Sheets.")
        furhat.say(UtilsLib.GoogleSheets.getText("greeting", textsSecondarySheet, otherSheet)) /**From Asset Collection**/

        furhat.say(UtilsLib.GoogleSheets.getText("greetingWithUtterance")) /**From Asset Collection**/

        furhat.say("Now with fallbacks")
        furhat.say(UtilsLib.GoogleSheets.getText("fakeLineKey", fallback = "This line doesn't exist")) /**From Asset Collection**/
        furhat.say(UtilsLib.GoogleSheets.getText(key = "greeting", sheetLink = "This-sheet-does-not-exist", fallback = "This sheet doesn't exist"))
        furhat.say(UtilsLib.GoogleSheets.getText(key = "greeting", sheetTab = "This-tab-does-not-exist", fallback = "This tab doesn't exist"))

        goto(Menu)
    }

    onResponse<No>{
        furhat.say("That's sad. I'll take a nap then.")
        goto(SleepMode)
    }

    onResponse(NullIntent) {
        reentry()
    }
}