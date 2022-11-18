package furhatos.app.assetcollectionexample

import furhatos.flow.kotlin.voice.PollyVoice
import furhatos.flow.kotlin.voice.Voice
import furhatos.util.Language

// Engagement params
const val maxNumberOfUsers = 5
const val distanceToEngage = 1.0


// Voice Params
val englishVoice = if (Voice("JennyNeural").isAvailable) Voice("JennyNeural") else PollyVoice.Matthew() //TODO : Better once Azure is in
val swedishVoice = Voice(language = Language.SWEDISH)
val frenchVoice = Voice(language = Language.FRENCH)


// Sheet Params
/** The sheets used for this example can be seen in the following drive :
 * https://drive.google.com/drive/folders/1khxYGeDYf5JoeeVqwNs5WialVwSLF85O
 */
//Regular integration
const val linkGoogleSheet = "1vpsoIQwRnZMYoCsSsZPg7dQuv1eSyb1BajJGb48JrNw"
const val textsPrimarySheet = "0"
const val intentsPrimarySheet = "1405898377"
const val otherIntentsPrimarySheet = "695770116"
const val buttonsPrimarySheet = "1639603394"

const val otherSheet = "1uviPraGQpIEwoVlRNLxnhty0KEMDqgNbvXR129rMOLw"
const val textsSecondarySheet = "0"
const val intentsSecondarySheet = "1099489951"
const val buttonsSecondarySheet = "163007925"


//Localized integration
const val localizedSheet = "1tKubpakbUiQZqm87NTlAhfLRlUmaZQnb3Az24ihzlRQ"
val sheetTabs = listOf(
    "1422713651",
    "1400318560",
    "2106884762",
    "1302651296",
    "unexisting"
)

