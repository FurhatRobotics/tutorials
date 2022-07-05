package furhatos.app.multilanguage.flow

import furhatos.app.multilanguage.MultilanguageSkill
import furhatos.app.multilanguage.flow.main.Idle
import furhatos.app.multilanguage.setting.distanceToEngage
import furhatos.app.multilanguage.setting.maxNumberOfUsers
import furhatos.app.multilanguage.util.createEntities
import furhatos.app.multilanguage.util.createIntents
import furhatos.app.multilanguage.util.createTranslations
import furhatos.flow.kotlin.State
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.state
import furhatos.flow.kotlin.users
import furhatos.flow.kotlin.voice.Voice
import furhatos.util.CommonUtils

// Get the log4J logger available from furhat.system.logger to print messages to the IntelliJ console and the log console in the web interface.
// Set the logging level in skill.properties
val log = CommonUtils.getLogger(MultilanguageSkill::class.java)

val Init: State = state() {
    init {
        /** Set our default interaction parameters */
        users.setSimpleEngagementPolicy(distanceToEngage, maxNumberOfUsers)
        furhat.voice = Voice("Matthew-Neural")
        furhat.character = "Alex"
        /** start the interaction */
        createTranslations()
        delay(200)
        createIntents()
        delay(200)
        createEntities()
        delay(1500)
        goto(Idle)
    }
}
