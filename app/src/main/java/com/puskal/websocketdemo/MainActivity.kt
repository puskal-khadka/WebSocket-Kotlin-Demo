package com.puskal.websocketdemo

import android.app.ProgressDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.puskal.websocketdemo.databinding.ActivityMainBinding
import okhttp3.WebSocket

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var webSocket: WebSocket? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val progressBarDialog = ProgressDialog(this)

        with(binding) {
            btnConnect.setOnClickListener {
               progressBarDialog.apply {
                   setTitle("Connecting to WebSocket")
                   setMessage("Please wait .....")
                   show()
               }
                webSocket = SocketUtils.webSocketConnection {
                    runOnUiThread {
                        progressBarDialog.cancel()
                        initView(isConnected = true)
                        tvReplyMsg.text=it
                    }
                }

            }

            btnSendMsg.setOnClickListener {
                val msg = etSendMsg.text.toString()
                if (msg.trim().isNotEmpty()) {
                    webSocket?.send(msg)
                }


            }
        }
    }

    private fun initView(isConnected:Boolean=false) {
        with(binding) {
            if (isConnected) {
                btnSendMsg.isVisible = isConnected
                etSendMsg.isVisible = isConnected
                btnConnect.isVisible=!isConnected
                tvReplyMsg.isVisible = isConnected
                tv6.isVisible = isConnected
                tvStatus.text=if(isConnected)"Status: connected" else "Status: not connected "
            }
        }
    }
}