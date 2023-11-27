package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.socket

import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException

object SocketManager {
    var socket: Socket?=null
    init {
        try {
            socket = IO.socket("http://10.0.2.2:5000")
        }catch (e:URISyntaxException){e.printStackTrace()}
    }
    fun connect(){ socket?.connect() }
    fun disconnect(){ socket?.disconnect() }
    fun sendMsj(msj:String){
        socket?.emit("message",msj)
    }
}