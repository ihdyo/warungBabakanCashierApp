package com.babakan.cashier.presentation.cashier.screen.invoice

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.babakan.cashier.R
import com.babakan.cashier.common.item.InvoiceItem
import com.babakan.cashier.common.ui.FullscreenLoading
import com.babakan.cashier.data.state.UiState
import com.babakan.cashier.presentation.authentication.model.UserModel
import com.babakan.cashier.presentation.owner.model.TransactionModel
import com.babakan.cashier.presentation.owner.viewmodel.ProductOutViewModel
import com.babakan.cashier.presentation.owner.viewmodel.TransactionViewModel
import com.babakan.cashier.presentation.owner.viewmodel.UserViewModel
import com.babakan.cashier.utils.constant.SizeChart

@Composable
fun Invoice(
    userViewModel: UserViewModel = viewModel(),
    transactionViewModel: TransactionViewModel = viewModel(),
    productOutViewModel: ProductOutViewModel = viewModel(),
    transactionId: String,
) {
    val userByIdState by userViewModel.fetchUserByIdState.collectAsState()
    val transactionByIdState by transactionViewModel.transactionByIdState.collectAsState()
    val productOutState by productOutViewModel.fetchProductOutState.collectAsState()

    LaunchedEffect(transactionId) {
        transactionViewModel.fetchTransactionById(transactionId)
        productOutViewModel.fetchProductOut(transactionId)
    }

    val transaction = when (val state = transactionByIdState) {
        is UiState.Success -> state.data
        else -> TransactionModel()
    }

    val productOut = when (val state = productOutState) {
        is UiState.Success -> state.data
        else -> emptyList()
    }

    val userId = transaction.userId

    LaunchedEffect(userId) {
        userViewModel.fetchUserById(userId)
    }

    val user = when (val state = userByIdState) {
        is UiState.Success -> state.data
        else -> UserModel()
    }

    val showLoading = userByIdState is UiState.Loading || transactionByIdState is UiState.Loading || productOutState is UiState.Loading

    Box(Modifier.fillMaxSize()) {
        Column(
            verticalArrangement = Arrangement.spacedBy(SizeChart.BETWEEN_SECTIONS.dp),
            modifier = Modifier
                .padding(horizontal = SizeChart.DEFAULT_SPACE.dp)
                .padding(bottom = SizeChart.PRINT_BUTTON_HEIGHT.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                stringResource(R.string.invoice),
                style = MaterialTheme.typography.titleLarge
            )
            InvoiceItem(
                transactionItem = transaction,
                userItem = user,
                productOut = productOut,
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(SizeChart.SMALL_SPACE.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(SizeChart.DEFAULT_SPACE.dp)
        ) {
            Button(
                { /*TODO: Save to Device*/ },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            ) {
                Text(stringResource(R.string.saveToDevice))
            }
            IconButton(
                { /*TODO: Share*/ },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ),
            ) {
                Icon(
                    Icons.Default.Share,
                    stringResource(R.string.share)
                )
            }
        }
        if (showLoading) { FullscreenLoading() }
    }
}