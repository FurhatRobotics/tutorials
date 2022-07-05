package furhatos.app.multilanguage.flow.main

import furhatos.app.multilanguage.flow.Parent
import furhatos.app.multilanguage.flow.log
import furhatos.app.multilanguage.util.sayInLocalLang
import furhatos.flow.kotlin.State
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.onResponse
import furhatos.flow.kotlin.state
import furhatos.flow.kotlin.voice.PollyNeuralVoice
import furhatos.util.Language

var greeting = "hello"

val Greeting: State = state(Parent) {
    onEntry {
        furhat.voice = PollyNeuralVoice.Matthew()
        furhat.setInputLanguage(
            Language.ENGLISH_US,
            Language.SWEDISH,
            Language.GERMAN,
            Language.ARABIC
        ) // Set up to four input languages for to the robot to listen in simultaneously
        delay(200)
        furhat.say("Hello, how are you doing today?")
        furhat.listen(12000)
    }

    onResponse {
        log.debug(it.text)
        log.debug(it.alternatives)
        log.debug(it.language)
        log.debug(it.language.code)

        furhat.setInputLanguage(it.language) // Setting the input language to the language spoken by the user.
        when (it.language) { // Set the voice to match the input language.
            Language.ENGLISH_US -> furhat.voice = PollyNeuralVoice.Matthew()
            else -> furhat.setVoice(language = it.language)
        }
        furhat.sayInLocalLang("So you want to speak English")
        goto(FavAnimal)
    }
}