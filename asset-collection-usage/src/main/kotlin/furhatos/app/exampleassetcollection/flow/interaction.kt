package furhatos.app.exampleassetcollection.flow

import furhat.libraries.standard.GesturesLib
import furhat.libraries.standard.NluLib
import furhat.libraries.standard.UtilsLib
import furhat.libraries.standard.utils.getShortestUser
import furhat.libraries.standard.utils.usersIncludeShortUser
import furhatos.app.exampleassetcollection.settings.otherSheet
import furhatos.nlu.common.*
import furhatos.flow.kotlin.*


val Start : State = state(Parent) {

    onEntry {
        /** From Asset Collection
         * Convenience method to get any short user, e.g. a child.
         **/
        if (usersIncludeShortUser(-0.2)) {
            for (greetOne in users.list) {
                furhat.attend(greetOne)
                delay(200)
                if (greetOne == getShortestUser()){ /** From Asset Collection**/
                    //Could be done with size estimation as well
                    furhat.gesture(GesturesLib.PerformBigSmileWithDelay()) /** From Asset Collection**/
                    furhat.say("Oh, hello there, little one! ")
                } else {
                    /** From Asset Collection
                     * Get lines to use as greeting phrases defined in the google sheet.
                     **/
                    furhat.say(UtilsLib.GoogleSheets.getLine("greeting"))
                }
                delay(500)
            }
        } else {
            for (greetOne in users.list) {
                furhat.attend(greetOne)
                delay(200)
                furhat.say(UtilsLib.GoogleSheets.getLine("greeting", sheetLink = otherSheet)) /** From Asset Collection**/
                delay(500)
            }
        }
        furhat.ask("Do you like robots?")
    }

    onResponse<Yes>{
        furhat.gesture(GesturesLib.PerformDoubleNod) /** From Asset Collection**/
        furhat.say("I like humans.")
        goto(Idle)
    }

    onResponse<No>{
        furhat.say("That's sad.")
        goto(SleepMode)
    }

    onResponse<NluLib.PositiveExpression>{ /** From Asset Collection**/
        furhat.say("I'm glad you are positive! Here are some lines from Google Sheets.")
        furhat.say(UtilsLib.GoogleSheets.getLine("greeting", "lines", otherSheet)) /** From Asset Collection**/

        furhat.say("Now testing get google sheet line")
        furhat.say(UtilsLib.GoogleSheets.getLine("adminModeEntered")) /** From Asset Collection**/

        furhat.say("This line doesn't exist")
        furhat.say(UtilsLib.GoogleSheets.getLine("fakeLineKey")) /** From Asset Collection**/

        goto(Idle)
    }
}
