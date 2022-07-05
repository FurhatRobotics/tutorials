package furhatos.app.multilanguage.flow.main

import furhatos.app.multilanguage.nlu.Animal
import furhatos.app.multilanguage.nlu.ILoveYou
import furhatos.app.multilanguage.nlu.Restart
import furhatos.app.multilanguage.util.getIntent
import furhatos.app.multilanguage.util.sayInLocalLang
import furhatos.flow.kotlin.State
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.onResponse
import furhatos.flow.kotlin.state

val FavAnimal: State = state() {
    onEntry {
        furhat.sayInLocalLang("Do you prefer cats, or dogs?")
        furhat.listen()
    }
    onResponse<Animal> {
        furhat.say("Oh. ${it.intent.animal}")
        goto(Greeting)
    }
    onResponse<ILoveYou> {
        furhat.say("Hurra")
        goto(Greeting)
    }
    onResponse<Restart> {
        furhat.say("Ok")
        goto(Greeting)
    }
    onResponse(getIntent("HelloIntent")) {
        furhat.say("Hello everyone")
        furhat.listen()
    }
    onResponse {
        furhat.ask("Hi?")
    }
}