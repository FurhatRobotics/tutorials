package furhatos.app.characterparams.gestures

import furhatos.gestures.ARKitParams
import furhatos.gestures.CharParams
import furhatos.gestures.CharParams.*
import furhatos.gestures.defineGesture

val BigEyes = defineGesture("BigEyes") {
    frame(0.35, 3.4){
        EYES_SCALE_UP to 1.0

    }
    reset(4.0)
}

fun SetCharParam(param: CharParams, value: Double) = defineGesture("SetCharParam") {
    frame(0.35, persist = true){
        param to value
    }
    //reset(4.0)
}
fun SetARKitParam(param: ARKitParams, value: Double) = defineGesture("SetARKitParam") {
    frame(0.3, 2.7){
        param to value
    }
    reset(3.0)
}

val CharacterReset = defineGesture("CharacterReset") {
    frame(0.35){
        CHEEK_BONES_DOWN to 0.0
        CHEEK_BONES_NARROWER to 0.0
        CHEEK_BONES_UP to 0.0
        CHEEK_BONES_WIDER to 0.0
        CHEEK_FULLER to 0.0
        CHEEK_THINNER to 0.0
        CHIN_DOWN to 0.0
        CHIN_NARROWER to 0.0
        CHIN_UP to 0.0
        CHIN_WIDER to 0.0
        EYEBROW_DOWN to 0.0
        EYEBROW_LARGER to 0.0
        EYEBROW_NARROWER to 0.0
        EYEBROW_SMALLER to 0.0
        EYEBROW_TILT_DOWN to 0.0
        EYEBROW_TILT_UP to 0.0
        EYEBROW_UP to 0.0
        EYEBROW_WIDER to 0.0
        EYES_DOWN to 0.0
        EYES_NARROWER to 0.0
        EYES_SCALE_DOWN to 0.0
        EYES_SCALE_UP to 0.0
        EYES_TILT_DOWN to 0.0
        EYES_TILT_UP to 0.0
        EYES_UP to 0.0
        EYES_WIDER to 0.0
        MOUTH_DOWN to 0.0
        MOUTH_FLATTER to 0.0
        MOUTH_NARROWER to 0.0
        MOUTH_SCALE to 0.0
        MOUTH_UP to 0.0
        MOUTH_WIDER to 0.0
        NOSE_DOWN to 0.0
        NOSE_NARROWER to 0.0
        NOSE_UP to 0.0
        NOSE_WIDER to 0.0
        LIP_BOTTOM_THICKER to 0.0
        LIP_BOTTOM_THINNER to 0.0
        LIP_TOP_THICKER to 0.0
        LIP_TOP_THINNER to 0.0
    }
}

val Character1 = defineGesture("Character1") {
    frame(0.35, persist = true){
        CHEEK_BONES_DOWN to 0.2
        CHEEK_BONES_NARROWER to 0.0
        CHEEK_BONES_UP to 1.5
        CHEEK_BONES_WIDER to 0.0
        CHEEK_FULLER to 0.0
        CHEEK_THINNER to 0.5
        CHIN_DOWN to 0.0
        CHIN_NARROWER to 1.5
        CHIN_UP to 0.0
        CHIN_WIDER to 0.0
        EYEBROW_DOWN to 1.5
        EYEBROW_LARGER to 1.5
        EYEBROW_NARROWER to -0.3
        EYEBROW_SMALLER to 0.0
        EYEBROW_TILT_DOWN to 0.8
        EYEBROW_TILT_UP to 0.0
        EYEBROW_UP to 0.0
        EYEBROW_WIDER to 0.0
        EYES_DOWN to 0.0
        EYES_NARROWER to 0.0
        EYES_SCALE_DOWN to 0.3
        EYES_SCALE_UP to 0.0
        EYES_TILT_DOWN to 0.0
        EYES_TILT_UP to 0.6
        EYES_UP to 0.0
        EYES_WIDER to 0.0
        MOUTH_DOWN to 0.2
        MOUTH_FLATTER to 0.6
        MOUTH_NARROWER to 0.0
        MOUTH_SCALE to 0.0
        MOUTH_UP to 0.4
        MOUTH_WIDER to 0.3
        NOSE_DOWN to 0.0
        NOSE_NARROWER to 0.0
        NOSE_UP to 0.0
        NOSE_WIDER to 0.2
        LIP_BOTTOM_THICKER to 0.4
        LIP_BOTTOM_THINNER to 0.0
        LIP_TOP_THICKER to 0.2
        LIP_TOP_THINNER to 0.0
    }
}

