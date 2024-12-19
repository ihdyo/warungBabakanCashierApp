package com.babakan.cashier.presentation.owner.screen.admin.product

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.babakan.cashier.R
import com.babakan.cashier.common.builder.ImageLoader
import com.babakan.cashier.common.component.CategoryComponent
import com.babakan.cashier.common.component.EditButton
import com.babakan.cashier.common.style.tabContentPadding
import com.babakan.cashier.data.dummy.dummyCategoryList
import com.babakan.cashier.data.dummy.dummyProductList
import com.babakan.cashier.presentation.owner.model.CategoryModel
import com.babakan.cashier.utils.constant.SizeChart
import com.babakan.cashier.utils.formatter.Formatter

@Composable
fun AdminProduct(
    nestedScrollConnection: NestedScrollConnection
) {

    val product = dummyProductList
    val category = dummyCategoryList

    Box(Modifier.fillMaxSize()) {
        LazyColumn(
            contentPadding = tabContentPadding(),
            verticalArrangement = Arrangement.spacedBy(SizeChart.BETWEEN_ITEMS.dp),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .nestedScroll(nestedScrollConnection)
        ) {
            itemsIndexed(product) {index, item ->
                val itemCategory = category.find { it.id == item.categoryId } ?: CategoryModel()

                Card(colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceContainer)) {
                    Row(
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(SizeChart.SMALL_SPACE.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.spacedBy(SizeChart.SMALL_SPACE.dp),
                            modifier = Modifier.weight(1f).height(IntrinsicSize.Min)
                        ) {
                            Card(
                                shape = MaterialTheme.shapes.small
                            ) {
                                ImageLoader(
                                    item.imageUrl,
                                    SizeChart.IMAGE_ADMIN_HEIGHT.dp,
                                    SizeChart.IMAGE_ADMIN_HEIGHT.dp
                                )
                            }
                            Column(
                                verticalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .weight(1f)
                            ) {
                                CategoryComponent(
                                    name = itemCategory.name,
                                    iconUrl = itemCategory.iconUrl
                                )
                                Text(
                                    item.name,
                                    style = MaterialTheme.typography.titleMedium,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    Formatter.currency(item.price),
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                )
                            }
                        }
                        EditButton {
                            // TODO Edit
                        }
                    }
                }
            }
        }
    }
}