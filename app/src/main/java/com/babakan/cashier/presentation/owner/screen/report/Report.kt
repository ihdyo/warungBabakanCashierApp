package com.babakan.cashier.presentation.owner.screen.report

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.babakan.cashier.common.item.ReportItem
import com.babakan.cashier.common.style.pageContentPadding
import com.babakan.cashier.data.dummy.dummyTransactionList
import com.babakan.cashier.data.dummy.dummyUserList
import com.babakan.cashier.presentation.authentication.model.UserModel
import com.babakan.cashier.presentation.owner.model.CategoryModel
import com.babakan.cashier.utils.constant.SizeChart

@Composable
fun Report(nestedScrollConnection: NestedScrollConnection) {

    val report = dummyTransactionList
    val user = dummyUserList

    Box(Modifier.fillMaxSize()) {
        LazyColumn(
            contentPadding = PaddingValues(SizeChart.DEFAULT_SPACE.dp),
            verticalArrangement = Arrangement.spacedBy(SizeChart.BETWEEN_ITEMS.dp),
            modifier = Modifier.nestedScroll(nestedScrollConnection)
        ) {
            itemsIndexed(report) { index, item ->

                val userItem = user.find { it.id == item.userId } ?: UserModel()

                OutlinedCard (
                    shape = MaterialTheme.shapes.large,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ReportItem(
                        index = index,
                        transactionItem = item,
                        userItem = userItem
                    )
                }
            }
        }
    }
}