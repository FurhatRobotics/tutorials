package furhatos.app.doyoulikerobots.flow.main

import furhat.libraries.standard.GesturesLib
import furhatos.app.doyoulikerobots.flow.Parent
import furhatos.flow.kotlin.*
import furhatos.records.Location

val Ending: State = state(Parent) {
    onEntry {
        furhat.say {
            random {
                +"That was all I wanted to ask you. "
                +"That's all I had to say. "
            }
            +behavior { furhat.attend(users.other) }
            random{
                +"Have a nice day!"
                +"Have a lovely day! "
                +"I wish you a great rest of the day, "
            }
            +GesturesLib.PerformSmile1
            +behavior { furhat.attend(Location.DOWN) }
        }
    }
    /** Wait a little before going to sleep, to avoid the interaction to restart too quickly. **/
    onTime(15000) {
        goto(Sleeping)
    }

}