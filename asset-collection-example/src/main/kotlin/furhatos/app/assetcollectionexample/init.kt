package furhatos.app.assetcollectionexample

import furhat.libraries.standard.UsersLib.usersLib
import furhat.libraries.standard.UtilsLib
import furhat.libraries.standard.utils.GoogleSheetsIntegration.Companion.textLanguage
import furhatos.app.assetcollectionexample.flow.Idle
import furhatos.app.assetcollectionexample.flow.classic.StartUnlocalized
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.state
import furhatos.flow.kotlin.users
import furhatos.util.Language

val Init = state {

    init {
        users.setSimpleEngagementPolicy(distanceToEngage, maxNumberOfUsers)
        furhat.voice = englishVoice

        /** From Asset Collection
         * Point to the Google sheet used as resource for fetching regular utterances and intents
         * Link for the browser: https://docs.google.com/spreadsheets/d/${linkGoogleSheet}/
         **/
        UtilsLib.GoogleSheets.setDefaultSheetLink(linkGoogleSheet)
        UtilsLib.GoogleSheets.setDefaultSheetIds(textsPrimarySheet, intentsPrimarySheet, buttonsPrimarySheet)

        /** From Asset Collection
         * Point to the Google sheet used as resource for fetching localized utterances and intents
         * Link for the browser: https://docs.google.com/spreadsheets/d/${linkGoogleSheet}/
         **/
        UtilsLib.GoogleSheets.loadLocalizedContent(localizedSheet, sheetTabs)

        /** From Asset Collection
         * Used to know which language to use with the localized sheet
         */
        textLanguage = Language.ENGLISH_US

        if (users.count >= 1) {
            furhat.usersLib.attendClosestUser() /** From Asset Collection**/
            goto(StartUnlocalized)
        } else {
            goto(Idle)
        }
    }
}