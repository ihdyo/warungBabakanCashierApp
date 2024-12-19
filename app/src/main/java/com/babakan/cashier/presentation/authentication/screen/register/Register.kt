package com.babakan.cashier.presentation.authentication.screen.register

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AlternateEmail
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.LockOpen
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.babakan.cashier.R
import com.babakan.cashier.presentation.authentication.viewmodel.AuthViewModel
import com.babakan.cashier.data.state.UiState
import com.babakan.cashier.utils.constant.SizeChart
import com.babakan.cashier.utils.validator.Validator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun Register(
    authViewModel: AuthViewModel = viewModel(),
    authScope: CoroutineScope,
    snackBarHostState: SnackbarHostState,
    onNavigateToLogin: () -> Unit,
    onNavigateToMain: () -> Unit
) {
    val context = LocalContext.current

    val uiState by authViewModel.uiState.collectAsState()

    var name by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var nameError by remember { mutableStateOf<String?>(null) }
    var usernameError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var confirmPasswordError by remember { mutableStateOf<String?>(null) }

    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(PaddingValues(SizeChart.DEFAULT_SPACE.dp)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                stringResource(R.string.registerGreeting),
                style = MaterialTheme.typography.headlineLarge
            )
            Spacer(modifier = Modifier.height(SizeChart.BETWEEN_SECTIONS.dp))
            Column {
                OutlinedTextField(
                    isError = nameError != null,
                    value = name,
                    onValueChange = { name = it },
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
                    onValueChange = { username = it },
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
                    onValueChange = { email = it },
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
                    onValueChange = { password = it },
                    label = { Text(stringResource(R.string.password)) },
                    singleLine = true,
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation('●'),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    leadingIcon = { Icon(Icons.Outlined.LockOpen, stringResource(R.string.name)) },
                    trailingIcon = { IconButton({ passwordVisible = !passwordVisible }) { Icon(if (passwordVisible) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff, stringResource(R.string.name)) } },
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
                    onValueChange = { confirmPassword = it },
                    label = { Text(stringResource(R.string.confirmPassword)) },
                    singleLine = true,
                    visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation('●'),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    leadingIcon = { Icon(Icons.Outlined.Lock, stringResource(R.string.name)) },
                    trailingIcon = { IconButton({ confirmPasswordVisible = !confirmPasswordVisible }) { Icon(if (confirmPasswordVisible) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff, stringResource(R.string.name)) } },
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
            Spacer(modifier = Modifier.height(SizeChart.BETWEEN_SECTIONS.dp))
            Button(
                onClick = {
                    nameError = Validator.isNotEmpty(context, name, context.getString(R.string.name))
                    usernameError = Validator.isNotEmpty(context, username, context.getString(R.string.username)) ?: Validator.isValidUsername(context, username)
                    emailError = Validator.isNotEmpty(context, email, context.getString(R.string.email)) ?: Validator.isValidEmail(context, email)
                    passwordError = Validator.isNotEmpty(context, password, context.getString(R.string.password)) ?: Validator.isValidPassword(context, password)
                    confirmPasswordError = Validator.isNotEmpty(context, confirmPassword, context.getString(R.string.confirmPassword)) ?: Validator.isPasswordMatch(context, password, confirmPassword)

                    if (nameError == null && usernameError == null && emailError == null && passwordError == null && confirmPasswordError == null) {
                        authViewModel.register(name, username, email, password)
                    } else {
                        authScope.launch { snackBarHostState.showSnackbar(context.getString(R.string.fillInAllFields)) }
                    }
                },
                Modifier.fillMaxWidth()
            ) { Text(stringResource(R.string.register)) }
            TextButton(
                onClick = { onNavigateToLogin() },
                Modifier.fillMaxWidth()
            ) { Text(stringResource(R.string.loginPrompt)) }
        }
        when (uiState) {
            is UiState.Loading -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator()
                }
            }
            is UiState.Success -> {
                onNavigateToMain()
            }
            is UiState.Error -> {
                val error = (uiState as UiState.Error)
                LaunchedEffect(error.message) {
                    snackBarHostState.showSnackbar(error.message)
                }
            }
            else -> {}
        }

    }
}