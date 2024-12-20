package com.babakan.cashier.presentation.navigation.screen.navigation.component.drawer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Store
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getString
import androidx.lifecycle.viewmodel.compose.viewModel
import com.babakan.cashier.R
import com.babakan.cashier.presentation.authentication.viewmodel.AuthViewModel
import com.babakan.cashier.utils.constant.SizeChart
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun NavigationDrawer(
    authViewModel: AuthViewModel = viewModel(),
    authScope: CoroutineScope,
    mainScope: CoroutineScope,
    snackBarHostState: SnackbarHostState,
    onDrawerStateChange: (DrawerValue) -> Unit,
    onNavigateToLogin: () -> Unit
) {
    val context = LocalContext.current
    var dialogState by remember { mutableStateOf(false) }

    // TODO Change this
    val name = "Kadek Michella"
    val isOwner = true

    ModalDrawerSheet(Modifier.width(SizeChart.DRAWER_WIDTH.dp)) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxHeight()
                .padding(SizeChart.DEFAULT_SPACE.dp)
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(SizeChart.DEFAULT_SPACE.dp),
                ) {
                    Card(colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer)) {
                        Icon(
                            if (isOwner) Icons.Default.Verified else Icons.Default.Store,
                            stringResource(R.string.cashier),
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(SizeChart.SMALL_SPACE.dp)
                        )
                    }
                    Column {
                        Text(
                            stringResource(R.string.greeting),
                            style = MaterialTheme.typography.titleMedium,
                        )
                        Text(
                            name,
                            style = MaterialTheme.typography.titleLarge,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                HorizontalDivider(Modifier.padding(vertical = SizeChart.DEFAULT_SPACE.dp))
                Spacer(Modifier.height(SizeChart.BETWEEN_SECTIONS.dp))
                Text(
                    stringResource(R.string.brandName),
                    Modifier.align(Alignment.CenterHorizontally)
                )
            }
            TextButton(
                { dialogState = !dialogState },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.error
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    stringResource(R.string.logout),
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }
    }

    if (dialogState) {
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
            onDismissRequest = { dialogState = !dialogState },
            confirmButton = {
                Button(
                    {
                        authViewModel.signOut(
                            onSuccess = {
                                onNavigateToLogin()
                                authScope.launch {
                                    snackBarHostState.showSnackbar(getString(context, R.string.logoutSuccess))
                                }
                            },
                            onError = { errorMessage ->
                                mainScope.launch {
                                    snackBarHostState.showSnackbar(getString(context, R.string.logoutFailed))
                                }
                            }
                        )

                        dialogState = !dialogState
                        onDrawerStateChange(DrawerValue.Closed)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text(stringResource(R.string.logout))
                }
            },
            dismissButton = {
                TextButton(
                    { dialogState = !dialogState },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                ) { Text(stringResource(R.string.cancel),) }
            }
        )
    }
}