package furhatos.app.assetcollectionexample.flow.utilsLibrary.googleSheets

import furhatos.app.assetcollectionexample.flow.utilsLibrary.UtilsLibParent
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.state

val googleSheetsExample = state(UtilsLibParent) {

    onEntry {
        furhat.say("Here you can test and understand how google sheets can be used to externalize the sentences I say.")
        furhat.say("There are two different implementations possible, a classic and a localized one.")
    }

    onButton("Explore Google Sheet supported azure voice tags") {
        goto(exploreTags)
    }

    onButton("Explore Google Sheet supported azure gesture tags") {
        goto(exploreTagGestures)
    }
}