package com.babakan.cashier.presentation.owner.screen.transaction

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.lifecycle.viewmodel.compose.viewModel
import com.babakan.cashier.common.list.TransactionList
import com.babakan.cashier.data.state.UiState
import com.babakan.cashier.presentation.owner.viewmodel.TransactionViewModel
import com.babakan.cashier.presentation.owner.viewmodel.UserViewModel

@Composable
fun Transaction(
    userViewModel: UserViewModel = viewModel(),
    transactionViewModel: TransactionViewModel = viewModel(),
    nestedScrollConnection: NestedScrollConnection,
) {
    val usersState by userViewModel.fetchUsersState.collectAsState()
    val transactionState by transactionViewModel.fetchTransactionsState.collectAsState()

    val users = when (val state = usersState) {
        is UiState.Success -> state.data
        else -> emptyList()
    }

    val transactions = when (val state = transactionState) {
        is UiState.Success -> state.data
        else -> emptyList()
    }

    val showLoading = usersState is UiState.Loading || transactionState is UiState.Loading

    Box(Modifier.fillMaxSize()) {
        if (showLoading) {
            LinearProgressIndicator(Modifier.fillMaxWidth())
        }
        TransactionList(
            transactions = transactions,
            users = users,
            nestedScrollConnection = nestedScrollConnection
        )
    }
}