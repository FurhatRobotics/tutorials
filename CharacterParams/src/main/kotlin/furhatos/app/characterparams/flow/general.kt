package furhatos.app.characterparams.flow

import furhatos.flow.kotlin.*
import furhatos.gestures.ARKitParams
import furhatos.util.*

val Idle: State = state {

    init {
        furhat.setVoice(Language.ENGLISH_US, Gender.MALE)
    }

    onEntry {
        goto(Start)

    }
}

