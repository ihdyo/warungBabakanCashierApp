package com.babakan.cashier.presentation.navigation.screen.navigation.component.drawer.component

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.core.content.ContextCompat.getString
import com.babakan.cashier.R
import com.babakan.cashier.common.ui.FullscreenLoading
import com.babakan.cashier.data.state.UiState
import com.babakan.cashier.presentation.authentication.viewmodel.AuthViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun LogoutDialog(
    authViewModel: AuthViewModel,
    context: Context,
    authScope: CoroutineScope,
    mainScope: CoroutineScope,
    dialogState: Boolean,
    setDialogState: (Boolean) -> Unit,
    onDrawerStateChange: (DrawerValue) -> Unit,
    snackBarHostState: SnackbarHostState,
    onNavigateToLogin: () -> Unit,
) {

    val authState by authViewModel.authState.collectAsState()

    if (authState is UiState.Loading) FullscreenLoading()
    LaunchedEffect(authState) {
        when (authState) {
            is UiState.Error -> {
                mainScope.launch {
                    snackBarHostState.showSnackbar(getString(context, R.string.logoutFailed))
                }
            }
            is UiState.Success -> {
                authScope.launch {
                    snackBarHostState.showSnackbar(getString(context, R.string.logoutSuccess))
                }
                onNavigateToLogin()
            }
            else -> {}
        }
    }

    AlertDialog(
        icon = {
            Icon(
                Icons.AutoMirrored.Default.Logout,
                stringResource(R.string.logout),
                tint = MaterialTheme.colorScheme.error
            )
        },
        title = {
            Text(
                stringResource(R.string.logout),
                textAlign = TextAlign.Center
            )
        },
        text = {
            Text(
                stringResource(R.string.logoutConfirmation),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        },
        onDismissRequest = { setDialogState(!dialogState) },
        confirmButton = {
            Button(
                {
                    authViewModel.signOutUser()
                    setDialogState(!dialogState)
                    onDrawerStateChange(DrawerValue.Closed)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.onErrorContainer
                )
            ) {
                Text(stringResource(R.string.logout))
            }
        },
        dismissButton = {
            TextButton(
                { setDialogState(!dialogState) },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            ) { Text(stringResource(R.string.cancel),) }
        }
    )
}