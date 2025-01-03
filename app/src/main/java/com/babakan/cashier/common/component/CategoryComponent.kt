package com.babakan.cashier.common.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.babakan.cashier.utils.builder.IconLoader
import com.babakan.cashier.utils.constant.SizeChart

@Composable
fun CategoryComponent(
    name: String,
    iconUrl: String
) {
    Card(
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(SizeChart.BETWEEN_TEXTS.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = SizeChart.SIZE_SM.dp, vertical = SizeChart.SIZE_2XS.dp)
        ) {
            IconLoader(
                iconUrl,
                SizeChart.ICON_EXTRA_SMALL.dp,
                MaterialTheme.colorScheme.onSecondaryContainer
            )
            Text(
                name,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                ),
            )
        }
    }
}