package com.babakan.cashier.common.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.babakan.cashier.presentation.owner.model.CategoryModel
import com.babakan.cashier.utils.constant.SizeChart

@Composable
fun CategoryList(
    nestedScrollConnection: NestedScrollConnection,
    categories: List<CategoryModel>,
    isAdmin: Boolean = false,
    onAdminEdit: (CategoryModel) -> Unit = {}
) {
    LazyColumn(
        contentPadding = tabContentPadding(),
        verticalArrangement = Arrangement.spacedBy(SizeChart.BETWEEN_ITEMS.dp),
        modifier = Modifier.nestedScroll(nestedScrollConnection)
    ) {
        itemsIndexed(categories) { index, item ->
            Card(colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceContainer)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(SizeChart.SMALL_SPACE.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(SizeChart.SMALL_SPACE.dp)
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
                        Text(
                            item.name,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    if (isAdmin) { EditButton { onAdminEdit(item) } }
                }
            }
        }
    }
}