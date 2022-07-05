/*
 * Furhat Robotics, All Rights Reserved, 2018.
 */

package furhatos.nlu

import furhatos.app.multilanguage.flow.log
import furhatos.app.multilanguage.util.intentsMap
import furhatos.util.Language


class GeneratedIntent(val key: String) : SimpleIntent() {
    override fun getExamples(lang: Language): List<String> {
        //return
        log.debug(lang.code)
        log.debug(intentsMap[key])
        val list = intentsMap[key]?.get(lang)
        return if (list.isNullOrEmpty()) {
            log.debug("empty list")
            listOf("")
        } else {
            log.debug(list)
            list
        }
    }
}

open class MultiLangIntent(
    val name: String? = null,
    val examples: List<String>,
    val speechRecPhrases: Boolean = false
) : IntentCandidate, IntentInstance {

    override fun getIntentCandidate(): IntentCandidate {
        return this;
    }

    override fun getEntities(): Map<String, Any> {
        return mapOf()
    }

    constructor(examples: List<String>) : this(null, examples)

    constructor(vararg examples: String) : this(null, examples.asList())

    override fun getExamples(lang: Language): List<String> {
        return if (examples.isNotEmpty())
            examples
        else {
            readExamplesFromResource(lang)
        }
    }

    override fun findEntityEdges(chart: EntityChart): Map<String, EntityEdge> {
        return mapOf()
    }

    override fun createInstance(entities: Map<String, EntityEdge>): IntentInstance {
        return this
    }

    override fun getIntentEntityFields(): Map<String, Class<out Entity>> {
        return mapOf()
    }

    override fun getIntentName(): String {
        return if (name != null) {
            name
        } else if (javaClass != SimpleIntent::class.java) {
            javaClass.name
        } else {
            val hashCode =
                if (examples.isNotEmpty()) {
                    examples.hashCode()
                } else {
                    hashCode()
                }
            "SimpleIntent@" + hashCode
        }
    }

    override fun getSimpleName(): String {
        return if (name != null) {
            name
        } else if (javaClass != SimpleIntent::class.java) {
            javaClass.simpleName
        } else {
            try {
                getExamples(Language.ENGLISH_US).getOrNull(0)?.let { "SimpleIntent($it)" }
            } catch (_: Exception) {
                null
            } ?: "SimpleIntent"
        }
    }

    override fun getSpeechRecPhrases(lang: Language): List<String> {
        if (speechRecPhrases == true) {
            return getExamples(lang)
        } else {
            return listOf()
        }
    }

    override fun toString(): String {
        return simpleName
    }

    override fun equals(other: Any?): Boolean {
        return other is SimpleIntent && other.intentName == this.intentName
    }

    override fun toStringIndent(): String {
        return simpleName
    }

}

fun intent(vararg examples: String): SimpleIntent = SimpleIntent(*examples)
