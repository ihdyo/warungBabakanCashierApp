package com.babakan.cashier.common.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.babakan.cashier.R
import com.babakan.cashier.utils.constant.SizeChart
import com.babakan.cashier.utils.formatter.Formatter

@Composable
fun OrderedProductItem() {
    Card(
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceContainerHigh),
    ) {
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(SizeChart.SMALL_SPACE.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(SizeChart.SMALL_SPACE.dp),
            ) {
                Card(
                    shape = MaterialTheme.shapes.small
                ) {
                    Image(
                        painterResource(R.drawable.img_placeholder),
                        stringResource(R.string.productImage),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(SizeChart.IMAGE_LIST_HEIGHT.dp)
                    )
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(SizeChart.BETWEEN_TEXTS.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        "Lorem Ipsum",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        Formatter.currency(10000),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                }
            }
            Text(
                stringResource(R.string.countItem, 2),
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }
}