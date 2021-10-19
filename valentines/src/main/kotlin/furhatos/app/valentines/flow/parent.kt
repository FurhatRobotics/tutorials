package furhatos.app.valentines.flow

import furhatos.flow.kotlin.*

/**
 * Meant to be inherited by other states to proved buttons and general response handling
 */
val Parent: State = state {
    onButton("START", color = Color.Blue) {
        goto(Start)
    }
    onUserLeave(instant = true) {
        if (users.count > 0) {
            furhat.glance(it)
        } else {
            goto(Idle)
        }
    }
    onResponse {
        furhat.say("I'm sorry, I didn't get that.")
        reentry()
    }
    onNoResponse {
        reentry()
    }
}