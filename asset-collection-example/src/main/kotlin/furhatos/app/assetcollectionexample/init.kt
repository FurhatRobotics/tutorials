package furhatos.app.assetcollectionexample

import furhatos.app.assetcollectionexample.flow.Menu
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.state
import furhatos.flow.kotlin.users
import furhatos.util.CommonUtils

val logger = CommonUtils.getLogger("AssetCollectionSkill")

val Init = state {

    init {
        users.setSimpleEngagementPolicy(distanceToEngage, maxNumberOfUsers)
        furhat.voice = englishVoice
        furhat.mask = "Adult"
        furhat.character = "Alex"

        if (users.count >= 1) {
            furhat.attend(users.random)
        }
        //TODO : include official link !
        logger.info("Official StandardLibraryCollection documentation available here : " +
                "https://furhat-files.s3.eu-west-1.amazonaws.com/standardlibrary-docs/1.2.0-review-SNAP/-standard-library-collection/furhat.libraries.standard.utils/-google-sheets-integration/index.html")
        furhat.say("Hello ! This skill is a demonstration of what functions are available in the Standard Library Collection and how they can be used.")
        furhat.say("There are five different libraries available. Please wizard your way through the skill to explore them.")

        goto(Menu)
    }
}