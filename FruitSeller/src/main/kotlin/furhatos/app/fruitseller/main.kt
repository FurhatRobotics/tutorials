package furhatos.app.fruitseller

import furhatos.app.fruitseller.flow.*
import furhatos.skills.Skill
import furhatos.flow.kotlin.*

class FruitsellerSkill : Skill() {
    override fun start() {
        Flow().run(Init)
    }
}

fun main(args: Array<String>) {
    Skill.main(args)
}
