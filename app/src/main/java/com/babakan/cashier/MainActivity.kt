package com.babakan.cashier

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.babakan.cashier.presentation.navigation.screen.main.MainScreen
import com.babakan.cashier.utils.theme.CashierTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CashierTheme {
                MainScreen()
            }
        }
    }
}