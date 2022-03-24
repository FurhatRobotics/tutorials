package furhatos.app.fruitseller.flow

import furhatos.app.fruitseller.flow.main.orderReceived
import furhatos.app.fruitseller.nlu.BuyFruit
import furhatos.app.fruitseller.nlu.Fruit
import furhatos.app.fruitseller.nlu.RequestOptions
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.onResponse
import furhatos.flow.kotlin.state
import furhatos.nlu.common.Yes

val Options = state(Parent) {
    onResponse<BuyFruit> {
        val fruits = it.intent.fruits
        if (fruits != null) {
            goto(orderReceived(fruits))
        }
        else {
            propagate()
        }
    }

    onResponse<RequestOptions> {
        furhat.say("We have ${Fruit().optionsToText()}")
        furhat.ask("Do you want some?")
    }

    onResponse<Yes> {
        random(
            { furhat.ask("What kind of fruit do you want?") },
            { furhat.ask("What type of fruit?") }
        )
    }
}