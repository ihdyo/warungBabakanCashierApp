package com.babakan.cashier.common.component

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.babakan.cashier.utils.builder.IconLoader
import com.babakan.cashier.utils.constant.SizeChart

@Composable
fun SearchChipComponent(
    onClick : () -> Unit,
    icon: ImageVector? = null,
    iconUrl: String? = null,
    isSelected: Boolean,
    label: String
) {
    FilterChip(
        onClick = { onClick() },
        leadingIcon = {
            if (icon != null || iconUrl != null) {
                if (iconUrl != null) {
                    IconLoader(
                        iconUrl = iconUrl,
                        size = SizeChart.ICON_LARGE.dp,
                        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                    )
                } else {
                    Icon(
                        icon ?: Icons.Default.GridView,
                        label,
                        tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(SizeChart.ICON_LARGE.dp)
                    )
                }
            }
        },
        label = {
            Text(
                label,
                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
            )
        },
        selected = isSelected
    )
}