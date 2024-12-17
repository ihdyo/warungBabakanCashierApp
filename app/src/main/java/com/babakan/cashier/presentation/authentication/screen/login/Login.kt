package com.babakan.cashier.presentation.authentication.screen.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.babakan.cashier.R
import com.babakan.cashier.utils.constant.SizeChart

@Composable
fun Login(
    onNavigateToRegister: () -> Unit,
    onNavigateToMain: () -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(PaddingValues(SizeChart.DEFAULT_SPACE.dp)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            stringResource(R.string.registerGreeting),
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(SizeChart.BETWEEN_SECTIONS.dp))
        Column(
            verticalArrangement = Arrangement.spacedBy(SizeChart.BETWEEN_ITEMS.dp)
        ) {
            OutlinedTextField(
                "",
                onValueChange = {},
                label = { Text(stringResource(R.string.email)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                "",
                onValueChange = {},
                label = { Text(stringResource(R.string.password)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(SizeChart.BETWEEN_ITEMS.dp))
            Button(
                {
                    // TODO Login Logic
                    onNavigateToMain()
                },
                Modifier.fillMaxWidth(),
            ) {
                Text(stringResource(R.string.login))
            }
        }
        TextButton(
            { onNavigateToRegister() },
            Modifier.fillMaxWidth(),
        ) {
            Text(stringResource(R.string.registerPrompt))
        }
    }
}