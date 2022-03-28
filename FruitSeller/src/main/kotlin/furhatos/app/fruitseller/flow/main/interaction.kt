package furhatos.app.fruitseller.flow.main

import furhatos.app.fruitseller.flow.Options
import furhatos.app.fruitseller.nlu.BuyFruit
import furhatos.app.fruitseller.nlu.Fruit
import furhatos.app.fruitseller.nlu.FruitList
import furhatos.app.fruitseller.nlu.RequestOptions
import furhatos.flow.kotlin.*
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes
import furhatos.util.Language

val TakingOrder = state(parent = Options) {
    onEntry {
        random(
            { furhat.ask("How about some fruits?") },
            { furhat.ask("Do you want some fruits?") }
        )
    }
    onResponse<Yes> {
        random(
            { furhat.ask("What kind of fruit do you want?") },
            { furhat.ask("What type of fruit?") }
        )
    }

    onResponse<No> {
        furhat.say("Okay, that's a shame. Have a splendid day!")
        goto(Idle)
    }

    onResponse<RequestOptions> {
        furhat.say("We have ${Fruit().optionsToText()}")
        furhat.ask("Do you want some?")
    }

    onResponse<BuyFruit> {
        val fruits = it.intent.fruits
        if (fruits != null) {
            goto(orderReceived(fruits))
        }
        else {
            propagate()
        }
    }
}

fun orderReceived(fruits: FruitList): State = state(Options) {
    onEntry {
        furhat.say("${fruits.text}, what a lovely choice!")
        fruits.list.forEach {
            users.current.order.fruits.list.add(it)
        }
        furhat.ask("Anything else?")
    }

    onReentry {
        furhat.ask("Did you want something else?")
    }

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
        furhat.say("We have ${Fruit().getEnum(Language.ENGLISH_US).joinToString(", ")}")
        furhat.ask("Do you want some?")
    }

    onResponse<Yes> {
        random(
            { furhat.ask("What kind of fruit do you want?") },
            { furhat.ask("What type of fruit?") }
        )
    }

    onResponse<No> {
        furhat.say("Okay, here is your order of ${users.current.order.fruits}. Have a great day!")
        goto(Idle)
    }
}