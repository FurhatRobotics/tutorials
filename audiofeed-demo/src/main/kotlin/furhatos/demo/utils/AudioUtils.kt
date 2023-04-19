package furhatos.demo.utils

fun removeLeftChannel(audioData: ByteArray) {
    for (i in 0 until audioData.size / 4) {
        audioData[i*4] = audioData[i*4+2]
        audioData[i*4+1] = audioData[i*4+3]
    }
}

fun removeRightChannel(audioData: ByteArray) {
    for (i in 0 until audioData.size / 4) {
        audioData[i*4+2] = audioData[i*4]
        audioData[i*4+3] = audioData[i*4+1]
    }
}