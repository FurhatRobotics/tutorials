package furhatos.app.assetcollectionexample.flow.buttonStates

import furhat.libraries.standard.utils.GoogleSheetsIntegration
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.state

val exploreTagGestures = state(backToIdle) {
    onEntry {
        furhat.say("On this state you can try the different gestures available in the Google Tags. " +
                "You can add yours as well in the GoogleSheetsIntegration.utteranceGestureTagMap map.")
    }

    for (gesture in GoogleSheetsIntegration.utteranceGestureTagMap) {
        onButton(gesture.key) {
            furhat.gesture(gesture.value)
        }
    }
}
