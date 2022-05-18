package furhatos.app.assetcollectionexample.flow

import furhat.libraries.standard.AutomaticHeadMovements
import furhat.libraries.standard.utils.attendClosestUser
import furhatos.flow.kotlin.*

val Idle: State = state {

    /** From Asset Collection
     * RandomHeadMovements is a partial state with OnTime triggers for making random head movements at specified intervals.
     * Triggers of a partial state can be included in a state with "include()"
     **/
    include(AutomaticHeadMovements.RandomHeadMovements(repetitionPeriod = 5000..10000))

    onEntry {
        if (users.count == 1) {
            goto(Start)
        } else if (users.count > 1) {
            furhat.attendClosestUser() /** From Asset Collection**/
            goto(Start)
        } else {
            furhat.attendNobody()
            /** Set value to avoid AutomaticHeadMovements to interfere with other head movements defined in a gesture. We assume gestures are not longer than 3000 ms **/
            AutomaticHeadMovements.autoHeadMovementDelay(3000)
        }
    }

    onUserEnter {
        furhat.attend(it)
        goto(Start)
    }
}