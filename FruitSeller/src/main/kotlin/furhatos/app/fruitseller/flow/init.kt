package furhatos.app.fruitseller.flow

import furhatos.app.fruitseller.flow.main.Idle
import furhatos.app.fruitseller.setting.distanceToEngage
import furhatos.app.fruitseller.setting.maxNumberOfUsers
import furhatos.flow.kotlin.*
import furhatos.flow.kotlin.voice.Voice

val Init : State = state() {
    init {
        /** Set our default interaction parameters */
        users.setSimpleEngagementPolicy(distanceToEngage, maxNumberOfUsers)
        furhat.voice = Voice("Matthew")
        /** start the interaction */
        goto(Idle)
    }
}
