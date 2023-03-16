package furhatos.monitor

import java.io.File

class FurhatAudioFeedRecorder(val audioStreamer: FurhatAudioFeedStreamer): FurhatAudioFeedStreamer.AudioStreamingListener {

    val audioRecorder = WavFileWriter()
    var running = false
        private set

    init {
        audioStreamer.addListener(this)
    }

    fun start(audioFile: File) {
        if (running)
            stop()
        running = true
        audioRecorder.open(audioFile, audioStreamer.audioFormat)
    }

    fun stop() {
        if (!running)
            return
        running = false
        audioRecorder.close()
    }

    override fun audioStreamingStarted() {
    }

    override fun audioStreamingData(data: ByteArray) {
        if (running) {
            audioRecorder.writeAudio(data, 0, data.size)
        }
    }

    override fun audioStreamingStopped() {
        stop()
    }

}

fun main() {
    val streamer = FurhatAudioFeedStreamer()
    val recorder = FurhatAudioFeedRecorder(streamer)
    streamer.start("127.0.0.1")
    recorder.start(File("recording.wav"))
    println("Starting recording, press return to stop.")
    readLine()
    streamer.stop()
}