package com.example.terragardenapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.terragardenapp.ui.theme.TerraGardenAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            TerraGardenAppTheme {
                // A surface container using the 'background' color from the theme
                MainScreen()
            }
        }
    }
}



