package com.babakan.cashier.presentation.cashier.screen.cart.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.babakan.cashier.R
import com.babakan.cashier.utils.constant.SizeChart

@Composable
fun CustomerData(
    customerName: String,
    tableNumber: String,
    notes: String
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(SizeChart.SMALL_SPACE.dp),
        modifier = Modifier.padding(vertical = SizeChart.SMALL_SPACE.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(SizeChart.SMALL_SPACE.dp)
                ) {
                    Card(
                        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondaryContainer),
                    ) {
                        Text(
                            stringResource(R.string.numberOrder, tableNumber).uppercase(),
                            style = MaterialTheme.typography.labelLarge.copy(
                                color = MaterialTheme.colorScheme.secondary
                            ),
                            modifier = Modifier.padding(horizontal = SizeChart.SIZE_SM.dp, vertical = SizeChart.SIZE_2XS.dp),
                        )
                    }
                    Text(
                        customerName,
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
        if (notes.isNotBlank()) {
            HorizontalDivider(
                thickness = 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = SizeChart.SMALL_SPACE.dp)
            )
            Text(notes)
        }
    }
}