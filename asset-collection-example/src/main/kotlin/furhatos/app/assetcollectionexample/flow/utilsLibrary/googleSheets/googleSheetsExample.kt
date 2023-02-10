package furhatos.app.assetcollectionexample.flow.utilsLibrary.googleSheets

import furhat.libraries.standard.UtilsLib
import furhat.libraries.standard.utils.GoogleSheetsIntegration
import furhatos.app.assetcollectionexample.*
import furhatos.app.assetcollectionexample.flow.utilsLibrary.UtilsLibParent
import furhatos.app.assetcollectionexample.flow.utilsLibrary.googleSheets.classic.unlocalizedGoogleSheetsExample
import furhatos.app.assetcollectionexample.flow.utilsLibrary.googleSheets.localized.localizedGoogleSheetsExample
import furhatos.flow.kotlin.Color
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.state
import furhatos.util.Language

val googleSheetsExample = state(UtilsLibParent) {

    /** It is recommended to use only one of the two googleSheet integration system not to get confused.
     * You should also initialize it in the init state of your skill.
     */
    init {
        /** Unlocalized GoogleSheet integration settings.
         * Point to the Google sheet used as resource for fetching regular utterances and intents
         * Link for the browser: https://docs.google.com/spreadsheets/d/${linkGoogleSheet}/
         */
        UtilsLib.GoogleSheets.setDefaultSheetLink(linkGoogleSheet)
        UtilsLib.GoogleSheets.setDefaultSheetIds(textsPrimarySheet, intentsPrimarySheet, buttonsPrimarySheet)


        /** Localized GoogleSheets integration settings
         * Point to the Google sheet used as resource for fetching localized utterances and intents
         * Link for the browser: https://docs.google.com/spreadsheets/d/${linkGoogleSheet}/
         */
        UtilsLib.GoogleSheets.loadLocalizedContent(localizedSheet, sheetTabs)
        /** Used to know which language to use with the localized sheet */
        GoogleSheetsIntegration.textLanguage = Language.ENGLISH_US
    }

    onEntry {
        logger.info("The example sheets are available on this drive : https://drive.google.com/drive/folders/1khxYGeDYf5JoeeVqwNs5WialVwSLF85O")
        furhat.say("Here you can test and understand how google sheets can be used to externalize the sentences I say.")
        furhat.say("There are two different implementations possible, a classic and a localized one.")
        furhat.say("I printed the link to the google sheet used in this example in the console log. ")
        furhat.say("Note that the intents are handled in different ways in both of them, even if the text behavior is quite similar.")
    }

    onReentry {
        println("Reentered")
    }

    onButton("Unlocalized google sheets", color = Color.Yellow) {
        call(unlocalizedGoogleSheetsExample)
    }

    onButton("Localized google sheets", color = Color.Yellow) {
        call(localizedGoogleSheetsExample)
    }

    onButton("Explore Google Sheet supported voice tags") {
        call(exploreTags)
    }

    onButton("Explore Google Sheet supported gesture tags") {
        call(exploreTagGestures)
    }
}