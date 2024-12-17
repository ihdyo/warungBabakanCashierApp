package com.babakan.cashier.presentation.owner.screen.admin.category

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.babakan.cashier.R
import com.babakan.cashier.common.component.EditButton
import com.babakan.cashier.utils.constant.SizeChart

@Composable
fun AdminCategory(
    nestedScrollConnection: NestedScrollConnection
) {
    Box(Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            GridCells.Fixed(2),
            contentPadding = PaddingValues(SizeChart.DEFAULT_SPACE.dp),
            verticalArrangement = Arrangement.spacedBy(SizeChart.BETWEEN_ITEMS.dp),
            horizontalArrangement = Arrangement.spacedBy(SizeChart.BETWEEN_ITEMS.dp),
            modifier = Modifier.nestedScroll(nestedScrollConnection)
        ) {
            items(5) {
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
                                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer)
                            ) {
                                Image(
                                    painterResource(R.drawable.ic_placeholder),
                                    stringResource(R.string.category),
                                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                                    modifier = Modifier
                                        .padding(vertical = SizeChart.SMALL_SPACE.dp)
                                        .size(SizeChart.ICON_MEDIUM.dp)
                                )
                            }
                            EditButton {
                                // TODO Edit
                            }
                        }
                        Text(
                            "Lorem Ipsum",
                            style = MaterialTheme.typography.titleMedium,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}