package furhatos.app.assetcollectionexample

import furhatos.flow.kotlin.Flow
import furhatos.skills.Skill

class AssetCollectionExampleSkill : Skill() {
    override fun start() {
        Flow().run(Init)
    }
}

fun main(args: Array<String>) {
    Skill.main(args)
}
