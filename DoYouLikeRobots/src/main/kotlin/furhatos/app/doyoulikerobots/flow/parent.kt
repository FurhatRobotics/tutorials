package furhatos.app.doyoulikerobots.flow

import furhatos.app.doyoulikerobots.flow.main.Sleeping
import furhatos.flow.kotlin.*

val Parent: State = state {

    onUserLeave(instant = true) {
        when {
            users.count == 0 -> goto(Sleeping)
            it == users.current -> furhat.attend(users.other)
        }
    }

    onUserEnter(instant = true) {
        furhat.glance(it)
    }
}