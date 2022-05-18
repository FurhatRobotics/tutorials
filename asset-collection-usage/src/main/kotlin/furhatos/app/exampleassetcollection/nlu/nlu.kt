package furhatos.app.exampleassetcollection.nlu

import furhatos.nlu.EnumEntity
import furhatos.util.Language

open class UpdateSheets : EnumEntity(speechRecPhrases = true) {

    override fun getEnum(lang: Language): List<String> {
        return listOf("update", "please update the standard library collection", "update assets collection",
            "reload the lines", "clear intents")
    }
}
