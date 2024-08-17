package com.hav.iot

import android.os.Build
import android.os.Bundle
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
import com.hav.iot.ui.screen.MainScreen
import com.hav.iot.ui.theme.IOTTheme
import com.hav.iot.ui.theme.MainBG

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val color = MainBG
            WindowCompat.getInsetsController(window, window.decorView).apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    window.statusBarColor = color.toArgb()
                    window.navigationBarColor = color.toArgb()
                    isAppearanceLightStatusBars = true
                    isAppearanceLightNavigationBars = false
                }
            }

            IOTTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    IOTTheme {
        Greeting("Android")
    }
}