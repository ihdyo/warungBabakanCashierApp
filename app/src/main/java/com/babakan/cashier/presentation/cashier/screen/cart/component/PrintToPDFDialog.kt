package com.babakan.cashier.presentation.cashier.screen.cart.component

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.babakan.cashier.R
import com.babakan.cashier.utils.constant.SizeChart
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun PrintToPDFDialog(
    context: Context,
    scope: CoroutineScope,
    snackBarHostState: SnackbarHostState,
    onDismiss: () -> Unit
) {
    AlertDialog(
        icon = {
            Icon(
                Icons.Default.ThumbUp,
                stringResource(R.string.confirm)
            )
        },
        title = {
            Text(
                stringResource(R.string.confirmSuccess),
                textAlign = TextAlign.Center
            )
        },
        text = {
            Text(
                stringResource(R.string.confirmMessage),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        },
        onDismissRequest = {},
        confirmButton = {
            Button(
                {
                    // TODO: Print PDF
                    onDismiss()
                    scope.launch { snackBarHostState.showSnackbar(context.getString(R.string.printSuccess)) }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(SizeChart.BETWEEN_TEXTS.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Print,
                        stringResource(R.string.print),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        stringResource(R.string.print),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        },
        dismissButton = {
            TextButton(
                { onDismiss() },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            ) { Text(stringResource(R.string.close)) }
        }
    )
}