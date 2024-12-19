package com.babakan.cashier.common.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.babakan.cashier.R
import com.babakan.cashier.common.builder.ImageLoader
import com.babakan.cashier.common.component.ItemCounterComponent
import com.babakan.cashier.utils.constant.SizeChart
import com.babakan.cashier.utils.formatter.Formatter

@Composable
fun ProductOutItem(
    isEditable: Boolean = false,
    imageUrl: String,
    name: String,
    price: Double,
    quantity: Int,
    textValue: String,
    onTextChanged: (String) -> Unit,
    onSubtract: () -> Unit,
    onAdd: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceContainer),
    ) {
        Row(
            verticalAlignment = if (isEditable) Alignment.CenterVertically else Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(SizeChart.SMALL_SPACE.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(SizeChart.SMALL_SPACE.dp),
                modifier = Modifier.weight(if (isEditable) 2f else 1f)
            ) {
                Card(
                    shape = MaterialTheme.shapes.small
                ) {
                    ImageLoader(
                        imageUrl,
                        SizeChart.IMAGE_LIST_HEIGHT.dp,
                        SizeChart.IMAGE_LIST_HEIGHT.dp
                    )
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(SizeChart.BETWEEN_TEXTS.dp)
                ) {
                    Text(
                        name,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        Formatter.currency(price),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                }
            }
            if (isEditable) {
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    ItemCounterComponent(
                        textValue = textValue,
                        onSubtract = onSubtract,
                        onAdd = onAdd,
                        onTextChanged = onTextChanged
                    )
                }
            } else {
                Text(
                    stringResource(R.string.countItem, quantity),
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        }
    }
}