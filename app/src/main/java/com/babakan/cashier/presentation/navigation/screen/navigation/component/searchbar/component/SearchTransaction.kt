package com.babakan.cashier.presentation.navigation.screen.navigation.component.searchbar.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.babakan.cashier.R
import com.babakan.cashier.common.list.TransactionList
import com.babakan.cashier.common.ui.FullscreenLoading
import com.babakan.cashier.data.state.UiState
import com.babakan.cashier.presentation.owner.viewmodel.TransactionViewModel
import com.babakan.cashier.presentation.owner.viewmodel.UserViewModel
import com.babakan.cashier.utils.constant.SizeChart
import com.babakan.cashier.utils.formatter.Formatter
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
    isSearchActive: Boolean,
    navController : NavController
) {
    val searchTransactionByTransactionId by transactionViewModel.searchTransactionIdState.collectAsState()
    val searchTransactionByUserName by transactionViewModel.searchTransactionNameState.collectAsState()
    val searchTransactionByDateRange by transactionViewModel.searchTransactionDateRangeState.collectAsState()
    val usersState by userViewModel.fetchUsersState.collectAsState()

    LaunchedEffect(query, dateRange, isSearchActive, isReportByTransactionNumber, isReportByCashier, isReportByDate) {
        if (isSearchActive) {
            if (isReportByTransactionNumber) {
                transactionViewModel.searchTransactionsByTransactionId(query)

                transactionViewModel.clearTransactionByUserName()
                transactionViewModel.clearTransactionByDateRange()
            } else if (isReportByCashier) {
                transactionViewModel.searchTransactionsByUserName(query)

                transactionViewModel.clearTransactionByTransactionId()
                transactionViewModel.clearTransactionByDateRange()
            } else if (isReportByDate) {
                if (dateRange != null) {
                    transactionViewModel.searchTransactionsByDateRange(
                        Timestamp(Date(dateRange.first!!)),
                        Timestamp(Date(dateRange.second!!))
                    )

                    transactionViewModel.clearTransactionByTransactionId()
                    transactionViewModel.clearTransactionByUserName()
                }
            }
        } else {
            transactionViewModel.resetSearchState()
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

    val showLoading = searchTransactionByTransactionId is UiState.Loading
            || searchTransactionByUserName is UiState.Loading
            || searchTransactionByDateRange is UiState.Loading

    onResultCountChange(transactions.size)

    if (showLoading) { FullscreenLoading() }

    Column {
        AnimatedVisibility(transactions.isNotEmpty() && dateRange != null) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth().padding(horizontal = SizeChart.DEFAULT_SPACE.dp)
            ) {
                Text(
                    stringResource(R.string.totalTransaction, transactions.size)
                )
                Text(
                    Formatter.currency(transactions.sumOf { it.totalPrice }),
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color = MaterialTheme.colorScheme.primary
                    )
                )
            }
        }
        TransactionList(
            transactions = transactions,
            users = users,
            nestedScrollConnection = nestedScrollConnection,
            navController = navController
        )
    }


}