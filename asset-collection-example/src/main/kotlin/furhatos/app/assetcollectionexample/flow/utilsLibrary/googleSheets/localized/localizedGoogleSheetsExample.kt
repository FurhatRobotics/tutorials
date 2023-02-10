package furhatos.app.assetcollectionexample.flow.utilsLibrary.googleSheets.localized

import furhat.libraries.standard.UtilsLib
import furhat.libraries.standard.utils.GoogleSheetsIntegration
import furhatos.app.assetcollectionexample.englishVoice
import furhatos.app.assetcollectionexample.flow.Menu
import furhatos.app.assetcollectionexample.frenchVoice
import furhatos.app.assetcollectionexample.logger
import furhatos.app.assetcollectionexample.swedishVoice
import furhatos.flow.kotlin.*
import furhatos.nlu.NullIntent
import furhatos.nlu.Response
import furhatos.util.Language

val localizedGoogleSheetsExample : State = state {

    onEntry {
        logger.info("See localized sheet in the drive folder")

        /**
         * localizedText returns a random value from the same line of the key "general-firstGreeting"
         */
        furhat.say(UtilsLib.GoogleSheets.localizedText("general-firstGreeting"))
        furhat.ask("In this state you can try to say and understand the same lines and intents in different languages.")
    }

    onReentry { furhat.listen() }

    /**
     * Sentence working in English and French, but triggering the fallback in Swedish
     */
    onButton("Fallbacks") {
        furhat.say(UtilsLib.GoogleSheets.localizedText("notInSwedish"))
    }

    /**
     * This one concerns the tab "Regular Strings" of the localized sheet.
     * It does not have a specified language
     */
    onButton("Sheet with no language specification") {
        furhat.say(UtilsLib.GoogleSheets.localizedText("regularExplanation", language = null))
        // Will not work - report as a missing string since the language is not specified in the sheet
        furhat.say(UtilsLib.GoogleSheets.localizedText("basicSentence", language = Language.ENGLISH_US))
        reentry()
    }

    /**
     * In the localized Google Sheets you can use the localized intent as an unResponse, where in the unlocalized it is used as a partial state.
     * You can specify several keys for the same onResponse.
     *
     * onResponse triggered here by a "Test" or "Test me" in different languages
     */
    onResponse(UtilsLib.GoogleSheets.localizedIntent("testMyLimits", "testMyLimits2")) {
        furhat.say(UtilsLib.GoogleSheets.localizedText("general-positiveReaction"))
        furhat.say(UtilsLib.GoogleSheets.localizedText("announceTests"))
        furhat.listen()
    }

    /** This one will not execute in Swedish, even though the translation is present, since the intent languages don't include it */
    onResponse(UtilsLib.GoogleSheets.localizedIntent("general-goodbye",  intentLanguages = listOf(Language.ENGLISH_US, Language.FRENCH))) {
        furhat.say("Goodbye")
        goto(Menu)
    }

    for (lang in listOf(Language.ENGLISH_US, Language.FRENCH, Language.SWEDISH)) {
        onButton(lang.name.split("(")[0], color = Color.Green) {
            changeLanguage(lang)
            furhat.say(UtilsLib.GoogleSheets.localizedText("languageChanged"))
            reentry()
        }
    }

    onResponse(UtilsLib.GoogleSheets.localizedIntent("general-improvedYes")) {
        furhat.ask(UtilsLib.GoogleSheets.localizedText("general-positiveReaction"))
    }

    /**
     * NB: To remove some verbose you can define your own functions
     */
    onResponseGoogle("general-improvedNo", "general-wait") {
        furhat.googleAsk("general-onResponseReaction")
    }


    onResponse(NullIntent) { reentry() }
    onNoResponse { reentry() }
}

fun FlowControlRunner.changeLanguage(language: Language){
    furhat.voice = when (language) {
        Language.FRENCH -> frenchVoice
        Language.ENGLISH_US -> englishVoice
        Language.SWEDISH -> swedishVoice
        else -> return
    }
    GoogleSheetsIntegration.textLanguage = language
}


/**
 * Utility functions to remove verbose
 */
fun StateBuilder.onResponseGoogle(vararg keys : String, trigger: TriggerRunner<*>.(Response<*>) -> Unit) {
    onResponse(UtilsLib.GoogleSheets.localizedIntent(*keys)) {
        trigger(it)
    }
}
fun Furhat.googleAsk(key: String) {
    ask(UtilsLib.GoogleSheets.localizedText(key))
}