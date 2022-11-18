package furhatos.app.assetcollectionexample.flow.buttonStates

import furhat.libraries.standard.UtilsLib
import furhat.libraries.standard.utils.GoogleSheetsIntegration
import furhat.libraries.standard.utils.googleSheetsLogger
import furhatos.flow.kotlin.Color
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.state

val exploreTags = state(backToIdle) {
    onEntry {
        furhat.say("The Google Sheet integration supports tags for attention, gestures, delays, L E D commands and speech alterations for azure voices")
        furhat.say("You can try the different buttons to see them, and see how they are implemented in the example Google Sheet.")
    }

    onButton("Perform a gesture") {
        furhat.say(UtilsLib.GoogleSheets.getText("performAGesture"))
    }

    onButton("Attend locations") {
        furhat.say(UtilsLib.GoogleSheets.getText("attendLocations"))
    }

    onButton("Say with delay") {
        furhat.say(UtilsLib.GoogleSheets.getText("sayWithDelay"))
    }

    onButton("Change LEDs") {
        furhat.say(UtilsLib.GoogleSheets.getText("changeLEDs"))
    }

    onButton("Say with speech style (Requires Azure voice)") {
        furhat.say(UtilsLib.GoogleSheets.getText("sayAngry"))
    }

    onButton("Say with speed change (Requires Azure voice)") {
        furhat.say(UtilsLib.GoogleSheets.getText("sayFaster"))
    }

    onButton("Explore tag gestures") {
        goto(exploreTagGestures)
    }

    onButton("List Google Sheet available gestures", color = Color.Green) {
        furhat.say("Logging the gestures")
        googleSheetsLogger.info("Available Google Sheet Gestures : \n${GoogleSheetsIntegration.utteranceGestureTagMap.keys.joinToString("\n")}")
    }

    onButton("List Google Sheet available tags", color = Color.Green) {
        furhat.say("Logging the available Google Sheet tags")
        googleSheetsLogger.info("Available Google Sheet tags : \n<g/gestureName>\n<a/loc(x,y,z)>\n<d/length>\n<s/speechStyle>\n<p/prosodyRatePercentage>\n<l/R,G,B>" +
                "\nDocumentation available here https://docs.google.com/spreadsheets/d/1vpsoIQwRnZMYoCsSsZPg7dQuv1eSyb1BajJGb48JrNw/edit#gid=745757956")
    }

    onButton("Where to find the logs ?", color = Color.Yellow) {
        furhat.say("You can find the logs in the web interface, by clicking the angle bracket on the top right corner. " +
                "If you run the skill from intellij, you can find it in the console as well.")
    }
}