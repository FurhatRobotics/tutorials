package furhatos.app.assetcollectionexample.flow.utilsLibrary.googleSheets

import furhat.libraries.standard.utils.GoogleSheetsIntegration
import furhatos.flow.kotlin.Color
import furhatos.flow.kotlin.State
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.state

val exploreTagGestures: State = state {
    onEntry {
        furhat.say("In this state you can try the different gestures available in the Google Tags. " +
                "You can add yours as well in the GoogleSheetsIntegration.utteranceGestureTagMap map.")
    }

    for (gesture in GoogleSheetsIntegration.utteranceGestureTagMap) {
        onButton(gesture.key) {
            furhat.gesture(gesture.value)
        }
    }

    onButton("Explore tags", color = Color.Yellow) {
        goto(exploreTags)
    }
}
