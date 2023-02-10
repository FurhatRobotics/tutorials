package furhatos.app.assetcollectionexample.flow.gestureslibrary

import furhat.libraries.standard.GesturesLib
import furhatos.app.assetcollectionexample.flow.MenuParent
import furhatos.flow.kotlin.Color
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.state
import furhatos.gestures.Gesture
import kotlin.reflect.full.createType
import kotlin.reflect.full.instanceParameter
import kotlin.reflect.full.memberFunctions
import kotlin.reflect.full.memberProperties

val gesturesLibExample = state(MenuParent) {

    onEntry {
        furhat.say("In this state you can test my different library gestures.")
        furhat.say("The green gestures are functions and can be modified with different parameters.")
    }

    /**
     * Fixed gesture expressions.
     * Example: furhat.gesture(GesturesLib.PerformWakeUpWithHeadShake)
     *
     * NOTE: When using the PerformFallAsleepPersist gesture, make sure to set the priority to more than 1
     * (or the eyes will reopen with the default blinking movements)
     */
    for (gesture in GesturesLib::class.memberProperties) {
        onButton(gesture.name) {
            furhat.gesture(gesture.call(GesturesLib) as Gesture, priority = 2)
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