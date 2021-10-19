package furhatos.app.objectidentifer


import org.zeromq.ZMQ

val context: ZMQ.Context = ZMQ.context(1)

fun getConnectedSocket(socketType: Int, port: String, receiveTimeout: Int = -1): ZMQ.Socket {
    val socket = context.socket(socketType)
    if (receiveTimeout >= 0) socket.receiveTimeOut = receiveTimeout
    socket.subscribe("")
    socket.connect(port)
    return socket
}

