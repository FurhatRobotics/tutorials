package furhatos.app.assetcollectionexample.flow

import furhatos.app.assetcollectionexample.flow.behaviorlibrary.behaviorLibExample
import furhatos.app.assetcollectionexample.flow.gestureslibrary.gesturesLibExample
import furhatos.app.assetcollectionexample.flow.nluLibrary.nluLibExample
import furhatos.app.assetcollectionexample.flow.usersLibrary.usersLibExample
import furhatos.app.assetcollectionexample.flow.utilsLibrary.utilsLibExample
import furhatos.flow.kotlin.Color
import furhatos.flow.kotlin.State
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.state

val Menu: State = state {

    onEntry {
        furhat.say("Which library do you want to test?", "Which library do you want to learn about?")
    }

    onButton("BehaviorLib") {
        goto(behaviorLibExample)
    }

    onButton("GesturesLib") {
        goto(gesturesLibExample)
    }

    onButton("NluLib") {
        goto(nluLibExample)
    }

    onButton("UsersLib") {
        goto(usersLibExample)
    }

    onButton("UtilsLib") {
        goto(utilsLibExample)
    }

    onButton("About this skill") {
        furhat.say("This skill demonstrates the different features and functions that are available in the Asset Collection Library, " +
                "that is one part of the Furhat Library. Read more about the Furhat Library in our docs page at docs at furhat dot i o. " +
                "In short, the asset collection is a place where we add experimental features and useful functions that have not yet " +
                "been included in directly in the Furhat platform. You can get access to these features directly from intelliJ. " +
                "In the future we hope to also include features and functions created by people from the Furhat Community. ")
    }
}

val MenuParent: State = state {
    onButton("Menu", color = Color.Red) {
        goto(Menu)
    }
}