package com.babakan.cashier

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import com.babakan.cashier.presentation.navigation.screen.main.MainNavigation
import com.babakan.cashier.utils.theme.CashierTheme

@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CashierTheme {
                MainNavigation()

//                val navController = rememberNavController()
//
//                Box(Modifier.fillMaxSize()) {
//                    NavHost(
//                        navController,
//                        Route.LOGIN.name
//                    ) {
//                        composable(Route.LOGIN.name) {
//                            Login(
//                                onNavigateToRegister = { navController.navigate(Route.REGISTER.name) },
//                                onNavigateToMain = { navController.navigate(Route.MAIN.name) }
//                            )
//                        }
//                        composable(Route.REGISTER.name) {
//                            Register(
//                                onNavigateToLogin = { navController.navigate(Route.LOGIN.name) },
//                                onNavigateToMain = { navController.navigate(Route.MAIN.name) }
//                            )
//                        }
//                        composable(Route.MAIN.name) {
//                            MainNavigation()
//                        }
//                    }
//                }
            }
        }
    }
}