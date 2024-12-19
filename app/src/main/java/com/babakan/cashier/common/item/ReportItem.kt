package com.babakan.cashier.common.item

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.babakan.cashier.common.list.ProductOutList
import com.babakan.cashier.data.dummy.dummyProductOutList
import com.babakan.cashier.presentation.authentication.model.UserModel
import com.babakan.cashier.presentation.owner.model.TransactionModel
import com.babakan.cashier.utils.constant.SizeChart
import com.babakan.cashier.utils.formatter.Formatter

@Composable
fun ReportItem(
    index: Int,
    transactionItem: TransactionModel,
    userItem: UserModel
) {
    var isExpanded by remember { mutableStateOf(false) }

    val productOut = dummyProductOutList

    val totalItem = 5

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
                            transactionItem.transactionId,
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
                        Formatter.date(transactionItem.createdAt),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                AnimatedVisibility(!isExpanded) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(SizeChart.DEFAULT_SPACE.dp)
                    ) {
                        Text(
                            Formatter.currency(transactionItem.totalPrice),
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
                                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondaryContainer),
                                ) {
                                    Text(
                                        stringResource(R.string.numberOrder, transactionItem.tableNumber).uppercase(),
                                        style = MaterialTheme.typography.labelLarge.copy(
                                            color = MaterialTheme.colorScheme.secondary
                                        ),
                                        modifier = Modifier.padding(horizontal = SizeChart.SIZE_SM.dp, vertical = SizeChart.SIZE_2XS.dp),
                                    )
                                }
                                Text(transactionItem.customerName)
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
                                Text(userItem.name)
                            }
                        }
                        HorizontalDivider(
                            thickness = 1.dp,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(transactionItem.notes)
                    }
                }
            }
        }
        AnimatedVisibility (isExpanded) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(
                    vertical = SizeChart.SMALL_SPACE.dp,
                    horizontal = SizeChart.DEFAULT_SPACE.dp
                )
            ) {
                ProductOutList(
                    isEditable = false,
                    productOutItem = productOut
                )
                Spacer(Modifier.height(SizeChart.DEFAULT_SPACE.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        stringResource(R.string.totalItem, totalItem),
                    )
                    Text(
                        Formatter.currency(transactionItem.totalPrice),
                        style = MaterialTheme.typography.headlineMedium.copy(
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                }
                IconButton({isExpanded = !isExpanded}) {
                    Icon(
                        Icons.Default.ExpandLess,
                        stringResource(R.string.collapse),
                    )
                }
            }
        }
    }
}