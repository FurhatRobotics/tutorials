package furhatos.app.extts.flow

import furhatos.flow.kotlin.*
import java.net.URLEncoder

/**
 * Specify the address of the external tts server here
 *
 * Use localhost when the server is running on the same device as the skill is.
 *
 * Otherwise, use a local ip like "192.168.1.25"
 *
 * The default port of the external tts server is 5000
 */
val server = "localhost:5000"

/**
 * Generates a url with the specified text, to query the server and uses it to make the robot speak.
 */
fun Furhat.exsay(text:String, engine:String = "", voice:String = "") {
    val encodedText = "text=${URLEncoder.encode(text, "utf-8")}" //Spaces will be turned into + etc etc
    var url = "http://$server/say?"
    if (engine != "") url += "engine=${engine}&"
    if (voice != "") url += "voice=${voice}&"
    url += "${encodedText}.wav"
    say {+Audio(url,text)}
}

val Start : State = state {
    onEntry {

        // below we try to synthesize speech using two different engines
        // on the external simple-talk server
        // if an engine is unavailable, another one will be used.
        // check the printouts from the simple-talk server to see which one is used.
        // if the server doesn't respond, default TTS of the say() method is used

        // first a few festival/festvox voices from the 'mimic' engine
        furhat.exsay("hello, good to see you, how are you doing today",engine="mimic")
        furhat.exsay("greetings fine folks, what lovely scottish weather we have today",voice="awb",engine="mimic")
        furhat.exsay("let me see now, who else do we have with us?",voice="slt",engine="mimic")

        // now let's try some dectalk voices
        // Note! The dectalk TTS engine is only available on windows (and Unix using wine)
        furhat.exsay("We are just an advanced breed of monkeys on a minor planet of a very average star.",engine="dectalk")
        furhat.exsay("Oh speak for yourself i am no monkey",engine="dectalk",voice="kit")
        furhat.exsay("Excuse me, now I think everyone needs to calm down",engine="dectalk",voice="rita")
        // DECTALK accepts in-line modification of speaking rate [:ra ..],
        // average pitch [:dv ap ..] and many other parameters *)
        furhat.exsay("[:ra 70] i am super calm",engine="dectalk",voice="dennis")
        furhat.exsay("[:dv ap 30] and my voice can go really low [:dv ap 350] and also ridiculously high",engine="dectalk",voice="dennis")

        // *) see DECTALK manual for full list of voices
        //    and parameters: https://vt100.net/dec/ek-dtc03-om-001.pdf

        furhat.listen() //Listen to the user
     }

    onResponse { //Repeats whatever the user said
        furhat.exsay(it.text)
        furhat.listen() //Listen to the user
    }
}
