package furhatos.app.multilanguage

import furhatos.app.multilanguage.flow.Init
import furhatos.flow.kotlin.Flow
import furhatos.skills.Skill

class MultilanguageSkill : Skill() {
    override fun start() {
        Flow().run(Init)
    }
}

fun main(args: Array<String>) {
    Skill.main(args)
}
