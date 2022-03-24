package furhatos.app.fruitseller.flow.main

import furhatos.app.fruitseller.nlu.FruitList
import furhatos.records.User

class FruitData {
    var fruits : FruitList = FruitList()
}

val User.order : FruitData
    get() = data.getOrPut(FruitData::class.qualifiedName, FruitData())