package furhatos.app.extts

import furhatos.app.extts.flow.*
import furhatos.skills.Skill
import furhatos.flow.kotlin.*

class ExttsSkill : Skill() {
    override fun start() {
        Flow().run(Start)
    }
}

fun main(args: Array<String>) {
    Skill.main(args)
}
