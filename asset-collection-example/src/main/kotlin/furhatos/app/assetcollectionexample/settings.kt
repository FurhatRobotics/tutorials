package furhatos.app.assetcollectionexample

import furhatos.flow.kotlin.voice.Voice

// Engagement params
const val maxNumberOfUsers = 5
const val distanceToEngage = 1.0


// Sheet Params
const val linkGoogleSheet = "1vpsoIQwRnZMYoCsSsZPg7dQuv1eSyb1BajJGb48JrNw"
const val otherSheet = "1uviPraGQpIEwoVlRNLxnhty0KEMDqgNbvXR129rMOLw"
const val localizedSheet = "1tKubpakbUiQZqm87NTlAhfLRlUmaZQnb3Az24ihzlRQ"

val sheetTabs = listOf(
    "LocalizedStrings",
    "LocalizedIntents",
    "RegularStrings",
    "RegularIntents",
    "unexisting"
)


// Voice Params
val frenchVoice = Voice("Mathieu")
val englishVoice = Voice("Matthew")
val swedishVoice = Voice("Elin")