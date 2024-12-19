package com.babakan.cashier.presentation.authentication.screen.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
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
fun Login(
    authViewModel: AuthViewModel = viewModel(),
    authScope: CoroutineScope,
    snackBarHostState: SnackbarHostState,
    onNavigateToRegister: () -> Unit
) {
    val context = LocalContext.current
    val uiState by authViewModel.uiState.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    var passwordVisible by remember { mutableStateOf(false) }

    when (uiState) {
        is UiState.Loading -> {
            Box(Modifier.fillMaxSize()) { CircularProgressIndicator() }
        }
        is UiState.Error -> {
            val error = (uiState as UiState.Error)
            LaunchedEffect(error.message) {
                snackBarHostState.showSnackbar(error.message)
            }
        }
        is UiState.Success -> {}
    }

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
                stringResource(R.string.loginGreeting),
                style = MaterialTheme.typography.headlineLarge
            )
            Spacer(modifier = Modifier.height(SizeChart.BETWEEN_SECTIONS.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy(SizeChart.BETWEEN_ITEMS.dp)
            ) {
                OutlinedTextField(
                    isError = emailError != null,
                    value = email,
                    onValueChange = { email = it },
                    label = { Text(stringResource(R.string.email)) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    leadingIcon = { Icon(Icons.Outlined.Email, stringResource(R.string.email)) },
                    supportingText = { emailError?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
                    shape = MaterialTheme.shapes.medium,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
                        errorLeadingIconColor = MaterialTheme.colorScheme.error
                    )
                )
                OutlinedTextField(
                    isError = passwordError != null,
                    value = password,
                    onValueChange = { password = it },
                    label = { Text(stringResource(R.string.password)) },
                    modifier = Modifier.fillMaxWidth(),
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
                    )
                )
            }
            Spacer(modifier = Modifier.height(SizeChart.BETWEEN_ITEMS.dp))
            Button(
                onClick = {
                    emailError = Validator.isNotEmpty(context, email, context.getString(R.string.email)) ?: Validator.isValidEmail(context, email)
                    passwordError = Validator.isNotEmpty(context, password, context.getString(R.string.password))

                    if (emailError == null && passwordError == null) {
                        authViewModel.login(email, password)
                    } else {
                        authScope.launch { snackBarHostState.showSnackbar(context.getString(R.string.fillInAllFields)) }
                    }
                },
                Modifier.fillMaxWidth()
            ) { Text(stringResource(R.string.login)) }
            TextButton(
                onClick = { onNavigateToRegister() },
                Modifier.fillMaxWidth(),
            ) { Text(stringResource(R.string.registerPrompt)) }
        }
    }
}