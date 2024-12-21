package com.babakan.cashier.common.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign

@Composable
fun OneWayDialog(
    icon: ImageVector,
    title: String,
    body: String,
    confirmText: String,
    onConfirm: () -> Unit,
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
        onDismissRequest = {},
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
        }
    )
}