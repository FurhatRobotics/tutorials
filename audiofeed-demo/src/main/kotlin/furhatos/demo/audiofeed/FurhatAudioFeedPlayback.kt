package furhatos.monitor

import javax.sound.sampled.AudioSystem
import javax.sound.sampled.SourceDataLine


class FurhatAudioFeedPlayback(val audioStreamer: FurhatAudioFeedStreamer): FurhatAudioFeedStreamer.AudioStreamingListener {

    var sourceDataLine: SourceDataLine? = null

    private var playSystem: Boolean = false
    private var playUser: Boolean = false

    var running = false
        private set

    init {
        audioStreamer.addListener(this)
    }

    override fun audioStreamingStarted() {
    }

    fun start(playSystem: Boolean, playUser: Boolean) {
        this.playSystem = playSystem
        this.playUser = playUser
        if (playSystem || playUser) {
            running = true
        }
    }

    fun stop() {
        running = false
    }

    override fun audioStreamingData(data: ByteArray) {
        if (running) {
            if (sourceDataLine == null) {
                sourceDataLine = AudioSystem.getSourceDataLine(audioStreamer.audioFormat).apply {
                    open(audioStreamer.audioFormat)
                    start()
                }
            }
            val audioData = data.copyOf()
            if (!playUser) {
                for (i in 0 until audioData.size / 4) {
                    audioData[i*4] = audioData[i*4+2]
                    audioData[i*4+1] = audioData[i*4+3]
                }
            } else if (!playSystem) {
                for (i in 0 until audioData.size / 4) {
                    audioData[i*4+2] = audioData[i*4]
                    audioData[i*4+3] = audioData[i*4+1]
                }
            }
            sourceDataLine?.write(audioData, 0, audioData.size)
        }
    }

    override fun audioStreamingStopped() {
        sourceDataLine?.stop()
        sourceDataLine?.close()
        sourceDataLine = null
        running = false
    }
}

fun main() {
    val streamer = FurhatAudioFeedStreamer()
    val playback = FurhatAudioFeedPlayback(streamer)
    playback.start(true, true)
    streamer.start("127.0.0.1")
    println("Starting playback, press return to stop.")
    readLine()
    streamer.stop()
}