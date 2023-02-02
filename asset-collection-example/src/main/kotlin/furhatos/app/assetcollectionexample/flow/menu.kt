package furhatos.app.assetcollectionexample.flow

import furhat.libraries.standard.UsersLib.usersLib
import furhat.libraries.standard.UtilsLib
import furhat.libraries.standard.utils.GoogleSheetsIntegration
import furhatos.app.assetcollectionexample.*
import furhatos.app.assetcollectionexample.flow.behaviorlibrary.behaviorLibExample
import furhatos.app.assetcollectionexample.flow.gestureslibrary.gesturesLibExample
import furhatos.app.assetcollectionexample.flow.nluLibrary.nluLibExample
import furhatos.app.assetcollectionexample.flow.usersLibrary.usersLibExample
import furhatos.app.assetcollectionexample.flow.utilsLibrary.googleSheets.classic.StartUnlocalized
import furhatos.app.assetcollectionexample.flow.utilsLibrary.utilsLibExample
import furhatos.flow.kotlin.*
import furhatos.util.Language

val Menu: State = state {

    onEntry {
        furhat.say("Which library do you want to test?", "Which library do you want to learn about?")
        furhat.listen()
    }

    onButton("BehaviorLib") {
        goto(behaviorLibExample)
    }

    onButton("GesturesLib") {
        goto(gesturesLibExample)
    }

    onButton("NluLib") {
        goto(nluLibExample)
    }

    onButton("UsersLib") {
        goto(usersLibExample)
    }

    onButton("UtilsLib") {
        goto(utilsLibExample)
    }

    //TODO : add voice commands

    onNoResponse {  }
}

val MenuParent: State = state {
    onButton("Menu", color = Color.Red) {
        goto(Menu)
    }
}


val removed = state {

    onEntry {
        if (users.count >= 1) {
            furhat.usersLib.attendClosestUser()
            /** From Asset Collection**/
            goto(StartUnlocalized)
        }


        // Classic GoogleSheet integration settings
        /** From Asset Collection
         * Point to the Google sheet used as resource for fetching regular utterances and intents
         * Link for the browser: https://docs.google.com/spreadsheets/d/${linkGoogleSheet}/
         */
        UtilsLib.GoogleSheets.setDefaultSheetLink(linkGoogleSheet)
        UtilsLib.GoogleSheets.setDefaultSheetIds(textsPrimarySheet, intentsPrimarySheet, buttonsPrimarySheet)

        // Localized GoogleSheet integration settings
        /** From Asset Collection
         * Point to the Google sheet used as resource for fetching localized utterances and intents
         * Link for the browser: https://docs.google.com/spreadsheets/d/${linkGoogleSheet}/
         */
        UtilsLib.GoogleSheets.loadLocalizedContent(localizedSheet, sheetTabs)

        /** From Asset Collection
         * Used to know which language to use with the localized sheet
         */
        GoogleSheetsIntegration.textLanguage = Language.ENGLISH_US

        /** From Asset Collection
         * Have a background state that makes LEDs glow
         */
        parallel{ goto(UtilsLib.LEDs.PulseLEDState) }
        send(UtilsLib.LEDs.pulseLEDStop)
    }


}