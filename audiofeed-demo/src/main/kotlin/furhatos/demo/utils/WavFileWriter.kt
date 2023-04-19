package furhatos.demo.utils

import java.io.*
import javax.sound.sampled.AudioFileFormat
import javax.sound.sampled.AudioFormat
import javax.sound.sampled.AudioInputStream
import javax.sound.sampled.AudioSystem

/**
 * A class for recording audio to a wav file
 */
class WavFileWriter {

    private var tmpFile: File? = null
    private var wavFile: File? = null
    private var audioStream: OutputStream? = null
    private var audioFormat: AudioFormat? = null

    @Synchronized
    fun open(file: File, audioFormat: AudioFormat) {
        this.tmpFile = File(file.absolutePath + ".tmp")
        this.wavFile = file
        this.audioFormat = audioFormat
        try {
            audioStream = FileOutputStream(tmpFile)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
    }

    @Synchronized
    fun writeAudio(buffer: ByteArray, pos: Int, len: Int) {
        audioStream?.apply {
            write(buffer, pos, len)
            flush()
        }
    }

    @Synchronized
    fun close() {
        audioStream?.apply {
            try {
                close()
                audioStream = null
                val fi = FileInputStream(tmpFile)
                val ai = AudioInputStream(fi, audioFormat, tmpFile!!.length() / 2)
                AudioSystem.write(ai, AudioFileFormat.Type.WAVE, wavFile)
                fi.close()
                tmpFile?.delete()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}