val Character2 = defineGesture("Character2") {
    frame(0.35, persist = true){
        CHEEK_BONES_DOWN to 0.8
        CHEEK_BONES_NARROWER to 0.0
        CHEEK_BONES_UP to -0.3
        CHEEK_BONES_WIDER to 0.0
        CHEEK_FULLER to -0.2
        CHEEK_THINNER to 0.0
        CHIN_DOWN to 0.6
        CHIN_NARROWER to 1.5
        CHIN_UP to 0.0
        CHIN_WIDER to -0.3
        EYEBROW_DOWN to 1.5
        EYEBROW_LARGER to 1.5
        EYEBROW_NARROWER to -0.3
        EYEBROW_SMALLER to 0.0
        EYEBROW_TILT_DOWN to 0.8
        EYEBROW_TILT_UP to 0.2
        EYEBROW_UP to 0.2
        EYEBROW_WIDER to 0.4
        EYES_DOWN to 0.2
        EYES_NARROWER to 0.0
        EYES_SCALE_DOWN to 0.3
        EYES_SCALE_UP to 0.4
        EYES_TILT_DOWN to 0.0
        EYES_TILT_UP to 0.6
        EYES_UP to 0.0
        EYES_WIDER to 0.4
        MOUTH_DOWN to 0.2
        MOUTH_FLATTER to -0.3
        MOUTH_NARROWER to 0.0
        MOUTH_SCALE to 0.2
        MOUTH_UP to 0.4
        MOUTH_WIDER to 0.5
        NOSE_DOWN to 0.2
        NOSE_NARROWER to 0.4
        NOSE_UP to 0.0
        NOSE_WIDER to 0.2
        LIP_BOTTOM_THICKER to 0.4
        LIP_BOTTOM_THINNER to 0.0
        LIP_TOP_THICKER to 0.4
        LIP_TOP_THINNER to 0.0
    }
}

val CharacterChildMask = defineGesture("CharacterChildMask") {
    frame(0.35, persist = true){
        CHEEK_BONES_DOWN to 1.4
        CHEEK_BONES_NARROWER to 0.0
        CHEEK_BONES_UP to 0.0
        CHEEK_BONES_WIDER to 0.0
        CHEEK_FULLER to 0.0
        CHEEK_THINNER to 0.0
        CHIN_DOWN to 0.0
        CHIN_NARROWER to 0.0
        CHIN_UP to 0.0
        CHIN_WIDER to 0.0
        EYEBROW_DOWN to 0.0
        EYEBROW_LARGER to 0.0
        EYEBROW_NARROWER to 0.0
        EYEBROW_SMALLER to 1.3
        EYEBROW_TILT_DOWN to 0.0
        EYEBROW_TILT_UP to 0.0
        EYEBROW_UP to 0.1
        EYEBROW_WIDER to 0.0
        EYES_DOWN to 1.0
        EYES_NARROWER to 0.0
        EYES_SCALE_DOWN to 0.0
        EYES_SCALE_UP to 1.5
        EYES_TILT_DOWN to 0.0
        EYES_TILT_UP to 0.0
        EYES_UP to 0.0
        EYES_WIDER to 0.0
        MOUTH_DOWN to 0.0
        MOUTH_FLATTER to -0.5
        MOUTH_NARROWER to 0.0
        MOUTH_SCALE to 0.3
        MOUTH_UP to 0.0
        MOUTH_WIDER to 1.5
        NOSE_DOWN to 0.1
        NOSE_NARROWER to 1.3
        NOSE_UP to 0.0
        NOSE_WIDER to 0.0
        LIP_BOTTOM_THICKER to 0.0
        LIP_BOTTOM_THINNER to 0.0
        LIP_TOP_THICKER to 0.0
        LIP_TOP_THINNER to 0.0
    }
}