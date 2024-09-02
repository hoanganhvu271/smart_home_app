package com.hav.iot.socket

import android.util.Log
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException


class SocketManager {
    private var socket: Socket? = null

    init {
        try {
            val opts = IO.Options()
            opts.transports = arrayOf(Socket.EVENT_CONNECT)

            socket = IO.socket("https://iot-server-siz9.onrender.com")
            socket?.on(Socket.EVENT_CONNECT) {
                Log.d("SocketManager", "Connected to server")
            }
            socket?.on(Socket.EVENT_CONNECT_ERROR) { args ->
                val error = args[0] as? Exception
                Log.e("SocketManager", "Connection error: ${error?.message}")
            }
            socket?.on(Socket.EVENT_DISCONNECT) {
                Log.d("SocketManager", "Disconnected from server")
            }
        } catch (e: URISyntaxException) {
            Log.e("SocketManager", "URISyntaxException: ${e.message}")
        }
    }


    fun connect() {
        socket?.connect()
    }

    fun disconnect() {
        socket?.disconnect()
    }

    fun isConnected(): Boolean {
        return socket?.connected() ?: false
    }

    fun onMessageReceived(listener: (String) -> Unit) {
        socket?.on("message") { args ->
            val message = args[0] as String
            listener.invoke(message)
        }
    }

    fun sendMessage(message: String) {
        socket?.emit("message", message)
    }
}