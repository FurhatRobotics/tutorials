package furhatos.app.valentines.flow

import furhatos.flow.kotlin.*
import furhatos.flow.kotlin.voice.PollyNeuralVoice
import furhatos.util.*

/**
 * Idle until a user arrives
 */
val Idle: State = state(Parent) {

    init {
        furhat.setVoice(Language.ENGLISH_US, Gender.MALE)
        furhat.voice = PollyNeuralVoice.Matthew()
        if (users.count > 0) {
            furhat.attend(users.random)
            goto(Start)
        }
    }

    onEntry {
        furhat.attendNobody()
    }

    onUserEnter {
        furhat.attend(it)
        goto(Start)
    }
}