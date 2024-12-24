package com.babakan.cashier.presentation.cashier.screen.cart.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.babakan.cashier.R
import com.babakan.cashier.common.component.PrintButtonComponent

@Composable
fun PrintInvoiceDialog(
    onConfirm: () -> Unit,
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
            PrintButtonComponent { onConfirm() }
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