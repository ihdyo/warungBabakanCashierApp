package com.babakan.cashier.common.item

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.babakan.cashier.R
import com.babakan.cashier.common.component.CategoryComponent
import com.babakan.cashier.utils.constant.SizeChart
import com.babakan.cashier.utils.formatter.Formatter

@Composable
fun ReportItem() {
    var isExpanded by remember { mutableStateOf(false) }

    Column {
        Card(
            { isExpanded = !isExpanded },
            colors = CardDefaults.cardColors(
                if (isExpanded) {
                    MaterialTheme.colorScheme.surfaceContainerHigh
                } else {
                    MaterialTheme.colorScheme.surfaceContainer
                }
            ),
            shape = MaterialTheme.shapes.large
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(SizeChart.DEFAULT_SPACE.dp),
                modifier = Modifier
                    .padding(SizeChart.DEFAULT_SPACE.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(SizeChart.SMALL_SPACE.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            "3249VIY3429",
                            style = MaterialTheme.typography.titleLarge
                        )
                        Card(
                            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary),
                            shape = MaterialTheme.shapes.extraSmall
                        ) {
                            Text(
                                stringResource(R.string.paid).uppercase(),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = MaterialTheme.colorScheme.onPrimary
                                ),
                                modifier = Modifier.padding(horizontal = SizeChart.SIZE_XS.dp, vertical = SizeChart.SIZE_2XS.dp)
                            )
                        }
                    }
                    Text(
                        Formatter.date(System.currentTimeMillis()),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                AnimatedVisibility(!isExpanded) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(SizeChart.DEFAULT_SPACE.dp)
                    ) {
                        Text(
                            Formatter.currency(10000),
                            style = MaterialTheme.typography.headlineMedium.copy(
                                color = MaterialTheme.colorScheme.primary
                            )
                        )
                        Row(
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(Modifier.weight(1f)) {
                                Card(
                                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer),
                                ) {
                                    Text(
                                        stringResource(R.string.number, 2).uppercase(),
                                        style = MaterialTheme.typography.labelMedium,
                                        modifier = Modifier.padding(horizontal = SizeChart.SIZE_SM.dp, vertical = SizeChart.SIZE_2XS.dp)
                                    )
                                }
                                Text("Lorem Ipsum")
                            }
                            Column(
                                horizontalAlignment = Alignment.End,
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    stringResource(R.string.cashier),
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                )
                                Text("Lorem Ipsum")
                            }
                        }
                        HorizontalDivider(
                            thickness = 1.dp,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                        Text("Lorem Ipsum")
                    }
                }
            }
        }
        AnimatedVisibility (isExpanded) {
            Column(
                verticalArrangement = Arrangement.spacedBy(SizeChart.SMALL_SPACE.dp),
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = SizeChart.DEFAULT_SPACE.dp)
                    .padding(top = SizeChart.DEFAULT_SPACE.dp, bottom = SizeChart.SMALL_SPACE.dp)
            ) {
                for (categoryIndex in 0 until 2) {
                    Spacer(Modifier.weight(1f))
                    CategoryComponent()
                    for (productIndex in 0 until 3) {
                        OrderedProductItem()
                    }
                }
                Spacer(Modifier.weight(1f))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        stringResource(R.string.totalItem, 4),
                    )
                    Text(
                        Formatter.currency(10000),
                        style = MaterialTheme.typography.headlineMedium.copy(
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                }
                IconButton(
                    {isExpanded = !isExpanded},
                    Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Icon(
                        Icons.Default.ExpandLess,
                        stringResource(R.string.collapse),
                    )
                }
            }
        }
    }
}