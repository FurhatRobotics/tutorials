package furhatos.app.characterparams

import furhatos.app.characterparams.flow.*
import furhatos.skills.Skill
import furhatos.flow.kotlin.*

class CharacterparamsSkill : Skill() {
    override fun start() {
        Flow().run(Idle)
    }
}

fun main(args: Array<String>) {
    Skill.main(args)
}
