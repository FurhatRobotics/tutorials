package furhatos.app.doyoulikerobots.flow

import furhatos.app.doyoulikerobots.flow.main.Greeting
import furhatos.app.doyoulikerobots.flow.main.Sleeping
import furhatos.app.doyoulikerobots_start.setting.DISTANCE_TO_ENGAGE
import furhatos.app.doyoulikerobots_start.setting.MAX_NUMBER_OF_USERS
import furhatos.flow.kotlin.State
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.state
import furhatos.flow.kotlin.users
import furhatos.flow.kotlin.voice.Voice

val Init: State = state() {
    init {
        /** Set our default interaction parameters */
        users.setSimpleEngagementPolicy(DISTANCE_TO_ENGAGE, MAX_NUMBER_OF_USERS)

        /** Set the persona for the interaction **/
        furhat.voice = Voice("Matthew-Neural")
        furhat.character = "Alex"

        /** start the interaction */
        when (users.count) {
            0 -> goto(Sleeping)
            1  -> {
                furhat.attend(users.random)
                goto(Greeting)
            }
            else -> {
                furhat.attendAll()
                goto(Greeting)
            }
        }

    }
}
