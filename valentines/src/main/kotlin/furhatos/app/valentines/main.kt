package furhatos.app.valentines

import furhatos.app.valentines.flow.*
import furhatos.skills.Skill
import furhatos.flow.kotlin.*

class ValentinesSkill : Skill() {
    override fun start() {
        Flow().run(Idle)
    }
}

fun main(args: Array<String>) {
    Skill.main(args)
}
