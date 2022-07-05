package furhatos.app.multilanguage.util

import furhatos.app.multilanguage.MultilanguageSkill
import furhatos.app.multilanguage.flow.log
import furhatos.nlu.GeneratedIntent
import furhatos.skills.Skill
import furhatos.util.Language
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import java.io.InputStreamReader

var intentsMap = mutableMapOf<String, MutableMap<Language, List<String>>>()
var genIntents = mutableMapOf<String, GeneratedIntent>()

var entitiesMap = mutableMapOf<String, MutableMap<Language, List<String>>>()

fun getIntent(key: String): GeneratedIntent {
    if (genIntents[key] != null) {
        return genIntents[key]!!
    } else {
        return GeneratedIntent("")
    }
}

fun createTranslations() {
    val props = Skill.getSkillProperties()
    val translationsCSV = props.getProperty("translationsCSV")
    val csvParser =
        if (translationsCSV != null) {
            val surveyResource = MultilanguageSkill::class.java.getResourceAsStream(translationsCSV)
            CSVParser(InputStreamReader(surveyResource), CSVFormat.DEFAULT.withFirstRecordAsHeader())
        } else {
            throw RuntimeException("'translationsCSV' not specified in skill.properties")
        }

    for (csvRecord in csvParser) {
        val record = csvRecord.toMap()
        val english = (record["English"] ?: "").trim()
        var phrase = Phrase(
            en = english,
            swe = (record["Swedish"] ?: "").trim(),
            de = (record["German"] ?: "").trim(),
            ar = (record["Arabic"] ?: "").trim()
        )
        if (phrases.containsKey(english)) {
            throw RuntimeException("Same key in English exists twice: " + english)
        }
        phrases[english] = phrase
    }
}

fun createIntents() {
    val props = Skill.getSkillProperties()
    val intentsCSV = props.getProperty("intentsCSV")
    val csvParser =
        if (intentsCSV != null) {
            val surveyResource = MultilanguageSkill::class.java.getResourceAsStream(intentsCSV)
            CSVParser(InputStreamReader(surveyResource), CSVFormat.DEFAULT.withFirstRecordAsHeader())
        } else {
            throw RuntimeException("'intentsCSV' not specified in skill.properties")
        }

    var currentKey = ""
    var intentEnList = mutableListOf<String>()
    var intentSweList = mutableListOf<String>()
    var intentDeList = mutableListOf<String>()
    var intentArList = mutableListOf<String>()

    println("creating intents")
    for (csvRecord in csvParser) {
        log.debug("Record")
        val record = csvRecord.toMap()
        val key = (record["Key"] ?: "").trim()
        log.debug(key)

        if (key.isNotEmpty()) {
            if (currentKey.isNotEmpty()) {
                // finalize the previous intent
                var intents = mutableMapOf<Language, List<String>>(
                    Language.ENGLISH_US to intentEnList.toList(),
                    Language.SWEDISH to intentSweList.toList(),
                    Language.GERMAN to intentDeList.toList(),
                    Language.ARABIC_GULF to intentArList.toList()
                )
                intentsMap[currentKey] = intents
                genIntents[currentKey] = GeneratedIntent(currentKey)
                println("created intent $currentKey")
                intentEnList.clear()
                intentSweList.clear()
                intentDeList.clear()
                intentArList.clear()
            }
            currentKey = key
        }
        val english = (record["English"] ?: "").trim()
        val swedish = (record["Swedish"] ?: "").trim()
        val german = (record["German"] ?: "").trim()
        val arabic = (record["Arabic"] ?: "").trim()

        if (english.isNotEmpty()) intentEnList.add(english)
        if (swedish.isNotEmpty()) intentSweList.add(swedish)
        if (german.isNotEmpty()) intentDeList.add(german)
        if (arabic.isNotEmpty()) intentArList.add(arabic)
    }
    if (currentKey.isNotEmpty()) {
        // finalize the previous intent
        var intents = mutableMapOf<Language, List<String>>(
            Language.ENGLISH_US to intentEnList.toList(),
            Language.SWEDISH to intentSweList.toList(),
            Language.GERMAN to intentDeList.toList(),
            Language.ARABIC_GULF to intentArList.toList()
        )
        intentsMap[currentKey] = intents
        genIntents[currentKey] = GeneratedIntent(currentKey)
        log.info("created intent $currentKey")
    }
}

fun createEntities() {
    val props = Skill.getSkillProperties()
    val entitiesCSV = props.getProperty("entitiesCSV")
    val csvParser =
        if (entitiesCSV != null) {
            val surveyResource = MultilanguageSkill::class.java.getResourceAsStream(entitiesCSV)
            CSVParser(InputStreamReader(surveyResource), CSVFormat.DEFAULT.withFirstRecordAsHeader())
        } else {
            throw RuntimeException("'entitiesCSV' not specified in skill.properties")
        }

    var currentKey = ""
    var entityEnList = mutableListOf<String>()
    var entitySweList = mutableListOf<String>()
    var entityDeList = mutableListOf<String>()
    var entityArList = mutableListOf<String>()

    println("creating entities")
    for (csvRecord in csvParser) {
        log.debug("Record")
        val record = csvRecord.toMap()
        //println("1"+record.toString())
        val key = (record["Key"] ?: "").trim()
        log.debug(key)

        if (key.isNotEmpty()) {
            if (currentKey.isNotEmpty()) {
                // finalize the previous intent
                var entities = mutableMapOf<Language, List<String>>(
                    Language.ENGLISH_US to entityEnList.toList(),
                    Language.SWEDISH to entitySweList.toList(),
                    Language.GERMAN to entityDeList.toList(),
                    Language.ARABIC_GULF to entityArList.toList()
                )
                entitiesMap[currentKey] = entities
                log.info("created entity $currentKey")
                entityEnList.clear()
                entitySweList.clear()
                entityDeList.clear()
                entityArList.clear()
            }
            currentKey = key
        }
        val english = (record["English"] ?: "").trim()
        val swedish = (record["Swedish"] ?: "").trim()
        val german = (record["German"] ?: "").trim()
        val arabic = (record["Arabic"] ?: "").trim()

        if (english.isNotEmpty()) entityEnList.add(english)
        if (swedish.isNotEmpty()) entitySweList.add(swedish)
        if (german.isNotEmpty()) entityDeList.add(german)
        if (arabic.isNotEmpty()) entityArList.add(arabic)
    }
    if (currentKey.isNotEmpty()) {
        // finalize the previous intent
        var entities = mutableMapOf<Language, List<String>>(
            Language.ENGLISH_US to entityEnList.toList(),
            Language.SWEDISH to entitySweList.toList(),
            Language.GERMAN to entityDeList.toList(),
            Language.ARABIC_GULF to entityArList.toList()
        )
        entitiesMap[currentKey] = entities
        log.info("created entity $currentKey")
    }
}