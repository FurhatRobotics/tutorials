package furhatos.app.characterparams.flow

import furhatos.app.characterparams.gestures.Character1
import furhatos.app.characterparams.gestures.Character2
import furhatos.app.characterparams.gestures.CharacterChildMask
import furhatos.app.characterparams.gestures.CharacterReset
import furhatos.flow.kotlin.*
import furhatos.gestures.ARKitParams
import furhatos.util.*

val Examples: State = state() {
    onEntry {
        furhat.say("Hi. Click wizard buttons to see different characters.")
    }
    onButton("CharacterReset", color = Color.Blue, instant = true) {
        furhat.gesture(CharacterReset)
    }
    onButton("Character1", color = Color.Blue, instant = true) {
        furhat.gesture(Character1)
    }
    onButton("Character2", color = Color.Blue, instant = true) {
        furhat.gesture(Character2)
    }
    onButton("CharacterChildMask", color = Color.Blue, instant = true) {
        furhat.gesture(CharacterChildMask)
    }
    onButton("Back", color = Color.Red, instant = true) {
        goto(Start)
    }
}
