package furhatos.app.assetcollectionexample.flow.localized

import furhat.libraries.standard.UtilsLib
import furhat.libraries.standard.utils.GoogleSheetsIntegration.Companion.textLanguage
import furhatos.app.assetcollectionexample.englishVoice
import furhatos.app.assetcollectionexample.flow.Idle
import furhatos.app.assetcollectionexample.flow.Parent
import furhatos.app.assetcollectionexample.frenchVoice
import furhatos.app.assetcollectionexample.swedishVoice
import furhatos.flow.kotlin.*
import furhatos.util.Language

val MultiLangState : State = state(Parent) {

    onEntry {
        furhat.ask(UtilsLib.GoogleSheets.localizedText("general-firstGreeting"))
    }

    onReentry {
        furhat.ask(UtilsLib.GoogleSheets.localizedText("general-positiveReaction"))
    }

    onResponse(UtilsLib.GoogleSheets.localizedIntent("testMyLimits")) {
        furhat.say(UtilsLib.GoogleSheets.localizedText("announceTests"))

        furhat.say(UtilsLib.GoogleSheets.localizedText("Wrong key"))
        furhat.say(UtilsLib.GoogleSheets.localizedText("notInSwedish"))

        furhat.say(UtilsLib.GoogleSheets.localizedText("regularExplanation", language = null))
        furhat.say(UtilsLib.GoogleSheets.localizedText("basicSentence", language = Language.ENGLISH_US)) // Will not work - report as a missing string

        reentry()
    }

    /** This one will not execute in Swedish, even though the translation is present */
    onResponse(UtilsLib.GoogleSheets.localizedIntent("general-goodbye",  intentLanguages = listOf(Language.ENGLISH_US, Language.FRENCH))) {
        goto(Idle)
    }

    for (lang in listOf(Language.ENGLISH_US, Language.FRENCH, Language.SWEDISH)) {
        onButton(lang.name.split("(")[0]) {
            changeLanguage(lang)
            reentry()
        }
    }

    onNoResponse {
        reentry()
    }
}

fun FlowControlRunner.changeLanguage(language: Language){
    furhat.voice = when (language) {
        Language.FRENCH -> frenchVoice
        Language.ENGLISH_US -> englishVoice
        Language.SWEDISH -> swedishVoice
        else -> return
    }
    textLanguage = language
}