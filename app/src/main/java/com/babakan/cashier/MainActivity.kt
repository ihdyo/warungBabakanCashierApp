package com.babakan.cashier

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.babakan.cashier.presentation.authentication.screen.login.Login
import com.babakan.cashier.presentation.authentication.screen.register.Register
import com.babakan.cashier.presentation.authentication.viewmodel.AuthViewModel
import com.babakan.cashier.presentation.navigation.screen.main.MainNavigation
import com.babakan.cashier.utils.constant.RouteState
import com.babakan.cashier.utils.theme.CashierTheme

@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CashierTheme {
                val authScope = rememberCoroutineScope()
                val snackBarHostState = remember { SnackbarHostState() }
                val navController = rememberNavController()

                // Check if the user is already logged in
                val isLoggedIn = authViewModel.checkUserSession()

                // Check if internet is available
                val isInternetAvailable = isInternetConnected()

                if (!isInternetAvailable) {
                    // Show a dialog if there's no internet connection
                    ShowNoInternetDialog()
                } else {
                    // Proceed with the normal navigation flow if connected to the internet
                    Box(Modifier.fillMaxSize()) {
                        NavHost(
                            navController,
                            startDestination = if (isLoggedIn) RouteState.MAIN.name else RouteState.LOGIN.name
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
                                    onNavigateToLogin = { navController.navigate(RouteState.LOGIN.name) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // Function to check internet connection
    private fun isInternetConnected(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)

        return networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    // Function to show a dialog if there's no internet connection
    @Composable
    private fun ShowNoInternetDialog() {
        AlertDialog(
            onDismissRequest = {},
            title = { Text(text = "No Internet Connection") },
            text = { Text(text = "Please check your internet connection and try again.") },
            confirmButton = {
                TextButton(onClick = { finish() }) {
                    Text("Exit")
                }
            }
        )
    }
}
