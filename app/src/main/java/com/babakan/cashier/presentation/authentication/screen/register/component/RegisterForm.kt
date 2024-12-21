package com.babakan.cashier.presentation.authentication.screen.register.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AlternateEmail
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.LockOpen
import androidx.compose.material.icons.outlined.Person
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
import com.babakan.cashier.R

@Composable
fun RegisterForm(
    name: String,
    username: String,
    email: String,
    password: String,
    confirmPassword: String,
    nameError: String?,
    usernameError: String?,
    emailError: String?,
    passwordError: String?,
    confirmPasswordError: String?,
    onNameChange: (String) -> Unit,
    onUsernameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit
) {
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

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
            isError = usernameError != null,
            value = username,
            onValueChange = { onUsernameChange(it) },
            label = { Text(stringResource(R.string.username)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            leadingIcon = { Icon(Icons.Outlined.AlternateEmail, stringResource(R.string.name)) },
            supportingText = { usernameError?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
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
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            leadingIcon = { Icon(Icons.Outlined.Email, stringResource(R.string.name)) },
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
            leadingIcon = { Icon(Icons.Outlined.LockOpen, stringResource(R.string.name)) },
            trailingIcon = { IconButton({ passwordVisible = !passwordVisible }) { Icon(if (passwordVisible) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff, stringResource(
                R.string.name)
            ) } },
            supportingText = { passwordError?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
            shape = MaterialTheme.shapes.medium,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
                errorLeadingIconColor = MaterialTheme.colorScheme.error
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            isError = confirmPasswordError != null,
            value = confirmPassword,
            onValueChange = { onConfirmPasswordChange(it) },
            label = { Text(stringResource(R.string.confirmPassword)) },
            singleLine = true,
            visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation('●'),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            leadingIcon = { Icon(Icons.Outlined.Lock, stringResource(R.string.name)) },
            trailingIcon = { IconButton({ confirmPasswordVisible = !confirmPasswordVisible }) { Icon(if (confirmPasswordVisible) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff, stringResource(
                R.string.name)
            ) } },
            supportingText = { confirmPasswordError?.let { Text(text = it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall) } },
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