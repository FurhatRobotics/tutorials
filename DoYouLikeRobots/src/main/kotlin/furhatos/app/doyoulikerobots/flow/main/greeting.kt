package furhatos.app.doyoulikerobots.flow.main

import furhat.libraries.standard.GesturesLib
import furhatos.app.doyoulikerobots.flow.Parent
import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures
import furhatos.nlu.common.Greeting
import furhatos.records.Location

val Greeting: State = state(Parent) {
    onEntry {

        /** Greet the user **/
        furhat.say {
            random {
                +"Hello there. "
                +"Hi there! "
                +"Hello! "
            }
            +GesturesLib.PerformSmile1
        }

        /** ... and other users present **/
        when (users.count) {
            in 0..1 -> {} // do nothing else
            2 -> {
                furhat.say {
                    +behavior { furhat.attend(users.other) }
                    random {
                        +"And hello there to you to.  "
                        +"And hi there to you as well.  "
                        +"both of you. "
                    }
                    furhat.gesture(Gestures.BigSmile)
                    +behavior { furhat.attendAll() }
                }
            }
            else -> { // with a max number of 2 users - this should never happen.
                furhat.glance(Location.LEFT)
                furhat.glance(Location.RIGHT)
                furhat.say("all of you")
            }
        }
        /** leave the conversation open for user to return the greeting **/
        furhat.listen()
    }

    /** User returned the greeting **/
    onResponse<Greeting> {
        val canIAskYouSomething = furhat.askYN("Can I ask you something?")
        if (canIAskYouSomething == true) {
            goto(Asking)
        } else {
            furhat.say("Sorry to bother you. ")
            goto(Sleeping)
        }
    }

    /** ... or not return the greeting **/
    onNoResponse {
        raise(Greeting()) // We'll handle it the same way as if the user actually said a greeting.
    }

    /** User said something else **/
    onResponse {
        furhat.say("Anyway. ")
        raise(Greeting()) // We are just going to ignore what they said, and keep the conversation flow going where we want it to go.
    }
}
