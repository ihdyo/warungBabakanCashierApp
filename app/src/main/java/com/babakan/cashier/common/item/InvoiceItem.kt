package com.babakan.cashier.common.item

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.babakan.cashier.R
import com.babakan.cashier.common.list.ProductOutList
import com.babakan.cashier.presentation.authentication.model.UserModel
import com.babakan.cashier.presentation.owner.model.ProductOutModel
import com.babakan.cashier.presentation.owner.model.TransactionModel
import com.babakan.cashier.utils.constant.SizeChart
import com.babakan.cashier.utils.formatter.Formatter

@Composable
fun InvoiceItem(
    transactionItem: TransactionModel,
    userItem: UserModel,
    productOut: List<ProductOutModel>,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(SizeChart.BETWEEN_ITEMS.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(vertical = SizeChart.SMALL_SPACE.dp)
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                disabledContentColor = MaterialTheme.colorScheme.onSurface
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
            }
        }
        ProductOutList(
            productOutItem = productOut,
        )
        Spacer(Modifier.height(SizeChart.SMALL_SPACE.dp))
        OutlinedCard {
            Column(
                verticalArrangement = Arrangement.spacedBy(SizeChart.DEFAULT_SPACE.dp),
                modifier = Modifier.padding(SizeChart.DEFAULT_SPACE.dp)
            ) {
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
                                    color = MaterialTheme.colorScheme.onSecondaryContainer
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
                        Text(
                            Formatter.firstName(userItem.name),
                            textAlign = TextAlign.End
                        )
                    }
                }
                if (transactionItem.notes.isNotBlank()) {
                    HorizontalDivider(
                        thickness = 1.dp,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(transactionItem.notes)
                }
            }
        }
        Spacer(Modifier.height(SizeChart.BETWEEN_ITEMS.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                stringResource(R.string.totalItem, productOut.sumOf { it.quantity }),
            )
            Text(
                Formatter.currency(transactionItem.totalPrice),
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = MaterialTheme.colorScheme.primary
                )
            )
        }
        Spacer(Modifier.height(SizeChart.BETWEEN_SECTIONS.dp))
    }
}