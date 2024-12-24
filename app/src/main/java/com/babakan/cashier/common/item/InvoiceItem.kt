package com.babakan.cashier.common.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
        verticalArrangement = Arrangement.spacedBy(SizeChart.BETWEEN_SECTIONS.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(vertical = SizeChart.SMALL_SPACE.dp)
    ) {
        ProductOutList(
            productOutItem = productOut,
        )
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
    }
}