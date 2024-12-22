package com.babakan.cashier.presentation.owner.screen.transaction

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.lifecycle.viewmodel.compose.viewModel
import com.babakan.cashier.common.list.TransactionList
import com.babakan.cashier.data.state.UiState
import com.babakan.cashier.presentation.authentication.model.UserModel
import com.babakan.cashier.presentation.owner.model.ProductOutModel
import com.babakan.cashier.presentation.owner.model.TransactionModel
import com.babakan.cashier.presentation.owner.viewmodel.ProductOutViewModel
import com.babakan.cashier.presentation.owner.viewmodel.TransactionViewModel
import com.babakan.cashier.presentation.owner.viewmodel.UserViewModel

@Composable
fun Transaction(
    productOutViewModel: ProductOutViewModel = viewModel(),
    transactionViewModel: TransactionViewModel = viewModel(),
    userViewModel: UserViewModel = viewModel(),
    nestedScrollConnection: NestedScrollConnection,
) {
    val transactionState by transactionViewModel.fetchTransactionsState.collectAsState()
    val usersState by userViewModel.fetchUsersState.collectAsState()
    val productOutState by productOutViewModel.fetchProductOutState.collectAsState()

    var transactions by remember { mutableStateOf(emptyList<TransactionModel>()) }
    var users by remember { mutableStateOf(emptyList<UserModel>()) }
    var productOut by remember { mutableStateOf(emptyList<Pair<String, List<ProductOutModel>>>()) }

    var showLoading by remember { mutableStateOf(false) }

    LaunchedEffect(usersState) {
        if (usersState is UiState.Loading) {
            showLoading = true
        } else if (usersState is UiState.Success) {
            showLoading = false
            users = (usersState as UiState.Success<List<UserModel>>).data
        }
    }
    LaunchedEffect(transactionState) {
        if (transactionState is UiState.Loading) {
            showLoading = true
        } else if (transactionState is UiState.Success) {
            showLoading = false
            transactions = (transactionState as UiState.Success<List<TransactionModel>>).data
            productOutViewModel.fetchProductOut(transactions.map { it.transactionId })
        }
    }
    LaunchedEffect(productOutState) {
        if (productOutState is UiState.Loading) {
            showLoading = true
        } else if (productOutState is UiState.Success) {
            showLoading = false
            productOut = (productOutState as UiState.Success<List<Pair<String, List<ProductOutModel>>>>).data
        }
    }
    Box(Modifier.fillMaxSize()) {
        if (showLoading) { LinearProgressIndicator(Modifier.fillMaxWidth()) }
        TransactionList(
            transactions = transactions,
            users = users,
            productOut = productOut,
            nestedScrollConnection = nestedScrollConnection
        )
    }
}