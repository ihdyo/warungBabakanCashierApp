package com.babakan.cashier.presentation.authentication.screen.login.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.babakan.cashier.R
import com.babakan.cashier.utils.constant.SizeChart

@Composable
fun LoginForm(
    email: String,
    password: String,
    emailError: String?,
    passwordError: String?,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit
) {

    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.spacedBy(SizeChart.BETWEEN_ITEMS.dp)
    ) {
        OutlinedTextField(
            isError = emailError != null,
            value = email,
            onValueChange = { onEmailChange(it) },
            label = { Text(stringResource(R.string.email)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
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
        OutlinedTextField(
            isError = passwordError != null,
            value = password,
            onValueChange = { onPasswordChange(it) },
            label = { Text(stringResource(R.string.password)) },
            singleLine = true,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation('●'),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            leadingIcon = { Icon(Icons.Outlined.Lock, stringResource(R.string.password)) },
            trailingIcon = {
                IconButton({ passwordVisible = !passwordVisible }) {
                    Icon(
                        if (passwordVisible) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                        stringResource(R.string.password)
                    )
                }
            },
            supportingText = { passwordError?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
            shape = MaterialTheme.shapes.medium,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
                errorLeadingIconColor = MaterialTheme.colorScheme.error
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}