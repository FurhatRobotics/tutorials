package furhatos.app.assetcollectionexample

import furhatos.app.assetcollectionexample.flow.Idle
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.state
import furhatos.flow.kotlin.users

val Init = state {

    init {
        users.setSimpleEngagementPolicy(distanceToEngage, maxNumberOfUsers)
        furhat.voice = englishVoice
        furhat.mask = "Adult"
        furhat.character = "Alex"

        if (users.count >= 1) {
            furhat.attend(users.random)
        }
        furhat.say("Hello ! This skill is a demonstration of what functions are available in the Standard Library Collection and how they can be used.")
        furhat.say("There are five different libraries available. Please wizard your way through the skill to explore them.")

        goto(Idle)
    }
}