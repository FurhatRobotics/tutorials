package gestures

import furhatos.gestures.BasicParams
import furhatos.gestures.defineGesture

/**
 * Shake head slowly
 */
val slowShake1 = defineGesture() {
    frame(0.1, 0.25){
        BasicParams.BLINK_LEFT to 1.0
        BasicParams.BLINK_RIGHT to 1.0

        BasicParams.NECK_TILT to 12.0
    }
    frame(0.25) { }
    frame(0.25, 0.7){
        BasicParams.BLINK_LEFT to 0.9
        BasicParams.BLINK_RIGHT to 0.9

        BasicParams.NECK_PAN to 10.0

        BasicParams.BROW_UP_LEFT to 2.0
        BasicParams.BROW_UP_RIGHT to 2.0
    }
    frame(1.0, 1.25){
        BasicParams.BLINK_LEFT to 0.8
        BasicParams.BLINK_RIGHT to 0.8

        BasicParams.NECK_PAN to -10.0
    }
    frame(1.75, 2.0){
        BasicParams.BLINK_LEFT to 0.8
        BasicParams.BLINK_RIGHT to 0.8

        BasicParams.BROW_UP_LEFT to 2.0
        BasicParams.BROW_UP_RIGHT to 2.0

        BasicParams.SMILE_OPEN to 0.2

        BasicParams.NECK_PAN to 10.0
    }
    frame(2.6, 2.8){
        BasicParams.BLINK_LEFT to 0.9
        BasicParams.BLINK_RIGHT to 0.9

        BasicParams.NECK_PAN to -12.0
    }
    frame(3.3, 4.0){
        BasicParams.BLINK_LEFT to 0.1
        BasicParams.BLINK_RIGHT to 0.1

        BasicParams.BROW_UP_LEFT to 0.0
        BasicParams.BROW_UP_RIGHT to 0.0

        BasicParams.NECK_PAN to 0.0
        BasicParams.NECK_TILT to 10.0
    }
    frame(4.0, 4.2){
        BasicParams.BLINK_LEFT to 1.0
        BasicParams.BLINK_RIGHT to 1.0
    }
    frame(4.2, 4.4){
        BasicParams.BLINK_LEFT to 0.0
        BasicParams.BLINK_RIGHT to 0.0
    }
    frame(4.4, 4.7){
        BasicParams.BLINK_LEFT to 1.0
        BasicParams.BLINK_RIGHT to 1.0
    }
    frame(4.7, 5.0){
        BasicParams.BLINK_LEFT to 0.0
        BasicParams.BLINK_RIGHT to 0.0
    }
    frame(5.2){
        BasicParams.BROW_DOWN_RIGHT to 1.0
        BasicParams.BROW_DOWN_LEFT to 1.0
        BasicParams.SMILE_OPEN to 0.2
    }
    reset(5.5)
}