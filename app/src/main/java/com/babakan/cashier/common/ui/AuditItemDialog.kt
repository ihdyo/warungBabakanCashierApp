package com.babakan.cashier.common.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Update
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.babakan.cashier.R

@Composable
fun AuditItemDialog(
    title: String,
    body: String,
    isDelete: Boolean = false,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        icon = {
            Icon(
                if (isDelete) Icons.Default.Delete else Icons.Default.Update,
                title,
                tint = if (isDelete) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.secondary
            )
        },
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
                    containerColor = if (isDelete) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = if (isDelete) MaterialTheme.colorScheme.onErrorContainer else MaterialTheme.colorScheme.onSecondaryContainer
                )
            ) {
                Text(if (isDelete) stringResource(R.string.delete) else stringResource(R.string.update))
            }
        },
        dismissButton = {
            TextButton(
                { onDismiss() },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            ) { Text(stringResource(R.string.cancel),) }
        }
    )
}