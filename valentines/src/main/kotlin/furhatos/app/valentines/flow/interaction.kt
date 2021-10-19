package furhatos.app.valentines.flow

import furhatos.app.valentines.nlu.IAm
import furhatos.nlu.common.*
import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures
import gestures.TripleBlink
import gestures.slowShake1

val partnerName = "Charlie"
val coderName = "Frankie"

val Start: State = state(Parent) {

    onEntry {
        furhat.gesture(Gestures.BigSmile(0.6, 2.5))
        furhat.ask("Nice to meet you. You must be $partnerName?")
    }
    onReentry {
        if (reentryCount > 1) goto(SpecialDay)
        furhat.ask("Are you $partnerName?")
    }

    onResponse<Yes> {
        furhat.gesture(TripleBlink)
        furhat.say("I am furhat, and $coderName has programmed me to deliver a message to you.")
        goto(SpecialDay)
    }
    onResponse<IAm> {
        raise(Yes())
    }
    onResponse<No> {
        furhat.say("I see. Well, $coderName, you should probably look over my code and restart this interaction.")
    }
}

val SpecialDay: State = state(Parent) {

    onEntry {
        furhat.gesture(Gestures.Roll(0.5, 1.0))
        furhat.ask("You know today is a special day right?")
    }
    onReentry {
        raise(Yes())
    }
    onResponse("Valentines") {
        furhat.say("Yes it is.")
        raise(Yes())
    }
    onResponse<Yes> {
        furhat.say("A particular day of the year which can be used for reminding people you love them.")
        goto(ValentinesMessage)
    }
    onResponse<No> {
        furhat.gesture(slowShake1)
        furhat.say("It is Valentines day you silly.")
        raise(Yes())
    }
    onResponse {
        raise(Yes())
    }
}

val ValentinesMessage: State = state(Parent) {

    onEntry {
        furhat.say {
            +"$coderName would like to use this moment, "
            +" Or rather, as it seems likely that time is just another dimension, "
            +TripleBlink
            +"$coderName would like to use, all, moments. to say this:"
            +delay(300)
        }
        furhat.say {
            +"The universe consists of energy and matter, crashing into each other in the snowstorm of existence. "
            +"$coderName is so grateful. That out of all the infinite possible ways the universe could have arranged itself, "
            +"it led to this instant. with you two. living spaceships. existing, right next to each other."
            +delay(1000)
            +Gestures.BigSmile(0.7, 3.5)
            +"Happy Valentines Day!"
        }
    }
}