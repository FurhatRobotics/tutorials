package furhatos.app.valentines.nlu

import furhatos.nlu.Intent
import furhatos.util.Language

class IAm : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "I am",
            "I sure am"
        )
    }
}

class Valentines : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "Valentines",
            "It is Valentines day"
        )
    }
}