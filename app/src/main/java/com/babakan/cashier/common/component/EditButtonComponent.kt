package com.babakan.cashier.common.component

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.babakan.cashier.R
import com.babakan.cashier.utils.constant.SizeChart

@Composable
fun EditButton(
    onClick: () -> Unit
) {
    IconButton(
        onClick = {onClick()},
        modifier = Modifier.size(SizeChart.SMALL_ICON_BUTTON.dp)
    ) {
        Icon(
            Icons.Default.Edit,
            stringResource(R.string.edit),
            tint = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.size(SizeChart.ICON_MEDIUM.dp)
        )
    }
}