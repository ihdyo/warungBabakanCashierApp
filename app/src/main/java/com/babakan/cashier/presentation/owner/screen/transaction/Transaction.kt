package com.babakan.cashier.presentation.owner.screen.transaction

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import com.babakan.cashier.common.list.TransactionList
import com.babakan.cashier.data.dummy.dummyTransactionList

@Composable
fun Transaction(
    nestedScrollConnection: NestedScrollConnection
) {

    val transactions = dummyTransactionList

    Box(Modifier.fillMaxSize()) {
        TransactionList(
            nestedScrollConnection = nestedScrollConnection,
            transactions = transactions
        )
    }
}