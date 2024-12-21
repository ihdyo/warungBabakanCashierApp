package com.babakan.cashier.common.audit.user.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.AlternateEmail
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.babakan.cashier.R
import com.babakan.cashier.utils.constant.SizeChart

@Composable
fun UserAuditForm(
    name: String,
    nameError: String?,
    onNameChange: (String) -> Unit,
    username: String,
    usernameError: String?,
    onUsernameChange: (String) -> Unit,
    email: String,
    emailError: String?,
    onEmailChange: (String) -> Unit,
    isActive: Boolean,
    onIsActiveChange: (Boolean) -> Unit
) {
    Column {
        OutlinedTextField(
            isError = nameError != null,
            value = name,
            onValueChange = { onNameChange(it) },
            label = { Text(stringResource(R.string.name)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            leadingIcon = { Icon(Icons.Outlined.Person, stringResource(R.string.name)) },
            supportingText = { nameError?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
            shape = MaterialTheme.shapes.medium,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
                errorLeadingIconColor = MaterialTheme.colorScheme.error
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            isError = emailError != null,
            value = email,
            onValueChange = { onEmailChange(it) },
            label = { Text(stringResource(R.string.email)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            leadingIcon = { Icon(Icons.Outlined.Email, stringResource(R.string.email)) },
            supportingText = { emailError?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
            shape = MaterialTheme.shapes.medium,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
                errorLeadingIconColor = MaterialTheme.colorScheme.error
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(SizeChart.DEFAULT_SPACE.dp)
        ) {
            OutlinedTextField(
                isError = usernameError != null,
                value = username,
                onValueChange = { onUsernameChange(it) },
                label = { Text(stringResource(R.string.username)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                leadingIcon = { Icon(Icons.Outlined.AlternateEmail, stringResource(R.string.username)) },
                supportingText = { usernameError?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
                shape = MaterialTheme.shapes.medium,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
                    errorLeadingIconColor = MaterialTheme.colorScheme.error
                ),
                modifier = Modifier.weight(1f)
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = SizeChart.DEFAULT_SPACE.dp)
            ) {
                Text(
                    stringResource(R.string.status),
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
                Switch(
                    checked = isActive,
                    onCheckedChange = { onIsActiveChange(it) },
                    thumbContent = if (isActive) {
                        {
                            Icon(
                                Icons.Default.Check,
                                stringResource(R.string.active),
                                tint = MaterialTheme.colorScheme.primaryContainer,
                                modifier = Modifier.size(SwitchDefaults.IconSize)
                            )
                        }
                    } else {
                        {
                            Icon(
                                Icons.Default.Close,
                                stringResource(R.string.inactive),
                                tint = MaterialTheme.colorScheme.errorContainer,
                                modifier = Modifier.size(SwitchDefaults.IconSize),
                            )
                        }
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        checkedTrackColor = MaterialTheme.colorScheme.primaryContainer,
                        checkedBorderColor = MaterialTheme.colorScheme.primaryContainer,
                        uncheckedThumbColor = MaterialTheme.colorScheme.onErrorContainer,
                        uncheckedTrackColor = MaterialTheme.colorScheme.errorContainer,
                        uncheckedBorderColor = MaterialTheme.colorScheme.errorContainer
                    )
                )
            }
        }
    }
}