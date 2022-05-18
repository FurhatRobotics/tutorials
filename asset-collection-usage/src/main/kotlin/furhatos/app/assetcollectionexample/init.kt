package furhatos.app.assetcollectionexample

import furhat.libraries.standard.UtilsLib
import furhatos.app.assetcollectionexample.flow.Idle
import furhatos.app.assetcollectionexample.settings.distanceToEngage
import furhatos.app.assetcollectionexample.settings.linkGoogleSheet
import furhatos.app.assetcollectionexample.settings.maxNumberOfUsers
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