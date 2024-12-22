package com.babakan.cashier.common.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.babakan.cashier.common.item.TransactionItem
import com.babakan.cashier.presentation.authentication.model.UserModel
import com.babakan.cashier.presentation.owner.model.ProductOutModel
import com.babakan.cashier.presentation.owner.model.TransactionModel
import com.babakan.cashier.utils.constant.SizeChart

@Composable
fun TransactionList(
    transactions: List<TransactionModel>,
    users: List<UserModel>,
    nestedScrollConnection: NestedScrollConnection
) {
    var expandedIndex by remember { mutableIntStateOf(-1) }

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
                    }
                )
            }
        }
    }
}