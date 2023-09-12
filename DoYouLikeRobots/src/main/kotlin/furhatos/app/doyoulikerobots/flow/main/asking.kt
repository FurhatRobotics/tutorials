package furhatos.app.doyoulikerobots.flow.main

import furhatos.app.doyoulikerobots.flow.Parent
import furhatos.app.doyoulikerobots.setting.likeRobots
import furhatos.flow.kotlin.*
import furhatos.nlu.common.DontKnow
import furhatos.nlu.common.Maybe
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes


val Asking: State = state(Parent) {
    onEntry {
        var isFirstUser = true
        for (user in furhat.users.list) {
            furhat.attend(user)
            call(DoYouLikeRobots(isFirstUser))
            isFirstUser = false
        }
        goto(Ending)
    }

}

fun DoYouLikeRobots(firstUser: Boolean): State = state(Parent) {
    onEntry {
        if (!firstUser) furhat.say("And what about you? ")
        furhat.ask {
            random {
                +"Do you like robots?"
                +"Would you say you enjoy robots?"
                +"Are you into robots?"
            }
        }
    }
    onReentry {
        when (reentryCount) {
            1 -> furhat.ask("I didn't quite hear you. Do you like robots?")
            2 -> furhat.ask("One more time. Do you like robots? ")
            else -> terminate()
        }
    }
    onResponse<Yes> {
        furhat.say {
            random {
                +"How nice! "
                +"That's nice! "
                +"Lovely! "
            }
        }
        users.current.likeRobots = true
        terminate()
    }
    onResponse<No> {
        furhat.say {
            random {
                +"That's a shame! "
                +"Too bad! "
                +"Bummer! "
            }
        }
        users.current.likeRobots = false
        terminate()
    }
    onResponse<DontKnow> {
        furhat.say("That's ok. ")
        terminate()
    }
    onResponse<Maybe> {
        furhat.say("I'm going to take that as a yes.  ")
        users.current.likeRobots = true
        terminate()
    }
    onNoResponse {
        reentry()
    }
    /** User said something else unexpected **/
    onResponse {
        reentry()
    }
}