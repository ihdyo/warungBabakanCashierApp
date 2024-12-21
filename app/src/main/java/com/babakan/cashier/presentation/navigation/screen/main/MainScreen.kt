package com.babakan.cashier.presentation.navigation.screen.main

import android.os.Process
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.babakan.cashier.R
import com.babakan.cashier.common.ui.OneWayDialog
import com.babakan.cashier.presentation.authentication.screen.login.Login
import com.babakan.cashier.presentation.authentication.screen.register.Register
import com.babakan.cashier.presentation.authentication.viewmodel.AuthViewModel
import com.babakan.cashier.presentation.navigation.screen.navigation.MainNavigation
import com.babakan.cashier.presentation.navigation.viewmodel.MainViewModel
import com.babakan.cashier.utils.animation.Duration
import com.babakan.cashier.utils.animation.fadeInAnimation
import com.babakan.cashier.utils.animation.fadeOutAnimation
import com.babakan.cashier.utils.constant.RouteState
import com.babakan.cashier.utils.helper.isNetworkAvailable
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    authViewModel: AuthViewModel = viewModel(),
    mainViewModel: MainViewModel = viewModel(),
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val isLoggedIn by mainViewModel.isLoggedIn.collectAsState()
    val isUserActive by mainViewModel.isUserActive.collectAsState()
    val isLoading by mainViewModel.isLoading.collectAsState()

    val snackBarHostState = remember { SnackbarHostState() }
    val navController = rememberNavController()

    var showInactiveUserDialog by remember { mutableStateOf(false) }
    val isInternetAvailable = remember { derivedStateOf { isNetworkAvailable(context) } }

    if (!isInternetAvailable.value) {
        OneWayDialog(
            icon = Icons.Default.WifiOff,
            title = stringResource(R.string.noConnection),
            body = stringResource(R.string.noConnectionMessage),
            confirmText = stringResource(R.string.exit)
        ) {
            Process.killProcess(Process.myPid())
        }
    }

    LaunchedEffect(isUserActive) {
        if (isUserActive == false) {
            showInactiveUserDialog = true
        }
    }

    if (showInactiveUserDialog) {
        OneWayDialog(
            icon = Icons.Default.Block,
            title = stringResource(R.string.inactive),
            body = stringResource(R.string.inactiveMessage),
            confirmText = stringResource(R.string.exit)
        ) {
            scope.launch {
                authViewModel.signOut(
                    onSuccess = {
                        navController.navigate(RouteState.LOGIN.name) {
                            popUpTo(RouteState.LOGIN.name) { inclusive = true }
                        }
                    },
                    onError = { Process.killProcess(Process.myPid()) }
                )
            }
            showInactiveUserDialog = false
        }
    }

    if (!isLoading) {
        NavHost(
            navController = navController,
            startDestination = if (isLoggedIn) RouteState.MAIN.name else RouteState.LOGIN.name,
            enterTransition = { fadeInAnimation(Duration.ANIMATION_LONG) },
            exitTransition = { fadeOutAnimation(Duration.ANIMATION_LONG) },
        ) {
            composable(RouteState.LOGIN.name) {
                Login(
                    authScope = scope,
                    snackBarHostState = snackBarHostState,
                    onNavigateToRegister = { navController.navigate(RouteState.REGISTER.name) }
                )
            }
            composable(RouteState.REGISTER.name) {
                Register(
                    authScope = scope,
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
                    authScope = scope,
                    snackBarHostState = snackBarHostState,
                    onNavigateToLogin = { navController.navigate(RouteState.LOGIN.name) }
                )
            }
        }
    }
}