package furhatos.app.doyoulikerobots.setting

import furhatos.flow.kotlin.UserDataDelegate
import furhatos.records.User

var User.likeRobots : Boolean? by UserDataDelegate()