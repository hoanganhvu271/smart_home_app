package com.hav.iot

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.hav.iot.socket.SocketManager
import com.hav.iot.ui.screen.MainScreen
import com.hav.iot.ui.theme.IOTTheme
import com.hav.iot.ui.theme.MainBG
import com.hav.iot.viewmodel.HomeViewmodel

class MainActivity : ComponentActivity() {
    private val socketManager = SocketManager()
    private val homeViewModel = HomeViewmodel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {


            IOTTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(homeViewModel)
                }
            }
        }

        socketManager.connect()
        socketManager.sendMessage("Hello from Android")
        if(socketManager.isConnected()){
            Log.d("socket", "Connected")
        }
        else{
            Log.d("socket", "Not connected")
        }
        socketManager.onMessageReceived {
            Log.d("socket", "Message received: $it")
            runOnUiThread {
                // Update the UI
                homeViewModel.updateMessage(it)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        socketManager.disconnect()
    }
}

