package com.babakan.cashier.presentation.navigation.screen.main

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.babakan.cashier.R
import com.babakan.cashier.presentation.authentication.screen.login.Login
import com.babakan.cashier.presentation.authentication.screen.register.Register
import com.babakan.cashier.presentation.authentication.viewmodel.AuthViewModel
import com.babakan.cashier.presentation.navigation.screen.navigation.MainNavigation
import com.babakan.cashier.presentation.navigation.viewmodel.MainViewModel
import com.babakan.cashier.utils.constant.RouteState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    authViewModel: AuthViewModel = viewModel(),
    mainViewModel: MainViewModel = viewModel(),
) {
    val context = LocalContext.current
    val authScope = rememberCoroutineScope()

    val userId by mainViewModel.userId.collectAsState()
    val isLoggedIn by mainViewModel.isLoggedIn.collectAsState()
    val isUserActive by mainViewModel.isUserActive.collectAsState()

    val snackBarHostState = remember { SnackbarHostState() }
    val navController = rememberNavController()

    var showInactiveUserDialog by remember { mutableStateOf(false) }

    val isInternetAvailable = remember {
        derivedStateOf {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            connectivityManager.activeNetwork?.let {
                connectivityManager.getNetworkCapabilities(it)
            }?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false
        }
    }

    if (!isInternetAvailable.value) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text(stringResource(R.string.placeholder)) },
            text = { Text(stringResource(R.string.placeholder)) },
            confirmButton = {
                Button(onClick = { android.os.Process.killProcess(android.os.Process.myPid()) }) {
                    Text(stringResource(R.string.placeholder))
                }
            }
        )
    }

    LaunchedEffect(isLoggedIn, isUserActive) {
        if (!isLoggedIn) {
            navController.navigate(RouteState.LOGIN.name) {
                popUpTo(RouteState.LOGIN.name) { inclusive = true }
            }
        } else if (isUserActive == false) {
            showInactiveUserDialog = true
        } else {
            if (userId != null) {
                mainViewModel.observeIsActiveField(userId!!)
                navController.navigate(RouteState.MAIN.name) {
                    popUpTo(RouteState.MAIN.name) { inclusive = true }
                }
            }
        }
    }

    if (showInactiveUserDialog) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text(stringResource(R.string.logout)) },
            text = { Text(stringResource(R.string.logoutConfirmation)) },
            confirmButton = {
                Button(onClick = {
                    authScope.launch {
                        authViewModel.signOut(
                            {
                                navController.navigate(RouteState.LOGIN.name) {
                                    popUpTo(navController.graph.startDestinationId) { inclusive = true }
                                }
                            }
                        )
                    }
                    showInactiveUserDialog = false
                }) {
                    Text(stringResource(R.string.logout))
                }
            }
        )
    }

    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) RouteState.MAIN.name else RouteState.LOGIN.name
    ) {
        composable(RouteState.LOGIN.name) {
            Login(
                authScope = authScope,
                snackBarHostState = snackBarHostState,
                onNavigateToRegister = { navController.navigate(RouteState.REGISTER.name) }
            )
        }
        composable(RouteState.REGISTER.name) {
            Register(
                authScope = authScope,
                snackBarHostState = snackBarHostState,
                onNavigateToLogin = { navController.navigate(RouteState.LOGIN.name) },
                onNavigateToMain = {
                    navController.navigate(RouteState.MAIN.name) {
                        popUpTo(RouteState.MAIN.name) { inclusive = true }
                    }
                }
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
