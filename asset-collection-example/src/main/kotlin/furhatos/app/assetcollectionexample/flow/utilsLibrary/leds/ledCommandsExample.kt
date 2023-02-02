package furhatos.app.assetcollectionexample.flow.utilsLibrary.leds

import furhat.libraries.standard.UtilsLib
import furhatos.app.assetcollectionexample.flow.utilsLibrary.UtilsLibParent
import furhatos.flow.kotlin.*
import java.awt.Color

val ledCommandsExample = state(UtilsLibParent) {

    init {
        /** Have a background state that makes LEDs glow
         * We recommend that you use define that parallel state in the init phase of your skill
         */
        parallel(abortOnExit = false) { goto(UtilsLib.LEDs.PulseLEDState) }

        /** Changes the LED colors when furhat starts/stops listening
         * We recommend that you use define that parallel state in the init phase of your skill
         */
        parallel(abortOnExit = false) { goto(UtilsLib.LEDs.ListenGlowState(listenLedColor = Color.GREEN, defaultLedColor = Color.GRAY)) }
        send(UtilsLib.LEDs.turnOffListenGlow) //Wait the
    }

    onButton("Turn on pulsing", color = furhatos.flow.kotlin.Color.Green) {
        send(UtilsLib.LEDs.pulseLEDStart(pulseColor = Color.BLUE))
    }

    onButton("Turn off pulsing", color = furhatos.flow.kotlin.Color.Red) {
        send(UtilsLib.LEDs.pulseLEDStop)
    }

    onButton("Turn on listenGlow", color = furhatos.flow.kotlin.Color.Green) {
        send(UtilsLib.LEDs.turnOnListenGlow)
        furhat.say("Now I'm going to ask you a few insignificant questions to you can see the effect of listen glow")
        questionIndex = 0
        nextQuestion()
    }

    onResponse {
        furhat.say("Very well", "Excellent", "How wonderful")
        nextQuestion()
    }

    onNoResponse {
        furhat.say("I'll take your silence as an answer.", "It's ok not to know, you can still try",
            "If you don't feel like answering I will sharpen my knowledge elsewhere")
        nextQuestion()
    }

    onButton("Turn off listenGlow", color = furhatos.flow.kotlin.Color.Red) {
        send(UtilsLib.LEDs.turnOffListenGlow)
    }

    onExit {
        send(UtilsLib.LEDs.pulseLEDStop)
        send(UtilsLib.LEDs.turnOffListenGlow)
    }
}


// Useless questions logic to demonstrate the listenGlow functionality
val listQuestions = listOf("Do you prefer cactuses of lemons?", "But why?", "Where is the sun?", "Are you a cat or a dog person?")
var questionIndex = 0
fun FlowControlRunner.nextQuestion() {
    if (questionIndex < listQuestions.size) {
        furhat.ask(listQuestions[questionIndex++])
    }
}