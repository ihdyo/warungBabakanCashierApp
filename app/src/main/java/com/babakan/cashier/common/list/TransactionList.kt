package com.babakan.cashier.common.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.babakan.cashier.R
import com.babakan.cashier.common.item.TransactionItem
import com.babakan.cashier.presentation.authentication.model.UserModel
import com.babakan.cashier.presentation.owner.model.TransactionModel
import com.babakan.cashier.utils.constant.RemoteData
import com.babakan.cashier.utils.constant.SizeChart

@Composable
fun TransactionList(
    transactions: List<TransactionModel>,
    users: List<UserModel>,
    nestedScrollConnection: NestedScrollConnection,
    navController: NavController
) {
    var expandedIndex by remember { mutableIntStateOf(-1) }

    val itemCount = transactions.size
    val isLimit = itemCount == RemoteData.LIMIT_TRANSACTION.toInt()

    LazyColumn(
        contentPadding = PaddingValues(SizeChart.DEFAULT_SPACE.dp),
        verticalArrangement = Arrangement.spacedBy(SizeChart.BETWEEN_ITEMS.dp),
        modifier = Modifier.nestedScroll(nestedScrollConnection)
    ) {
        itemsIndexed(transactions) { index, item ->
            val isExpanded = expandedIndex == index

            val userItem = users.find { it.id == item.userId } ?: UserModel()

            OutlinedCard(
                shape = MaterialTheme.shapes.large,
                modifier = Modifier.fillMaxWidth()
            ) {
                TransactionItem(
                    transactionItem = item,
                    userItem = userItem,
                    isExpanded = isExpanded,
                    onExpand = {
                        expandedIndex = if (isExpanded) -1 else index
                    },
                    navController = navController
                )
            }
        }
        if (isLimit) {
            item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(SizeChart.BETWEEN_ITEMS.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                        .padding(SizeChart.BETWEEN_SECTIONS.dp)
                ) {
                    Text(
                        stringResource(R.string.limitTransaction),
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Center
                    )
                    Text(stringResource(
                        R.string.limitTransactionMessage),
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }

}