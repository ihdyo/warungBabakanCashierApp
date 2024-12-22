package com.babakan.cashier.presentation.navigation.screen.navigation.component.searchbar.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import com.babakan.cashier.common.list.TransactionList
import com.babakan.cashier.data.dummy.dummyTransactionList

@Composable
fun SearchTransaction(
    nestedScrollConnection: NestedScrollConnection,
    query: String,
    onResultCountChange: (Int) -> Unit,
    dateRange: Pair<Long?, Long?>? = null,
    isReportByTransactionNumber: Boolean,
    isReportByCashier: Boolean,
    isReportByDate: Boolean,
) {

    val transactions = if (query.isNotBlank() || dateRange != null) {
        dummyTransactionList
    } else {
        emptyList()
    }
    onResultCountChange(transactions.size)

//    dateRange?.let { range ->
//        Text("Selected Start: ${range.first ?: "Not Selected"}")
//        Text("Selected End: ${range.second ?: "Not Selected"}")
//    }

    TransactionList(
        transactions = transactions,
        users = emptyList(), // TODO: Change this,
        productOut = emptyList(), // TODO: Change this,
        nestedScrollConnection = nestedScrollConnection
    )

}