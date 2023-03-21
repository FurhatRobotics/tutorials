package furhatos.demo.audiofeed

import org.zeromq.SocketType
import org.zeromq.ZMQ
import javax.sound.sampled.AudioFormat
import kotlin.concurrent.thread

class FurhatAudioFeedStreamer {

    private val context: ZMQ.Context = ZMQ.context(1)
    var running = false
        private set
    private var runThread: Thread? = null
    val audioFormat = AudioFormat(16000F, 16, 2, true, false)
    private val audioListeners = mutableListOf<AudioStreamingListener>()
    private var ipaddr = ""

    interface AudioStreamingListener {

        fun audioStreamingStopped()
        fun audioStreamingData(data: ByteArray)
    }

    fun addListener(audioListener: AudioStreamingListener) {
        audioListeners += audioListener
    }

    fun start(ipaddr: String) {
        if (ipaddr != this.ipaddr) {
            stop()
            this.ipaddr = ipaddr
        } else if (running) {
            return
        }
        val socket = context.socket(SocketType.SUB).apply {
            receiveTimeOut = 1000
            subscribe(byteArrayOf())
            connect("tcp://$ipaddr:3001")
        }
        running = true
        runThread = thread(start = true) {
            while (running) {
                val data = socket!!.recv()
                if (data != null && data.isNotEmpty()) {
                    audioListeners.forEach { it.audioStreamingData(data) }
                }
            }
        }
    }

    fun stop() {
        if (running) {
            running = false
            runThread?.join()
            runThread = null
            audioListeners.forEach { it.audioStreamingStopped() }
        }
    }
}
