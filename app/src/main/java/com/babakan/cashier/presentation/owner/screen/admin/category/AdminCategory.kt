package com.babakan.cashier.presentation.owner.screen.admin.category

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.babakan.cashier.common.builder.IconLoader
import com.babakan.cashier.common.component.EditButton
import com.babakan.cashier.common.style.tabContentPadding
import com.babakan.cashier.data.dummy.dummyCategoryList
import com.babakan.cashier.utils.constant.SizeChart

@Composable
fun AdminCategory(nestedScrollConnection: NestedScrollConnection) {

    val category = dummyCategoryList

    Box(Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            GridCells.Fixed(2),
            contentPadding = tabContentPadding(),
            verticalArrangement = Arrangement.spacedBy(SizeChart.BETWEEN_ITEMS.dp),
            horizontalArrangement = Arrangement.spacedBy(SizeChart.BETWEEN_ITEMS.dp),
            modifier = Modifier.nestedScroll(nestedScrollConnection)
        ) {
            itemsIndexed(category) { index, item ->
                Card(colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceContainer)) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(SizeChart.BETWEEN_TEXTS.dp),
                        modifier = Modifier.padding(SizeChart.SMALL_SPACE.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Card(
                                shape = MaterialTheme.shapes.small,
                                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondaryContainer)
                            ) {
                                Box(Modifier.padding(SizeChart.SMALL_SPACE.dp)) {
                                    IconLoader(
                                        item.iconUrl,
                                        SizeChart.ICON_LARGE.dp,
                                        MaterialTheme.colorScheme.secondary
                                    )
                                }
                            }
                            EditButton {
                                // TODO Edit
                            }
                        }
                        Text(
                            item.name,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}