package furhatos.app.multilanguage.util

import furhatos.app.multilanguage.flow.log
import furhatos.flow.kotlin.Furhat
import furhatos.util.Language

var phrases = mutableMapOf<String, Phrase>()

fun List<String>.enumerateAsString(): String {
    return dropLast(2).joinToString("") { "$it, " } + takeLast(2).joinToString(" and ")
}

fun String.splitToList(delimiter: String) = split(delimiter).map { it.trim() }.filter { it.isNotBlank() }

fun Furhat.sayInLocalLang(string: String) {
    say(getTranslation(string, outputLanguage))
}

fun Furhat.say(string: String, language: Language) {
    say(getTranslation(string, language))
}

fun getTranslation(string: String, language: Language): String {
    log.debug(language.code + 1)
    return when {
        language == Language.SWEDISH -> phrases[string]?.swe ?: "Jag vet inte"
        language == Language.GERMAN -> phrases[string]?.de ?: "Ich weiß nicht"
        language == Language.ARABIC_GULF -> phrases[string]?.ar ?: "لا أعرف"
        language.code == "arb" -> phrases[string]?.ar ?: "لا أعرف"
        else -> string // english
    }
}