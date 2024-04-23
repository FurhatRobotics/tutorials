package furhatos.app.characterparams.flow

import furhatos.app.characterparams.gestures.SetARKitParam
import furhatos.app.characterparams.gestures.SetCharParam
import furhatos.nlu.common.*
import furhatos.flow.kotlin.*
import furhatos.flow.kotlin.Color
import furhatos.gestures.ARKitParams
import furhatos.gestures.CharParams
import furhatos.gestures.defineGesture
import java.math.BigDecimal
import java.math.RoundingMode

val charParamValues = DoubleArray(200) { 0.0 }

val Start: State = state {

    onEntry {
    }
    onButton("CharParams", color = Color.Green) {
        goto(CharacterParamPlay)
    }
    onButton("ARKitParams", color = Color.Green) {
        goto(ARKitParamPlay)
    }
    onButton("Examples", color = Color.Green) {
        goto(Examples)
    }
}

val CharacterParamPlay: State = state {
    for (param in CharParams.values()) {
        onButton("-" + param.name, color = Color.Blue) {
            charParamValues[param.ordinal] =
                BigDecimal(charParamValues[param.ordinal] - 0.2).setScale(2, RoundingMode.HALF_EVEN).toDouble()
            if (charParamValues[param.ordinal] < param.min)
                charParamValues[param.ordinal] = param.min

            furhat.gesture(SetCharParam(param, charParamValues[param.ordinal]))
            println(param.name + " to " + charParamValues[param.ordinal])
        }
        onButton(charParamValues[param.ordinal].toString() + " +", color = Color.Green) {
            charParamValues[param.ordinal] =
                BigDecimal(charParamValues[param.ordinal] + 0.2).setScale(2, RoundingMode.HALF_EVEN).toDouble()
            if (charParamValues[param.ordinal] > param.max)
                charParamValues[param.ordinal] = param.max

            furhat.gesture(SetCharParam(param, charParamValues[param.ordinal]))
            println(param.name + " to " + charParamValues[param.ordinal])
        }
        onButton("Reset", color = Color.Red) {
            charParamValues[param.ordinal] = 0.0
            furhat.gesture(SetCharParam(param, charParamValues[param.ordinal]))
            println(param.name + " to " + charParamValues[param.ordinal])
        }
    }
    onButton("See values", color = Color.Green) {
        reentry()
    }
    onButton("Print values", color = Color.Green) {
        println("val Character = defineGesture(\"Character\") {")
        println("  frame(0.35, persist = true){")
        for (param in CharParams.values()) {
            println("    "+param.name+" to "+charParamValues[param.ordinal])
        }
        println("  }")
        println("}")
    }
    onButton("Reset All", color = Color.Red) {
        for (param in CharParams.values()) {
            furhat.gesture(SetCharParam(param, 0.0))
            charParamValues[param.ordinal] = 0.0
        }
        reentry()
    }
    onButton("Goto Start", color = Color.Green) {
        goto(Start)
    }
}

val ARKitParamPlay: State = state {
    for (param in ARKitParams.values()) {
        onButton("-" + param.name, color = Color.Blue) {
            furhat.gesture(SetARKitParam(param, param.min))
            println(param.name + " to " + param.min)
        }
        onButton(" +", color = Color.Green) {
            furhat.gesture(SetARKitParam(param, param.max))
            println(param.name + " to " + param.max)
        }
        onButton("Reset", color = Color.Red) {
            furhat.gesture(SetARKitParam(param, 0.0))
            println(param.name + " to " + 0.0)
        }
    }
    onButton("Goto Start", color = Color.Green) {
        goto(Start)
    }
}
