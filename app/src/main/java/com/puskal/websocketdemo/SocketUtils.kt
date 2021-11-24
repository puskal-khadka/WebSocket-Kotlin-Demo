package com.puskal.websocketdemo

import android.util.Log
import okhttp3.*
import okio.ByteString
import java.util.concurrent.TimeUnit

/**
 * created by Puskal Khadka
 */
object SocketUtils {
    val wsUrl="wss://demo.piesocket.com/v3/channel_1?api_key=oCdCMcMPQpbvNjUIzqtvF1d2X2okWpDQj4AwARJuAgtjhzKxVEjQU6IdCjwm&notify_self" //test socket url
    fun webSocketConnection(userEndUpdateListener:(String)->Unit): WebSocket {
        val okHttpClient = OkHttpClient.Builder().pingInterval(30, TimeUnit.SECONDS).build()

        val wsRequest = Request.Builder().url(wsUrl).build()

        val webSocket = okHttpClient.newWebSocket(wsRequest, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                Log.d("w","open ws connection")
                super.onOpen(webSocket, response)
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                Log.d("w","message received $text")
                userEndUpdateListener(text)
                super.onMessage(webSocket, text)
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                super.onMessage(webSocket, bytes)
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosed(webSocket, code, reason)
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                super.onFailure(webSocket, t, response)
            }
        })

        okHttpClient.dispatcher.executorService.shutdown()

        return webSocket

    }


}