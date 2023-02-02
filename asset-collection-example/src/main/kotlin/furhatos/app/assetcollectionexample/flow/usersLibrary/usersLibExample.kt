package furhatos.app.assetcollectionexample.flow.usersLibrary

import furhat.libraries.standard.UsersLib.usersLib
import furhatos.app.assetcollectionexample.flow.MenuParent
import furhatos.flow.kotlin.FlowControlRunner
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.state
import kotlin.random.Random

val usersLibExample = state(MenuParent) {

    onEntry {
        furhat.say("In this state you can test different ways to handle users.")
        // Convenience method to get any short user, e.g. a child.
        if (usersLib.usersIncludeShortUser(-0.2)) {
            furhat.say("I see for instance that you have a short user in the audience")
        }
        furhat.say("You can try the different functions with the wizard buttons")
    }

    /** Furhat extensions */
    onButton("Attend closest user") {
        furhat.usersLib.attendClosestUser()
        furhat.say("Attending closest user")
    }

    /** FlowControlRunner functions */
    onButton("Get user from a random libs function") {
        val functionNumber = Random.nextInt(mapUserFlow.size)
        furhat.attend(mapUserFlow[functionNumber].first)
        furhat.say(mapUserFlow[functionNumber].second)
    }
}

val FlowControlRunner.mapUserFlow
    get() = listOf(
        usersLib.getCenterUser() to "Getting center user",
        usersLib.getLeftmostUser() to "Getting leftmost user",
        usersLib.getRightmostUser() to "Getting rightmost user",
        usersLib.getShortestUser() to "Getting shortest user",
        usersLib.getTallestUser() to "Getting tallest user"
    )