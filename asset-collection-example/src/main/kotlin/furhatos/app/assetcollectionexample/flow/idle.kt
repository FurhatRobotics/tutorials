package furhatos.app.assetcollectionexample.flow

import furhat.libraries.standard.UsersLib.usersLib
import furhat.libraries.standard.UtilsLib
import furhat.libraries.standard.utils.GoogleSheetsIntegration
import furhatos.app.assetcollectionexample.*
import furhatos.app.assetcollectionexample.flow.classic.StartUnlocalized
import furhatos.flow.kotlin.*
import furhatos.util.Language

val Idle: State = state(WizardParentButtons) {

    onEntry {
        furhat.say("Which library do you want to test?", "Which library do you want to learn about?")
        furhat.listen()
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