package furhatos.app.assetcollectionexample.flow.gestureslibrary

import furhat.libraries.standard.GesturesLib
import furhatos.app.assetcollectionexample.flow.WizardParentButtons
import furhatos.flow.kotlin.Color
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.state
import furhatos.gestures.Gesture
import kotlin.reflect.full.*

val gesturesLibExample = state(WizardParentButtons) {

    onEntry {
        furhat.say("In this state you can test my different library gestures.")
        furhat.say("The blue green gestures can be modified with different parameters, but here use only the default ones.")
    }

    /**
     * Fixed gesture expressions.
     * Example: furhat.gesture(GesturesLib.PerformWakeUpWithHeadShake)
     */
    for (gesture in GesturesLib::class.memberProperties) {
        onButton(gesture.name) {
            furhat.gesture(gesture.call(GesturesLib) as Gesture)
        }
    }

    /**
     * Modifiable gesture functions.
     * Example: furhat.gesture(GesturesLib.ExpressHappiness1(2.0, 1.5))
     */
    for (gesture in GesturesLib::class.memberFunctions.filter { it.returnType == Gesture::class.createType() }) {
        onButton(gesture.name, color = Color.Green) {
            furhat.gesture(gesture.callBy(mapOf(gesture.instanceParameter!! to GesturesLib)) as Gesture)
        }
    }
}