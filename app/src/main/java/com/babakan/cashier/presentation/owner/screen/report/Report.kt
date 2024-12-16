package com.babakan.cashier.presentation.owner.screen.report

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.babakan.cashier.R
import com.babakan.cashier.utils.constant.Size
import com.babakan.cashier.utils.formatter.Formatter

@Composable
fun Report(
    nestedScrollConnection: NestedScrollConnection
) {

    var isExpanded by remember { mutableStateOf(false) }

    LazyColumn(
        contentPadding = PaddingValues(Size.DEFAULT_SPACE.dp),
        verticalArrangement = Arrangement.spacedBy(Size.BETWEEN_ITEMS.dp),
        modifier = Modifier.nestedScroll(nestedScrollConnection)
    ) {
        items(20) {
            OutlinedCard (
                shape = MaterialTheme.shapes.large,
                modifier = Modifier.fillMaxWidth()
            ) {
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
                            verticalArrangement = Arrangement.spacedBy(Size.DEFAULT_SPACE.dp),
                            modifier = Modifier
                                .padding(Size.DEFAULT_SPACE.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(Size.SMALL_SPACE.dp)
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
                                            modifier = Modifier.padding(horizontal = Size.SIZE_XS.dp, vertical = Size.SIZE_2XS.dp)
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
                                    verticalArrangement = Arrangement.spacedBy(Size.DEFAULT_SPACE.dp)
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
                                        Column {
                                            Card(
                                                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer),
                                            ) {
                                                Text(
                                                    stringResource(R.string.number, 2).uppercase(),
                                                    style = MaterialTheme.typography.labelMedium,
                                                    modifier = Modifier.padding(horizontal = Size.SIZE_SM.dp, vertical = Size.SIZE_2XS.dp)
                                                )
                                            }
                                            Text("Lorem Ipsum")
                                        }
                                        Column(
                                            horizontalAlignment = Alignment.End
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
                            verticalArrangement = Arrangement.spacedBy(Size.SMALL_SPACE.dp),
                            horizontalAlignment = Alignment.End,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(Size.DEFAULT_SPACE.dp)
                        ) {
                            Text(
                                stringResource(R.string.totalItem, 5),
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )
                            if (isExpanded) {
                                for (innerIndex in 0 until 5) {
                                    Card {
                                        Row(
                                            verticalAlignment = Alignment.Bottom,
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(Size.SMALL_SPACE.dp)
                                        ) {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.spacedBy(Size.SMALL_SPACE.dp)
                                            ) {
                                                Card(
                                                    shape = MaterialTheme.shapes.small
                                                ) {
                                                    Image(
                                                        painterResource(R.drawable.placeholder),
                                                        stringResource(R.string.productImage),
                                                        contentScale = ContentScale.Crop,
                                                        modifier = Modifier.size(Size.IMAGE_LIST_HEIGHT.dp)
                                                    )
                                                }
                                                Column {
                                                    Text(
                                                        "Lorem Ipsum",
                                                        style = MaterialTheme.typography.titleMedium
                                                    )
                                                    Text(
                                                        Formatter.currency(10000),
                                                        style = MaterialTheme.typography.titleLarge.copy(
                                                            color = MaterialTheme.colorScheme.primary
                                                        )
                                                    )
                                                }
                                            }
                                            Text(
                                                stringResource(R.string.countItem, 2),
                                                style = MaterialTheme.typography.labelLarge
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}