package furhatos.app.objectidentifier.flow

import furhatos.event.Event
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.state

/**
 * Events used for communication between the thread and the flow.
 */
class EnterEvent(val objects: List<String>): Event()
class LeaveEvent(val objects: List<String>): Event()

/**
 * Main flow that starts the camera feed and awaits events sent from the thread
 */
val Main = state {

    onEntry {
        furhat.cameraFeed.enable()
        furhat.say("Welcome to this camera feed demonstration, please place an item in front of me. Maybe I can detect what it is.")
    }

    onEvent<EnterEvent> {// Objects that enter the view
        if (it.objects.size > 1) {
            furhat.say("You showed me multiple objects. ${it.objects.joinToString(" and ")}")
        } else {
            furhat.say("Oh cool, that is a ${it.objects[0]}")
        }
    }

    onEvent<LeaveEvent> {
        if (it.objects.size > 1) { //Objects that leave the view
            furhat.say("You removed multiple objects. ${it.objects.joinToString(" and ")}")
        } else {
            furhat.say("No, don't remove that, I love the ${it.objects[0]}")
        }
    }

    onExit {
        furhat.cameraFeed.disable()
    }
}