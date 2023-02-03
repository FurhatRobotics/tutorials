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
}

val MenuParent: State = state {
    onButton("Menu", color = Color.Red) {
        goto(Menu)
    }

    onExit {
        println("Leaving parent")
    }
}