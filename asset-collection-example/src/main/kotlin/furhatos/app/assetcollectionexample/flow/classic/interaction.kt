package furhatos.app.assetcollectionexample.flow.classic

import furhat.libraries.standard.AutomaticHeadMovements
import furhat.libraries.standard.GesturesLib
import furhat.libraries.standard.NluLib
import furhat.libraries.standard.UtilsLib
import furhat.libraries.standard.utils.getShortestUser
import furhat.libraries.standard.utils.usersIncludeShortUser
import furhatos.app.assetcollectionexample.flow.Idle
import furhatos.app.assetcollectionexample.flow.Parent
import furhatos.app.assetcollectionexample.flow.localized.MultiLangState
import furhatos.app.assetcollectionexample.otherSheet
import furhatos.nlu.common.*
import furhatos.flow.kotlin.*
import furhatos.nlu.NullIntent


val Start : State = state(Parent) {

    include(UtilsLib.GoogleSheets.getPartialStateIntents()) /** From Asset Collection**/
    include(UtilsLib.GoogleSheets.getPartialStateIntents(sheetTab = "otherIntents")) /** From Asset Collection**/

    onEntry {
        /** From Asset Collection
         * Convenience method to get any short user, e.g. a child.
         **/
        if (usersIncludeShortUser(-0.2)) {
            for (greetOne in users.list) {
                furhat.attend(greetOne)
                delay(300)
                if (greetOne == getShortestUser()){ /** From Asset Collection**/
                    //Could be done with size estimation as well
                    furhat.gesture(GesturesLib.PerformBigSmileWithDelay()) /** From Asset Collection**/
                    furhat.say("Oh, hello there, little one! ")
                } else {
                    /** From Asset Collection
                     * Get lines to use as greeting phrases defined in the Google sheet.
                     **/
                    furhat.say(UtilsLib.GoogleSheets.getText("greeting"))
                }
                delay(500)
            }
        } else {
            for (greetOne in users.list) {
                furhat.attend(greetOne)
                delay(300)
                furhat.say(UtilsLib.GoogleSheets.getText("greeting")) /** From Asset Collection**/
                delay(500)
            }
        }
        furhat.ask("Do you like robots?")
    }

    onReentry {
        furhat.ask(UtilsLib.GoogleSheets.getText("otherQuestion", sheetLink = otherSheet))
    }

    onResponse<Yes>{
        /** Set value to avoid AutomaticHeadMovements to interfere with other head movements defined in a gesture. We assume gestures are not longer than 3000 ms **/
        AutomaticHeadMovements.autoHeadMovementDelay(3000)

        furhat.gesture(GesturesLib.PerformDoubleNod) /** From Asset Collection**/
        furhat.say("I like humans.")
        goto(Idle)
    }

    onResponse<No>{
        furhat.say("That's sad. I'll take a nap then.")
        goto(SleepMode)
    }

    onResponse<NluLib.PositiveExpression>{ /** From Asset Collection**/
        furhat.say("I'm glad you are positive! Here are some lines from Google Sheets.")
        furhat.say(UtilsLib.GoogleSheets.getText("greeting", "lines", otherSheet)) /** From Asset Collection**/

        furhat.say("Now with some movements")
        furhat.say(UtilsLib.GoogleSheets.getText("greetingWithUtterance")) /** From Asset Collection**/

        furhat.say(UtilsLib.GoogleSheets.getText("fakeLineKey", fallback = "This line doesn't exist")) /** From Asset Collection**/

        furhat.say(UtilsLib.GoogleSheets.getText(key = "greeting", sheetLink = "This-sheet-does-not-exist"))
        /** This one will default to the first tab if none is found */
        furhat.say(UtilsLib.GoogleSheets.getText(key = "greeting", sheetTab = "This-tab-does-not-exist"))

        goto(Idle)
    }

    onResponse(NullIntent) {
        reentry()
    }

    onNoResponse {
        furhat.say("Now I will enter my multi language mode")
        goto(MultiLangState)
    }
}
