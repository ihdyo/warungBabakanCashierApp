package com.babakan.cashier.presentation.navigation.screen.navigation.component.drawer.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.babakan.cashier.R
import com.babakan.cashier.common.ui.FullscreenLoading
import com.babakan.cashier.data.state.UiState
import com.babakan.cashier.presentation.authentication.viewmodel.AuthViewModel

@Composable
fun LogoutDialog(
    authViewModel: AuthViewModel,
    setDialogState: (Boolean) -> Unit,
    onDrawerStateChange: (DrawerValue) -> Unit,
) {
    val signOutState by authViewModel.signOutState.collectAsState()

    if (signOutState is UiState.Loading) FullscreenLoading()

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
        onDismissRequest = { setDialogState(false) },
        confirmButton = {
            Button(
                {
                    if (signOutState !is UiState.Loading) {
                        setDialogState(false)
                        onDrawerStateChange(DrawerValue.Closed)
                        authViewModel.signOutUser()
                    }
                },
                enabled = signOutState !is UiState.Loading,
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
                { setDialogState(false) },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            ) { Text(stringResource(R.string.cancel),) }
        }
    )
}