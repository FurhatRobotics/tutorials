package furhatos.demo.audiofeed

import furhatos.demo.utils.WavFileWriter
import furhatos.demo.utils.removeLeftChannel
import furhatos.demo.utils.removeRightChannel
import java.io.File

class FurhatAudioFeedRecorder(private val audioStreamer: FurhatAudioFeedStreamer): FurhatAudioFeedStreamer.AudioStreamingListener {

    private val audioRecorder = WavFileWriter()

    private val audioInRecorder = WavFileWriter()
    private val audioOutRecorder = WavFileWriter()

    private var recordUser: Boolean = false
    private var recordSystem: Boolean = false

    var running = false
        private set

    init {
        audioStreamer.addListener(this)
    }

    fun startRecordAll(audioFile: File) {
        if (running)
            stop()
        running = true
        audioRecorder.open(audioFile, audioStreamer.audioFormat)
    }

    fun startRecordSeparate(audioInFile: File? = null, audioOutFile: File? = null) {
        if (audioInFile == null && audioOutFile == null) {
            println("No file specified, did not record.")
            return
        }
        if (running)
            stop()
        running = true
        audioInFile?.let {
            recordUser = true
            audioInRecorder.open(it, audioStreamer.audioFormat)
        }
        audioOutFile?.let {
            recordSystem = true
            audioOutRecorder.open(it, audioStreamer.audioFormat)
        }
    }

    private fun stop() {
        if (!running)
            return
        running = false
        recordUser = false
        recordSystem = false
        audioRecorder.close()
        audioInRecorder.close()
        audioOutRecorder.close()
    }

    override fun audioStreamingData(data: ByteArray) {
        if (running) {
            // Write both audio in and out in the same audio file if nothing is specified (function startRecordAll())
            if (!recordSystem && !recordUser) {
                audioRecorder.writeAudio(data, 0, data.size)
            }

            // Write system only (audio out) when asked (function startRecordSeparate())
            if (recordSystem) {
                val audioOutData = data.copyOf()
                removeLeftChannel(audioOutData)
                audioOutRecorder.writeAudio(audioOutData, 0, audioOutData.size)
            }
            // Write user only (audio in) when asked (function startRecordSeparate())
            if (recordUser) {
                val audioInData = data.copyOf()
                removeRightChannel(audioInData)
                audioInRecorder.writeAudio(audioInData, 0, audioInData.size)
            }
        }
    }

    override fun audioStreamingStopped() {
        stop()
    }
}