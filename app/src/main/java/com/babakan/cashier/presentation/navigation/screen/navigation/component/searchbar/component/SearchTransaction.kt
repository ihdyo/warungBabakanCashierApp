package com.babakan.cashier.presentation.navigation.screen.navigation.component.searchbar.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.lifecycle.viewmodel.compose.viewModel
import com.babakan.cashier.common.list.TransactionList
import com.babakan.cashier.data.dummy.dummyTransactionList
import com.babakan.cashier.data.state.UiState
import com.babakan.cashier.presentation.owner.viewmodel.TransactionViewModel
import com.babakan.cashier.presentation.owner.viewmodel.UserViewModel
import com.google.firebase.Timestamp
import java.util.Date

@Composable
fun SearchTransaction(
    transactionViewModel: TransactionViewModel = viewModel(),
    userViewModel: UserViewModel = viewModel(),
    nestedScrollConnection: NestedScrollConnection,
    query: String,
    onResultCountChange: (Int) -> Unit,
    dateRange: Pair<Long?, Long?>? = null,
    isReportByTransactionNumber: Boolean,
    isReportByCashier: Boolean,
    isReportByDate: Boolean,
    isSearchActive: Boolean
) {
    val searchTransactionByTransactionId by transactionViewModel.searchTransactionIdState.collectAsState()
    val searchTransactionByUserName by transactionViewModel.searchTransactionNameState.collectAsState()
    val searchTransactionByDateRange by transactionViewModel.searchTransactionDateRangeState.collectAsState()
    val usersState by userViewModel.fetchUsersState.collectAsState()

    LaunchedEffect(query, dateRange) {
        if (isReportByTransactionNumber) {
            transactionViewModel.searchTransactionsByTransactionId(query)
        }
        if (isReportByCashier) {
            transactionViewModel.searchTransactionsByUserName(query)
        }
        if (isReportByDate) {
            if (dateRange != null) {
                transactionViewModel.searchTransactionsByDateRange(
                    Timestamp(Date(dateRange.first!!)),
                    Timestamp(Date(dateRange.second!!))
                )
            }
        }
    }

    val transactions = if (isReportByTransactionNumber) {
        when (val state = searchTransactionByTransactionId) {
            is UiState.Success -> state.data
            else -> emptyList()
        }
    } else if (isReportByCashier) {
        when (val state = searchTransactionByUserName) {
            is UiState.Success -> state.data
            else -> emptyList()
        }
    } else if (isReportByDate) {
        when (val state = searchTransactionByDateRange) {
            is UiState.Success -> state.data
            else -> emptyList()
        }
    } else {
        emptyList()
    }
    val users = when (val state = usersState) {
        is UiState.Success -> state.data
        else -> emptyList()
    }

    if (isSearchActive) {
        if (isReportByTransactionNumber) {
            transactionViewModel.searchTransactionsByTransactionId(query)
        }
        if (isReportByCashier) {
            transactionViewModel.searchTransactionsByUserName(query)
        }
        if (isReportByDate) {
            if (dateRange != null) {
                transactionViewModel.searchTransactionsByDateRange(
                    Timestamp(Date(dateRange.first!!)),
                    Timestamp(Date(dateRange.second!!))
                )
            }
        }
    }

    if (!isSearchActive) {
        transactionViewModel.resetSearchState()
        return
    }

    onResultCountChange(transactions.size)

    TransactionList(
        transactions = transactions,
        users = users,
        nestedScrollConnection = nestedScrollConnection
    )

}