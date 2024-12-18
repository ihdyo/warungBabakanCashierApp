package com.babakan.cashier

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.babakan.cashier.presentation.authentication.screen.login.Login
import com.babakan.cashier.presentation.authentication.screen.register.Register
import com.babakan.cashier.presentation.navigation.screen.main.MainNavigation
import com.babakan.cashier.utils.constant.RouteState
import com.babakan.cashier.utils.theme.CashierTheme

@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CashierTheme {
                val authScope = rememberCoroutineScope()

                val snackBarHostState = remember { SnackbarHostState() }
                val navController = rememberNavController()

                Box(Modifier.fillMaxSize()) {
                    NavHost(
                        navController,
                        RouteState.LOGIN.name
                    ) {
                        composable(RouteState.LOGIN.name) {
                            Login(
                                scope = authScope,
                                snackBarHostState = snackBarHostState,
                                onNavigateToRegister = { navController.navigate(RouteState.REGISTER.name) },
                                onNavigateToMain = { navController.navigate(RouteState.MAIN.name) }
                            )
                        }
                        composable(RouteState.REGISTER.name) {
                            Register(
                                scope = authScope,
                                snackBarHostState = snackBarHostState,
                                onNavigateToLogin = { navController.navigate(RouteState.LOGIN.name) },
                                onNavigateToMain = { navController.navigate(RouteState.MAIN.name) }
                            )
                        }
                        composable(RouteState.MAIN.name) {
                            MainNavigation(
                                authScope = authScope,
                                snackBarHostState = snackBarHostState,
                                onNavigateToLogin = { navController.navigate(RouteState.LOGIN.name) },
                            )
                        }
                    }
                }
            }
        }
    }
}