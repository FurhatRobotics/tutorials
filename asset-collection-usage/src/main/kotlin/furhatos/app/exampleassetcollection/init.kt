package furhatos.app.exampleassetcollection

import furhat.libraries.standard.GesturesLib
import furhat.libraries.standard.UtilsLib
import furhatos.app.exampleassetcollection.flow.Idle
import furhatos.app.exampleassetcollection.settings.distanceToEngage
import furhatos.app.exampleassetcollection.settings.linkGoogleSheet
import furhatos.app.exampleassetcollection.settings.maxNumberOfUsers
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.state
import furhatos.flow.kotlin.users
import furhatos.flow.kotlin.voice.Voice

val Init = state {

    init {
        users.setSimpleEngagementPolicy(distanceToEngage, maxNumberOfUsers)
        furhat.voice = Voice("Matthew")

        /** From Asset Collection
         * Point to the google sheet used as resource for fetching utterances and logging responses
         * Link for the browser: https://docs.google.com/spreadsheets/d/${linkGoogleSheet}/
         **/
        UtilsLib.GoogleSheets.updateDefaultSheetLink(linkGoogleSheet)

        goto(Idle)
    }
}