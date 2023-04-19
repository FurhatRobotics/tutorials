package furhatos.demo

import furhatos.demo.audiofeed.FurhatAudioFeedPlayback
import furhatos.demo.audiofeed.FurhatAudioFeedRecorder
import furhatos.demo.audiofeed.FurhatAudioFeedStreamer
import java.io.File

fun main() {
    val streamer = FurhatAudioFeedStreamer()
    streamer.start("192.168.1.50")

    /** Choose one of them to record or playback audio */
//    recordAudio(streamer)
    playbackAudio(streamer)

    println("Starting streaming, press return to stop.")
    readlnOrNull()
    streamer.stop()
}

fun recordAudio(streamer: FurhatAudioFeedStreamer) {
    val recorder = FurhatAudioFeedRecorder(streamer)

    /** Choose one of them to record audio in, out or both */
//    recorder.startRecordAll(File("recording.wav"))
    recorder.startRecordSeparate(audioInFile = File("audioIn.wav"), audioOutFile = File("audioOut.wav"))
}

fun playbackAudio(streamer: FurhatAudioFeedStreamer) {
    val playback = FurhatAudioFeedPlayback(streamer)
    playback.start(playSystem = true, playUser = true)
}