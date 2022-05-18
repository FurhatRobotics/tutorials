package furhatos.app.exampleassetcollection

import furhatos.flow.kotlin.Flow
import furhatos.skills.Skill

class ExampleassetcollectionSkill : Skill() {
    override fun start() {
        Flow().run(Init)
    }
}

fun main(args: Array<String>) {
    Skill.main(args)
}
