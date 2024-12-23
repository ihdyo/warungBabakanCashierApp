package com.babakan.cashier.presentation.authentication.screen.register

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.babakan.cashier.R
import com.babakan.cashier.common.ui.FullscreenLoading
import com.babakan.cashier.presentation.authentication.viewmodel.AuthViewModel
import com.babakan.cashier.data.state.UiState
import com.babakan.cashier.presentation.authentication.screen.register.component.RegisterForm
import com.babakan.cashier.utils.constant.SizeChart
import com.babakan.cashier.utils.validator.Validator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun Register(
    authViewModel: AuthViewModel = viewModel(),
    authScope: CoroutineScope,
    snackBarHostState: SnackbarHostState,
    onNavigateToLogin: () -> Unit
) {
    val context = LocalContext.current

    val uiState by authViewModel.authState.collectAsState()

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

    Scaffold(snackbarHost = { SnackbarHost(snackBarHostState) }) { innerPadding ->
        if (uiState is UiState.Loading) { FullscreenLoading() }
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
            Spacer(Modifier.height(SizeChart.BETWEEN_SECTIONS.dp))
            RegisterForm(
                name = name,
                username = username,
                email = email,
                password = password,
                confirmPassword = confirmPassword,
                nameError = nameError,
                usernameError = usernameError,
                emailError = emailError,
                passwordError = passwordError,
                confirmPasswordError = confirmPasswordError,
                onNameChange = { name = it },
                onUsernameChange = { username = it },
                onEmailChange = { email = it },
                onPasswordChange = { password = it },
                onConfirmPasswordChange = { confirmPassword = it }
            )
            Spacer(Modifier.height(SizeChart.BETWEEN_SECTIONS.dp))
            Button(
                {
                    nameError = Validator.isNotEmpty(context, name, context.getString(R.string.name))
                    usernameError = Validator.isNotEmpty(context, username, context.getString(R.string.username)) ?: Validator.isValidUsername(context, username)
                    emailError = Validator.isNotEmpty(context, email, context.getString(R.string.email)) ?: Validator.isValidEmail(context, email)
                    passwordError = Validator.isNotEmpty(context, password, context.getString(R.string.password)) ?: Validator.isValidPassword(context, password)
                    confirmPasswordError = Validator.isNotEmpty(context, confirmPassword, context.getString(R.string.confirmPassword)) ?: Validator.isPasswordMatch(context, password, confirmPassword)

                    if (nameError == null && usernameError == null && emailError == null && passwordError == null && confirmPasswordError == null) {
                        authViewModel.registerUser(name, username, email, password)
                    } else {
                        authScope.launch { snackBarHostState.showSnackbar(context.getString(R.string.fillInAllFields)) }
                    }
                },
                Modifier.fillMaxWidth()
            ) { Text(stringResource(R.string.register)) }
            TextButton(
                { onNavigateToLogin() },
                Modifier.fillMaxWidth()
            ) { Text(stringResource(R.string.loginPrompt)) }
        }
    }
}