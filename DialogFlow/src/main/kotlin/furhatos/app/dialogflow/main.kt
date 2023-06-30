package furhatos.app.dialogflow

import furhatos.app.dialogflow.flow.Idle
import furhatos.skills.Skill
import furhatos.flow.kotlin.*
import furhatos.skills.SingleUserEngagementPolicy
import furhatos.skills.UserManager

val dialogFlowAgent = DialogFlowAgent("PROJECT_ID", "PATH_TO_CREDENTIALS_FILE")

class DialogFlowSkill : Skill() {
    override fun start() {
        UserManager.engagementPolicy = SingleUserEngagementPolicy()
        Flow().run(Idle)
    }
}

fun main(args: Array<String>) {
    Skill.main(args)
}
