package com.babakan.cashier.common.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign

@Composable
fun CommonDialog(
    icon: ImageVector,
    title: String,
    body: String,
    onConfirm: () -> Unit,
    confirmText: String,
    onDismiss: () -> Unit,
    dismissText: String
) {
    AlertDialog(
        icon = { Icon(icon, title) },
        title = {
            Text(
                title,
                textAlign = TextAlign.Center
            )
        },
        text = {
            Text(
                body,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        },
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(
                { onConfirm() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                )
            ) {
                Text(confirmText)
            }
        },
        dismissButton = {
            TextButton(
                { onDismiss() },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            ) { Text(dismissText) }
        }
    )
}