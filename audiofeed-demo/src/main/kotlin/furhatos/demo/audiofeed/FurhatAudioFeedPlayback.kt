package furhatos.demo.audiofeed

import furhatos.demo.utils.removeLeftChannel
import furhatos.demo.utils.removeRightChannel
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.SourceDataLine

class FurhatAudioFeedPlayback(private val audioStreamer: FurhatAudioFeedStreamer): FurhatAudioFeedStreamer.AudioStreamingListener {

    private var sourceDataLine: SourceDataLine? = null

    private var playSystem: Boolean = false
    private var playUser: Boolean = false

    var running = false
        private set

    init {
        audioStreamer.addListener(this)
    }

    fun start(playSystem: Boolean, playUser: Boolean) {
        this.playSystem = playSystem
        this.playUser = playUser
        if (playSystem || playUser) {
            running = true
        }
    }

    private fun stop() {
        if (!running)
            return
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
                removeLeftChannel(audioData)
            } else if (!playSystem) {
                removeRightChannel(audioData)
            }
            sourceDataLine?.write(audioData, 0, audioData.size)
        }
    }

    override fun audioStreamingStopped() {
        sourceDataLine?.stop()
        sourceDataLine?.close()
        sourceDataLine = null
        stop()
    }
}