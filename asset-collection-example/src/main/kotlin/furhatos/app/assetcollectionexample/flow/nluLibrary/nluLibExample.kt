package furhatos.app.assetcollectionexample.flow.nluLibrary

import furhat.libraries.standard.GesturesLib
import furhat.libraries.standard.NluLib
import furhatos.app.assetcollectionexample.flow.MenuParent
import furhatos.flow.kotlin.*
import furhatos.nlu.NullIntent


val nluLibExample = state(MenuParent) {
    onEntry {
        furhat.say("In this state you can test and see entities available in the N L U library.")
        furhat.ask("Please try saying words that can be caught in the following intents")
    }

    onButton("List intents", color = Color.Green) {
        furhat.say("I have the following added intents: " +
                NluLib::class.nestedClasses.filter { it.simpleName != null }
                    .map { it.simpleName }.joinToString(", "))
        furhat.listen()
    }

    onResponse<NluLib.IAmDone> {
        furhat.say("You are done with my task")
        furhat.listen()
    }

    onResponse<NluLib.PositiveReaction> {
        furhat.say("I'm very happy to hear that you like it")
        furhat.listen()
    }

    onResponse<NluLib.NegativeReaction> {
        furhat.say("I sad to hear that")
        furhat.listen()
    }

    onResponse<NluLib.PositiveExpression> {
        furhat.say("You are quite positive")
        furhat.listen()
    }

    onResponse<NluLib.NegativeExpression> {
        furhat.say("You are quite negative")
        furhat.listen()
    }

    onResponse<NluLib.SpokenLanguages> {
        furhat.say("${it.intent} is indeed a language I speak !")
        furhat.listen()
    }

    // NB : All the spoken languages are also understood
    onResponse<NluLib.UnderStoodLanguages> {
        furhat.say("${it.intent} is indeed a language I understand !")
        furhat.listen()
    }

    onResponse<NluLib.WakeUp> {
        furhat.gesture(GesturesLib.PerformWakeUpWithHeadShake)
        furhat.say("Oh! Hello there!")
        furhat.listen()
    }

    onNoResponse {
        furhat.listen()
    }

    onResponse(NullIntent) {
        furhat.say("I'm sorry, I did not get that.", "Sorry, I don't think this is in the list.", "Sorry, I don't have that one.")
        furhat.listen()
    }
}