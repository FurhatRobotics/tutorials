package furhatos.app.assetcollectionexample.flow.behaviorlibrary

import furhat.libraries.standard.BehaviorLib
import furhat.libraries.standard.BehaviorLib.behaviorLib
import furhatos.app.assetcollectionexample.flow.MenuParent
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.state

val behaviorLibExample = state(MenuParent) {
    /** From Asset Collection
     * randomHeadMovements is a partial state with OnTime triggers for making random head movements at specified intervals.
     * Triggers of a partial state can be included in a state with "include()"
     */
    include(BehaviorLib.AutomaticMovements.randomHeadMovements(repetitionPeriod = 5000..10000))

    onEntry {
        furhat.say("In this state my head will move randomly to give me a more natural stance.")
        furhat.say("You can also try switching characters.")
    }

    onButton("Without head movements") {
        /** Set value to avoid AutomaticHeadMovements to interfere with other head movements defined in a gesture.
         * We assume gestures are not longer than 3000 ms **/
        BehaviorLib.AutomaticMovements.autoHeadMovementDelay(15000)
        furhat.say("My head should stay still for a while.")
        furhat.say("This function should be used if gestures are played in parallel.")
    }

    onButton("Switch to another character") {
        val newChar = furhat.characters.random()
        furhat.say("Switching to $newChar")
        behaviorLib.switchToCharacter(newChar)
    }

    onExit {
        behaviorLib.switchToCharacter("Alex")
    }
}