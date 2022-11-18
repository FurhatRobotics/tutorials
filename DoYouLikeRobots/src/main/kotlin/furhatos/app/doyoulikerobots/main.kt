package furhatos.app.doyoulikerobots

import furhatos.app.doyoulikerobots.flow.*
import furhatos.skills.Skill
import furhatos.flow.kotlin.*

class DoYouLikeRobotsSkill : Skill() {
    override fun start() {
        Flow().run(Init)
    }
}

fun main(args: Array<String>) {
    Skill.main(args)
}
