package furhatos.app.multilanguage.nlu

import furhatos.app.multilanguage.flow.log
import furhatos.app.multilanguage.util.entitiesMap
import furhatos.app.multilanguage.util.intentsMap
import furhatos.nlu.EnumEntity
import furhatos.nlu.Intent
import furhatos.util.Language

class ILoveYou : Intent() {
    override fun getExamples(lang: Language): List<String> {
        log.debug(intentsMap["ILoveYou"])
        return intentsMap["ILoveYou"]?.get(lang) ?: listOf("")
    }
}

class AnimalEntity : EnumEntity(speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        var list = entitiesMap["AnimalEntity"]?.get(lang)
        log.debug(entitiesMap["AnimalEntity"])
        return list ?: listOf("")
    }
}

class Animal(val animal: AnimalEntity? = null) : Intent() {
    override fun getExamples(lang: Language): List<String> {
        var list = intentsMap["Animal"]?.get(lang)
        log.debug(intentsMap["Animal"])
        return list ?: listOf("")
    }
}

class Restart : Intent() {
    override fun getExamples(lang: Language): List<String> {
        var list = intentsMap["Restart"]?.get(lang)
        return list ?: listOf("")
    }
}