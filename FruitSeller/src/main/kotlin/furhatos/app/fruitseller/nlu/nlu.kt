package furhatos.app.fruitseller.nlu

import furhatos.nlu.ComplexEnumEntity
import furhatos.nlu.EnumEntity
import furhatos.nlu.Intent
import furhatos.nlu.ListEntity
import furhatos.nlu.common.Number
import furhatos.util.Language

class Fruit : EnumEntity(stemming = true, speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf("banana", "orange", "apple", "cherimoya")
    }
}

class BuyFruit(var fruits : FruitList? = null) : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("@fruits", "I want @fruits", "I would like @fruits", "I want to buy @fruits")
    }
}

class RequestOptions: Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("What options do you have?",
            "What fruits do you have?",
            "What are the alternatives?",
            "What do you have?")
    }
}

class FruitList : ListEntity<QuantifiedFruit>()

class QuantifiedFruit(
    var count : Number? = Number(1),
    var fruit : Fruit? = null) : ComplexEnumEntity() {
    override fun getEnum(lang: Language): List<String> {
        return listOf("@count @fruit", "@fruit")
    }

    override fun toText(): String {
        return generate("$count " + if (count?.value == 1) fruit?.value else "${fruit?.value}" + "s")
    }
}