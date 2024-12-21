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
import com.babakan.cashier.common.ui.FullscreenLoading
import com.babakan.cashier.presentation.authentication.viewmodel.AuthViewModel
import com.babakan.cashier.data.state.UiState
import com.babakan.cashier.presentation.authentication.screen.login.component.LoginForm
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

    Scaffold(snackbarHost = { SnackbarHost(snackBarHostState) }) { innerPadding ->
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
            Spacer(Modifier.height(SizeChart.BETWEEN_SECTIONS.dp))
            LoginForm(
                email = email,
                password = password,
                emailError = emailError,
                passwordError = passwordError,
                onEmailChange = { email = it },
                onPasswordChange = { password = it }
            )
            Spacer(Modifier.height(SizeChart.BETWEEN_ITEMS.dp))
            Button(
                {
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
                { onNavigateToRegister() },
                Modifier.fillMaxWidth(),
            ) { Text(stringResource(R.string.registerPrompt)) }
        }
        when (uiState) {
            is UiState.Loading -> { FullscreenLoading() }
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