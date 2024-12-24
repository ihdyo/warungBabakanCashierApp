package com.babakan.cashier.presentation.navigation.screen.main

import android.os.Process
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat.getString
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.babakan.cashier.R
import com.babakan.cashier.common.ui.OneWayDialog
import com.babakan.cashier.data.state.UiState
import com.babakan.cashier.presentation.authentication.screen.login.Login
import com.babakan.cashier.presentation.authentication.screen.register.Register
import com.babakan.cashier.presentation.authentication.viewmodel.AuthViewModel
import com.babakan.cashier.presentation.navigation.screen.navigation.MainNavigation
import com.babakan.cashier.utils.animation.Duration
import com.babakan.cashier.utils.animation.fadeInAnimation
import com.babakan.cashier.utils.animation.fadeOutAnimation
import com.babakan.cashier.utils.constant.RouteState
import com.babakan.cashier.utils.helper.isNetworkAvailable
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    authViewModel: AuthViewModel,
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val isLoading = remember { mutableStateOf(true) }
    val isUserActive by authViewModel.isUserActive.collectAsState()
    val loginState by authViewModel.loginState.collectAsState()
    val registerState by authViewModel.registerState.collectAsState()
    val isUserSignedIn by authViewModel.isUserSignedIn.collectAsState()
    val signOutState by authViewModel.signOutState.collectAsState()

    val snackBarHostState = remember { SnackbarHostState() }
    val navController = rememberNavController()

    var showInactiveUserDialog by remember { mutableStateOf(false) }
    var showRegisteredDialog by remember { mutableStateOf(false) }
    val isInternetAvailable = remember { derivedStateOf { isNetworkAvailable(context) } }

    val toSingleMain = {
        navController.navigate(RouteState.MAIN.name) {
            popUpTo(RouteState.MAIN.name) { inclusive = true }
        }
    }
    val toSingleLogin = {
        navController.navigate(RouteState.LOGIN.name) {
            popUpTo(RouteState.LOGIN.name) { inclusive = true }
            launchSingleTop = true
        }
    }

    LaunchedEffect(Unit) {
        authViewModel.checkIsUserSignedIn()
    }

    LaunchedEffect(isUserActive) {
        if (isUserActive == false) {
            showInactiveUserDialog = true
        }
    }

    LaunchedEffect(isUserSignedIn) {
        if (isUserSignedIn) {
            toSingleMain()
        } else {
            toSingleLogin()
        }
        isLoading.value = false
    }

    LaunchedEffect(registerState) {
        when (registerState) {
            is UiState.Success -> {
                showRegisteredDialog = true
            }
            is UiState.Error -> {
                val error = (registerState as UiState.Error)
                snackBarHostState.showSnackbar(error.message)
            }
            else -> {}
        }
    }

    LaunchedEffect(loginState) {
        when (loginState) {
            is UiState.Success -> {
                val success = (loginState as UiState.Success)
                snackBarHostState.showSnackbar(success.data.second)
            }
            is UiState.Error -> {
                val error = (loginState as UiState.Error)
                snackBarHostState.showSnackbar(error.message)
            }
            else -> {}
        }
    }

    LaunchedEffect(signOutState) {
        when (signOutState) {
            is UiState.Success -> {
                if (!showRegisteredDialog && !showInactiveUserDialog) {
                    snackBarHostState.showSnackbar(
                        getString(context, R.string.logoutSuccess)
                    )
                }
            }
            is UiState.Error -> {
                snackBarHostState.showSnackbar(
                    getString(context, R.string.logoutFailed)
                )
            }
            else -> {}
        }
    }

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
    if (showInactiveUserDialog) {
        OneWayDialog(
            icon = Icons.Default.Block,
            title = stringResource(R.string.inactive),
            body = stringResource(R.string.inactiveMessage),
            confirmText = stringResource(R.string.exit)
        ) {
            scope.launch { authViewModel.signOutUser() }
            showInactiveUserDialog = false
        }
    }
    if (showRegisteredDialog) {
        OneWayDialog(
            icon = Icons.Default.Check,
            title = stringResource(R.string.registered),
            body = stringResource(R.string.registeredMessage),
            confirmText = stringResource(R.string.ok)
        ) {
            navController.navigate(RouteState.LOGIN.name)
            showRegisteredDialog = false
        }
    }

    Surface {
        NavHost(
            navController = navController,
            startDestination = RouteState.LOGIN.name,
            enterTransition = { fadeInAnimation(Duration.ANIMATION_LONG) },
            exitTransition = { fadeOutAnimation(Duration.ANIMATION_LONG) },
        ) {
            composable(RouteState.LOGIN.name) {
                Login(
                    authViewModel = authViewModel,
                    authScope = scope,
                    snackBarHostState = snackBarHostState,
                    onNavigateToRegister = { navController.navigate(RouteState.REGISTER.name) }
                )
            }
            composable(RouteState.REGISTER.name) {
                Register(
                    authViewModel = authViewModel,
                    authScope = scope,
                    snackBarHostState = snackBarHostState,
                    onNavigateToLogin = { navController.navigate(RouteState.LOGIN.name) }
                )
            }
            composable(RouteState.MAIN.name) {
                MainNavigation(
                    authViewModel = authViewModel,
                    snackBarHostState = snackBarHostState
                )
            }
        }
    }
}