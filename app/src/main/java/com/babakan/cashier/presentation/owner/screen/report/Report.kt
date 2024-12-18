package com.babakan.cashier.presentation.owner.screen.report

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.babakan.cashier.common.item.ReportItem
import com.babakan.cashier.common.style.pageContentPadding
import com.babakan.cashier.utils.constant.SizeChart

@Composable
fun Report(nestedScrollConnection: NestedScrollConnection) {
    LazyColumn(
        contentPadding = PaddingValues(SizeChart.DEFAULT_SPACE.dp),
        verticalArrangement = Arrangement.spacedBy(SizeChart.BETWEEN_ITEMS.dp),
        modifier = Modifier.nestedScroll(nestedScrollConnection)
    ) {
        items(20) {
            OutlinedCard (
                shape = MaterialTheme.shapes.large,
                modifier = Modifier.fillMaxWidth()
            ) {
                ReportItem()
            }
        }
    }
}