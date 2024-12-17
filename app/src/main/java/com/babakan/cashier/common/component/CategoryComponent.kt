package com.babakan.cashier.common.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.babakan.cashier.R
import com.babakan.cashier.utils.constant.SizeChart

@Composable
fun CategoryComponent() {
    Card(
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(SizeChart.SMALL_SPACE.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = SizeChart.SIZE_SM.dp, vertical = SizeChart.SIZE_2XS.dp)
        ) {
            Image(
                painterResource(R.drawable.ic_placeholder),
                stringResource(R.string.category),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                modifier = Modifier.size(SizeChart.ICON_SMALL.dp)
            )
            Text(
                "Kategori",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.primary
                ),
            )
        }
    }
}